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
    case tapFavorite(FeedContent)
    case favoriteResponse(Result<FeedContent, KotlinError>)
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

public let HomeListReducer = Reducer<HomeListState, HomeListAction, HomeListEnvironment> { state, action, environment in
    switch action {
    case .selectFeedContent:
        // TODO: open content page
        return .none
    case let .tapFavorite(feedContent):
        if feedContent.isFavorited {
            return environment.feedRepository.removeFavorite(feedItem: feedContent.item)
                .map { _ in feedContent }
                .catchToEffect()
                .map(HomeListAction.favoriteResponse)
        } else {
            return environment.feedRepository.addFavorite(feedItem: feedContent.item)
                .map { _ in feedContent }
                .catchToEffect()
                .map(HomeListAction.favoriteResponse)
        }
    case let .favoriteResponse(.success(feedContent)):
        if let index = state.feedContents.firstIndex(of: feedContent) {
            state.feedContents[index].isFavorited.toggle()
        }
        return .none
    case let .favoriteResponse(.failure(error)):
        print(error.localizedDescription)
        return .none
    case .answerQuestionnaire:
        // TODO: open questionnaire
        return .none
    }
}
