import Component
import ComposableArchitecture
import Model
import Repository

public enum FavoritesState: Equatable {
    case needToInitialize
    case emptyInitialized
    case initialized(FavoritesListState)

    public init() {
        self = .needToInitialize
    }
}

public enum FavoritesAction {
    case refresh
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case favoritesList(FavoritesListAction)
}

public struct FavoritesEnvironment {
    public let feedRepository: FeedRepositoryProtocol

    public init(
        feedRepository: FeedRepositoryProtocol
    ) {
        self.feedRepository = feedRepository
    }
}

public let favoritesReducer = Reducer<FavoritesState, FavoritesAction, FavoritesEnvironment>.combine(
    favoritesListReducer.pullback(
        state: /FavoritesState.initialized,
        action: /FavoritesAction.favoritesList,
        environment: {
            .init(feedRepository: $0.feedRepository)
        }
    ),
    .init { state, action, environment in
        switch action {
        case .refresh:
            return environment.feedRepository.feedContents()
                .catchToEffect()
                .map(FavoritesAction.refreshResponse)
        case let .refreshResponse(.success(feedContents)):
            let filteredFeedContents = feedContents.filter(\.isFavorited)
            if !filteredFeedContents.isEmpty {
                state = .initialized(.init(feedContents: filteredFeedContents))
            } else {
                state = .emptyInitialized
            }
            return .none
        case let .refreshResponse(.failure(error)):
            print(error.localizedDescription)
            // TODO: Error handling
            return .none
        case .favoritesList:
            return .none
        }
    }
)
