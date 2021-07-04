import Component
import ComposableArchitecture
import Model
import Repository

public enum HomeState: Equatable {
    case needToInitialize
    case initialized(HomeContentState)

    public init() {
        self = .needToInitialize
    }
}

public enum HomeAction {
    case refresh
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case homeContent(HomeContentAction)
}

public struct HomeEnvironment {
    public let feedRepository: FeedRepositoryProtocol

    public init(
        feedRepository: FeedRepositoryProtocol
    ) {
        self.feedRepository = feedRepository
    }
}

public let homeReducer = Reducer<HomeState, HomeAction, HomeEnvironment>.combine(
    homeContentReducer.pullback(
        state: /HomeState.initialized,
        action: /HomeAction.homeContent,
        environment: {
            .init(feedRepository: $0.feedRepository)
        }
    ),
    .init { state, action, environment in
        switch action {
        case .refresh:
            return environment.feedRepository.feedContents()
                .catchToEffect()
                .map(HomeAction.refreshResponse)
        case let .refreshResponse(.success(feedContents)):
            if !feedContents.isEmpty {
                state = .initialized(.init(feedContents: feedContents))
            }
            return .none
        case let .refreshResponse(.failure(error)):
            print(error.localizedDescription)
            // TODO: Error handling
            return .none
        case .homeContent:
            return .none
        }
    }
)
