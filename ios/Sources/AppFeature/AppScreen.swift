import AboutFeature
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
            return "Home"
        case .media:
            return "Media"
        case .favorites:
            return "Favorites"
        case .about:
            return "About"
        }
    }

    var view: some View {
        switch self {
        case .home:
            return AnyView(HomeScreen())
        case .media:
            return AnyView(MediaScreen())
        case .favorites:
            return AnyView(FavoritesScreen())
        case .about:
            return AnyView(AboutScreen())
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
}

public struct AppScreen: View {
    @State var selection = 0

    public init() {
        UITabBar.appearance().barTintColor = AssetColor.Background.contents.color
        UITabBar.appearance().unselectedItemTintColor = AssetColor.Base.disable.color
    }

    public var body: some View {
        TabView(
            selection: $selection,
            content: {
                ForEach(Array(AppTab.allCases.enumerated()), id: \.offset) { (offset, tab) in
                    tab.view
                        .tabItem {
                            Image(uiImage: tab.image)
                            Text(tab.title)
                        }
                        .tag(offset)
                }
            }
        ).accentColor(Color(AssetColor.primary.color.cgColor))
    }
}

public struct AppScreen_Previews: PreviewProvider {
    public static var previews: some View {
        AppScreen()
    }
}
