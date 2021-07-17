import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Styleguide
import SwiftUI
import UIKit

enum AppTab: CaseIterable {
    case home
    case media
    case favorites
    case about

    var title: String {
        switch self {
        case .home:
            return L10n.HomeScreen.title
        case .media:
            return L10n.MediaScreen.title
        case .favorites:
            return L10n.FavoriteScreen.title
        case .about:
            return L10n.AboutScreen.title
        }
    }

    var image: Image {
        switch self {
        case .home:
            return AssetImage.iconHome.image
        case .media:
            return AssetImage.iconBlog.image
        case .favorites:
            return AssetImage.iconStar.image
        case .about:
            return AssetImage.iconAbout.image
        }
    }

    @ViewBuilder
    func view(_ store: Store<AppState, AppAction>) -> some View {
        switch self {
        case .home:
            HomeScreen(
                store: store.scope(
                    state: \.homeState,
                    action: AppAction.home
                )
            )
        case .media:
            MediaScreen(
                store: store.scope(
                    state: \.mediaState,
                    action: AppAction.media
                )
            )
        case .favorites:
            FavoritesScreen(
                store: store.scope(
                    state: \.favoritesState,
                    action: AppAction.favorites
                )
            )
        case .about:
            AboutScreen(
                store: store.scope(
                    state: \.aboutState,
                    action: AppAction.about
                )
            )
        }
    }
}

public struct AppScreen: View {
    @State var selection = 0

    private let store: Store<AppState, AppAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, ViewAction>

    public init(store: Store<AppState, AppAction>) {
        self.store = store
        self.viewStore = ViewStore<ViewState, ViewAction>(
            store.scope(
                state: ViewState.init(state:),
                action: AppAction.init(action:)
            )
        )
        UITabBar.appearance().configureWithDefaultStyle()
        UINavigationBar.appearance().configureWithDefaultStyle()
    }

    internal struct ViewState: Equatable {
        init(state: AppState) {}
    }

    internal enum ViewAction {
        case progressViewAppeared
        case tapReload
    }

    public var body: some View {
        SwitchStore(store.scope(state: \.coreState)) {
            CaseLet(
                state: /AppState.AppCoreState.needToInitialize,
                action: AppAction.init(action:)) { _ in
                ProgressView()
                    .onAppear { viewStore.send(.progressViewAppeared) }
            }
            CaseLet(
                state: /AppState.AppCoreState.initialized,
                action: AppAction.init(action:)) { _ in
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
            }
            CaseLet(
                state: /AppState.AppCoreState.errorOccurred,
                action: AppAction.init(action:)) { _ in
                VStack(spacing: 16) {
                    Text("エラーが発生しました")

                    Button(action: {
                        viewStore.send(.tapReload)
                    }, label: {
                        Text("再度読み込み")
                            .foregroundColor(AssetColor.primary.color)
                    })
                }
            }
        }
        .accentColor(AssetColor.primary.color)
        .background(AssetColor.Background.primary.color)
    }
}

private extension AppAction {
    init(action: AppScreen.ViewAction) {
        switch action {
        case .progressViewAppeared:
            self = .refresh
        case .tapReload:
            self = .needRefresh
        }
    }
}

public struct AppScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            AppScreen(
                store: .init(
                    initialState: .init(),
                    reducer: .empty,
                    environment: AppEnvironment.noop
                )
            )
            .environment(\.colorScheme, .dark)
            AppScreen(
                store: .init(
                    initialState: .init(),
                    reducer: .empty,
                    environment: AppEnvironment.noop
                )
            )
            .environment(\.colorScheme, .light)
        }
    }
}
