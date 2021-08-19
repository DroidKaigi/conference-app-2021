import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import SettingFeature
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

    public init(store: Store<AppState, AppAction>) {
        self.store = store
        UITabBar.appearance().configureWithDefaultStyle()
        UINavigationBar.appearance().configureWithDefaultStyle()
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
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
            .sheet(
                isPresented: viewStore.binding(
                    get: \.isSettingPresented,
                    send: .hideSetting
                ),
                content: {
                    SettingScreen(
                        store: .init(
                            initialState: .init(items: [SettingModel.darkMode(true), SettingModel.language(false)]),
                            reducer: settingReducer,
                            environment: SettingEnvironment()
                        )
                    )
                }
            }
        )
        .accentColor(AssetColor.primary.color)
        .background(AssetColor.Background.primary.color)
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
