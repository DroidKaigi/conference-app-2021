import ComposableArchitecture
import HomeFeature
import FavoritesFeature

public struct AppState: Equatable {
    public var homeState: HomeState
    public var favoritesState: FavoritesState

    public init(
        homeState: HomeState = .init(),
        favoritesState: FavoritesState = .init(
            items: [
                .init(isFavorited: true),
                .init(isFavorited: false),
                .init(isFavorited: true),
                .init(isFavorited: false),
                .init(isFavorited: true),
                .init(isFavorited: false),
            ]
        )
    ) {
        self.homeState = homeState
        self.favoritesState = favoritesState
    }
}

public enum AppAction {
    case home(HomeAction)
    case favorites(FavoritesAction)
}

public struct AppEnvironment {
    public init() {}
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    homeReducer.pullback(
        state: \.homeState,
        action: /AppAction.home,
        environment: { _ -> HomeEnvironment in
            .init()
        }
    ),
    favoritesReducer.pullback(
        state: \.favoritesState,
        action: /AppAction.favorites,
        environment: { _ -> FavoritesEnvironment in
            .init()
        }
    ),
    .init { _, action, _ in
        switch action {
        case .home:
            return .none
        case .favorites:
            return .none
        }
    }
)
