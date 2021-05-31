import AboutFeature
import FavoritesFeature
import HomeFeature
import MediaFeature
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
}

public struct AppScreen: View {
    @State var selection = 0

    public init() {
    }

    public var body: some View {
        TabView(
            selection: $selection,
            content: {
                ForEach(Array(AppTab.allCases.enumerated()), id: \.offset) { (offset, tab) in
                    tab.view
                        .tabItem {
                            Text(tab.title)
                        }
                        .tag(offset)
                }
            }
        )
    }
}

public struct AppScreen_Previews: PreviewProvider {
    public static var previews: some View {
        AppScreen()
    }
}
