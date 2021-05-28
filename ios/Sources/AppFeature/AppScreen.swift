import AboutFeature
import FavoritesFeature
import HomeFeature
import MediaFeature
import SwiftUI

public struct AppScreen: View {
    @State var selection = 0

    public init() {}

    public var body: some View {
        TabView(
            selection: $selection,
            content: {
                HomeScreen()
                    .tabItem {
                        Text("Home")
                    }
                    .tag(0)
                MediaScreen()
                    .tabItem {
                        Text("Media")
                    }
                    .tag(1)
                FavoritesScreen()
                    .tabItem {
                        Text("Favorites")
                    }
                    .tag(2)
                AboutScreen()
                    .tabItem {
                        Text("About")
                    }
                    .tag(3)
            }
        )
    }
}

public struct AppScreen_Previews: PreviewProvider {
    public static var previews: some View {
        AppScreen()
    }
}
