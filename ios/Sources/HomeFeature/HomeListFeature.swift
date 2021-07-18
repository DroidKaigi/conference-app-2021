import ComposableArchitecture
import Model
import Repository
import UIApplicationClient

public struct HomeListState: Equatable {
    public var feedContents: [FeedContent]
    public var showingURL: URL?
    public var message: String {
        "Finished! 🤖"
    }

    public var topic: FeedContent? {
        feedContents.first
    }

    public var listFeedContents: [FeedContent] {
        Array(feedContents.dropFirst())
    }

    public var isShowingWebView: Bool {
        showingURL != nil
    }

    public init(feedContents: [FeedContent] = []) {
        self.feedContents = feedContents
        self.showingURL = nil
    }
}

public enum HomeListAction {
    case selectFeedContent(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
    case hideWebView
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
    case let .selectFeedContent(feedContent):
        state.showingURL = URL(string: feedContent.item.link)
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
    case .hideWebView:
        state.showingURL = nil
        return .none
    }
}
