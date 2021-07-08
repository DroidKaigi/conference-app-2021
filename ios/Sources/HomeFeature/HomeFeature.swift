import Component
import ComposableArchitecture
import Model
import Repository

public enum HomeState: Equatable {
    case needToInitialize
    case initialized(HomeListState)

    public init() {
        self = .needToInitialize
    }
}

public enum HomeAction {
    case refresh
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case homeList(HomeListAction)
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
    homeListReducer.pullback(
        state: /HomeState.initialized,
        action: /HomeAction.homeList,
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
        case .homeList:
            return .none
        }
    }
)
