import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Repository

public struct AppState: Equatable {
    public var homeState: HomeState
    public var mediaState: MediaState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState

    public init(
        homeState: HomeState = .init(),
        mediaState: MediaState = .init(),
        favoritesState: FavoritesState = .init(),
        aboutState: AboutState = .init()
    ) {
        self.homeState = homeState
        self.mediaState = mediaState
        self.favoritesState = favoritesState
        self.aboutState = aboutState
    }
}

public enum AppAction {
    case home(HomeAction)
    case media(MediaAction)
    case favorites(FavoritesAction)
    case about(AboutAction)
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    homeReducer.pullback(
        state: \.homeState,
        action: /AppAction.home,
        environment: { environment -> HomeEnvironment in
            .init(feedRepository: environment.feedRepository)
        }
    ),
    mediaReducer.pullback(
        state: \.mediaState,
        action: /AppAction.media,
        environment: { environment in
            .init(feedRepository: environment.feedRepository)
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
        case .media:
            return .none
        case .favorites:
            return .none
        case .about:
            return .none
        }
    }
)
