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
    public var isSettingPresented: Bool

    public init(
        homeState: HomeState = .init(),
        mediaState: MediaState = .init(),
        favoritesState: FavoritesState = .init(),
        aboutState: AboutState = .init(),
        isSettingPresented: Bool = false
    ) {
        self.homeState = homeState
        self.mediaState = mediaState
        self.favoritesState = favoritesState
        self.aboutState = aboutState
        self.isSettingPresented = isSettingPresented
    }
}

public enum AppAction {
    case home(HomeAction)
    case media(MediaAction)
    case favorites(FavoritesAction)
    case about(AboutAction)
    case hideSetting
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    homeReducer.pullback(
        state: \.homeState,
        action: /AppAction.home,
        environment: { _ -> HomeEnvironment in
            .init()
        }
    ),
    mediaReducer.pullback(
        state: \.mediaState,
        action: /AppAction.media,
        environment: { _ in
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
    .init { state, action, _ in
        switch action {
        case .home(.showSetting),
             .media(.showSetting),
             .favorites(.showSetting):
            state.isSettingPresented = true
            return .none
        case .home:
            return .none
        case .media:
            return .none
        case .favorites:
            return .none
        case .about:
            return .none
        case .hideSetting:
            state.isSettingPresented = false
            return .none
        }
    }
)
