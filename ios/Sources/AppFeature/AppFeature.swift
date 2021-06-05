import ComposableArchitecture
import AboutFeature
import FavoritesFeature
import HomeFeature

public struct AppState: Equatable {
    public var homeState: HomeState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState
    
    public init(
        homeState: HomeState = .init(),
        favoritesState: FavoritesState = .init(),
        aboutState: AboutState = .init()
    ) {
        self.homeState = homeState
        self.favoritesState = favoritesState
        self.aboutState = aboutState
    }
}

public enum AppAction {
    case home(HomeAction)
    case favorites(FavoritesAction)
    case about(AboutAction)
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
    aboutReducer.pullback(
        state: \.aboutState,
        action: /AppAction.about,
        environment: { _ -> AboutEnvironment in
            .init()
        }
    ),
    .init { _, action, _ in
        switch action {
        case .home:
            return .none
        case .favorites:
            return .none
        case .about:
            return .none
        }
    }
)
