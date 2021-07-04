import ComposableArchitecture
import Model
import Repository

public struct HomeContentState: Equatable {
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

public enum HomeContentAction {
    case selectFeedContent
    case tapFavorite(FeedContent)
    case favoriteResponse(Result<FeedContent, KotlinError>)
    case answerQuestionnaire
}

public struct HomeContentEnvironment {
    public let feedRepository: FeedRepositoryProtocol

    public init(
        feedRepository: FeedRepositoryProtocol
    ) {
        self.feedRepository = feedRepository
    }
}

public let homeContentReducer = Reducer<HomeContentState, HomeContentAction, HomeContentEnvironment> { state, action, environment in
    switch action {
    case .selectFeedContent:
        // TODO: open content page
        return .none
    case let .tapFavorite(feedContent):
        if feedContent.isFavorited {
            return environment.feedRepository.removeFavorite(feedItem: feedContent.item)
                .map { _ in feedContent }
                .catchToEffect()
                .map(HomeContentAction.favoriteResponse)
        } else {
            return environment.feedRepository.addFavorite(feedItem: feedContent.item)
                .map { _ in feedContent }
                .catchToEffect()
                .map(HomeContentAction.favoriteResponse)
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
