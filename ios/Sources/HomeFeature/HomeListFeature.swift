import ComposableArchitecture
import Model
import Repository
import UIApplicationClient

public struct HomeListState: Equatable {
    public var feedContents: [FeedContent]
    public var message: String {
        "Finished! 🤖"
    }

    public var topic: FeedContent? {
        feedContents.first
    }

    public var listFeedContents: [FeedContent] {
        Array(feedContents.dropFirst())
    }

    public init(feedContents: [FeedContent] = []) {
        self.feedContents = feedContents
    }
}

public enum HomeListAction {
    case selectFeedContent(id: String)
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
    case urlOpened(Bool)
}

public struct HomeListEnvironment {
    public let feedRepository: FeedRepositoryProtocol
    public let applicationClient: UIApplicationClientProtocol

    public init(
        feedRepository: FeedRepositoryProtocol,
        applicationClient: UIApplicationClientProtocol
    ) {
        self.feedRepository = feedRepository
        self.applicationClient = applicationClient
    }
}

public let homeListReducer = Reducer<HomeListState, HomeListAction, HomeListEnvironment> { state, action, environment in
    switch action {
    case let .selectFeedContent(id):
        if let selectedFeedContent = state.feedContents.first(where: { $0.id == id }),
           let url = URL(string: selectedFeedContent.item.link) {
            return environment.applicationClient.open(
                url: url,
                options: [:]
            )
            .eraseToEffect()
            .map(HomeListAction.urlOpened)
        }
        return .none
    case .tapFavorite(let isFavorited, let id):
        let publisher = isFavorited
            ? environment.feedRepository.removeFavorite(id: id)
            : environment.feedRepository.addFavorite(id: id)
        return publisher
            .map { id }
            .catchToEffect()
            .map(HomeListAction.favoriteResponse)
    case let .favoriteResponse(.success(id)):
        if let index = state.feedContents.map(\.id).firstIndex(of: id) {
            state.feedContents[index].isFavorited.toggle()
        }
        return .none
    case let .favoriteResponse(.failure(error)):
        print(error.localizedDescription)
        return .none
    case .answerQuestionnaire:
        // TODO: open questionnaire
        return .none
    case .urlOpened:
        return .none
    }
}
