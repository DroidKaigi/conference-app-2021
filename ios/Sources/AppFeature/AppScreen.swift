import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Styleguide
import SwiftUI

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

    var image: UIImage {
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

    func view(_ store: Store<AppState, AppAction>) -> some View {
        switch self {
        case .home:
            return AnyView(HomeScreen(
                store: store.scope(
                    state: \.homeState,
                    action: AppAction.home
                )
            ))
        case .media:
            return AnyView(MediaScreen())
        case .favorites:
            return AnyView(FavoritesScreen())
        case .about:
            return AnyView(AboutScreen())
        }
    }
}

public struct AppScreen: View {
    @State var selection = 0

    private let store: Store<AppState, AppAction>

    public init(store: Store<AppState, AppAction>) {
        self.store = store
        UITabBar.appearance().barTintColor = AssetColor.Background.contents.color
        UITabBar.appearance().unselectedItemTintColor = AssetColor.Base.disable.color
        UINavigationBar.appearance().barTintColor = AssetColor.Background.primary.color
    }

    public var body: some View {
        TabView(
            selection: $selection,
            content: {
                ForEach(Array(AppTab.allCases.enumerated()), id: \.offset) { (offset, tab) in
                    tab.view(store)
                        .tabItem {
                            Image(uiImage: tab.image)
                            Text(tab.title)
                        }
                        .tag(offset)
                }
            }
        )
        .accentColor(Color(AssetColor.primary.color.cgColor))
        .background(Color(AssetColor.Background.primary.color))
    }
}

struct AppScreen_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            AppScreen(
                store: .init(
                    initialState: .init(),
                    reducer: appReducer,
                    environment: .init()
                )
            )
            .environment(\.colorScheme, .dark)
            AppScreen(
                store: .init(
                    initialState: .init(),
                    reducer: appReducer,
                    environment: .init()
                )
            )
            .environment(\.colorScheme, .light)
        }
    }
}
