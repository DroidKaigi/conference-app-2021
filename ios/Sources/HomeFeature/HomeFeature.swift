import Component
import ComposableArchitecture
import Model
import Repository

public struct HomeState: Equatable {
    let feedContents: [FeedContent]
    public var message: String {
        "Finished! 🤖"
    }

    public var topic: FeedContent? {
        feedContents.first
    }

    public var listFeedContents: [FeedContent] {
        Array(feedContents.dropFirst())
    }

    public init(feedContents: [FeedContent]) {
        self.feedContents = feedContents
    }
}

public enum HomeAction {
    case selectFeedContent
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
}

public struct HomeEnvironment {
    public let feedRepository: FeedRepositoryProtocol

    public init(
        feedRepository: FeedRepositoryProtocol
    ) {
        self.feedRepository = feedRepository
    }
}

public let homeReducer = Reducer<HomeState, HomeAction, HomeEnvironment> { _, _, _ in
    return .none
//        switch action {
//        case .refresh:
//            return environment.feedRepository.feedContents()
//                .catchToEffect()
//                .map(HomeAction.refreshResponse)
//        case let .refreshResponse(.success(feedContents)):
//            if !feedContents.isEmpty {
//                state = .initialized(.init(feedContents: feedContents))
//            }
//            return .none
//        case let .refreshResponse(.failure(error)):
//            print(error.localizedDescription)
//            // TODO: Error handling
//            return .none
//        case .needRefresh:
//            state = .needToInitialize
//            return .none
//        case .homeList:
//            return .none
//        }
//    }
}
