import ComposableArchitecture
import HomeFeature
import MediaFeature
import FavoritesFeature
import AboutFeature
import SwiftUI
import Model

public struct AppTabState: Equatable {
    public var homeState: HomeState
    public var mediaState: MediaState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState

    public init(
        feedContent: [FeedContent]
    ) {
        self.homeState = HomeState.init(feedContents: feedContent)
        self.mediaState = MediaState.init()
        self.favoritesState = FavoritesState.init()
        self.aboutState = AboutState.init()
    }
}

public enum AppTabAction {
    case home(HomeAction)
    case media(MediaAction)
    case favorites(FavoritesAction)
    case about(AboutAction)
}

public let appTabReducer = Reducer<AppTabState, AppTabAction, AppEnvironment>.combine(
    homeReducer.pullback(
        state: \.homeState,
        action: /AppTabAction.home,
        environment: { environment -> HomeEnvironment in
            .init(feedRepository: environment.feedRepository)
        }
    ),
    mediaReducer.pullback(
        state: \.mediaState,
        action: /AppTabAction.media,
        environment: { environment in
            .init(feedRepository: environment.feedRepository)
        }
    ),
    favoritesReducer.pullback(
        state: \.favoritesState,
        action: /AppTabAction.favorites,
        environment: { environment -> FavoritesEnvironment in
            .init(feedRepository: environment.feedRepository)
        }
    ),
    aboutReducer.pullback(
        state: \.aboutState,
        action: /AppTabAction.about,
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

public struct AppTabView: View {
    @State var selection: Int = .zero
    public let store: Store<AppTabState, AppTabAction>

    public init(store: Store<AppTabState, AppTabAction>) {
        self.store = store
        UITabBar.appearance().configureWithDefaultStyle()
        UINavigationBar.appearance().configureWithDefaultStyle()
    }

    public enum ViewAction {
        case reload
    }

    public var body: some View {
        TabView(
            selection: $selection,
            content: {
                ForEach(Array(AppTab.allCases.enumerated()), id: \.offset) { (offset, tab) in
                    tab.view(store)
                        .tabItem {
                            tab.image.renderingMode(.template)
                            Text(tab.title)
                        }
                        .tag(offset)
                }
            }
        )
        .onReceive(
            NotificationCenter
                .default
                .publisher(for: UIApplication.willEnterForegroundNotification)
        ) { _ in
            print("hoge")
//            viewStore.send(.reload)
        }
    }
}
