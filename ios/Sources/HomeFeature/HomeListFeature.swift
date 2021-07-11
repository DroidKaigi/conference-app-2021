import ComposableArchitecture
import Model
import Repository

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
    case selectFeedContent
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
}

public struct HomeListEnvironment {
    public let feedRepository: FeedRepositoryProtocol

    public init(
        feedRepository: FeedRepositoryProtocol
    ) {
        self.feedRepository = feedRepository
    }
}

public let homeListReducer = Reducer<HomeListState, HomeListAction, HomeListEnvironment> { state, action, environment in
    switch action {
    case .selectFeedContent:
        // TODO: open content page
        return .none
    case .tapFavorite(let isFavorited, let id):
        if let index = state.feedContents.map(\.id).firstIndex(of: id) {
            state.feedContents[index].isFavorited.toggle()
        }
        let publisher = isFavorited
            ? environment.feedRepository.removeFavorite(id: id)
            : environment.feedRepository.addFavorite(id: id)
        return publisher
            .map { id }
            .catchToEffect()
            .map(HomeListAction.favoriteResponse)
    case let .favoriteResponse(.success(id)):
        return .none
    case let .favoriteResponse(.failure(error)):
        print(error.localizedDescription)
        return .none
    case .answerQuestionnaire:
        // TODO: open questionnaire
        return .none
    }
}
