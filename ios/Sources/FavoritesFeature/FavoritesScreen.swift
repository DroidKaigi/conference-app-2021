import SwiftUI
import Styleguide

public struct FavoritesScreen: View {
    public init() {}

    public var body: some View {
        Text(L10n.FavoriteScreen.title)
    }
}

public struct FavoritesScreen_Previews: PreviewProvider {
    public static var previews: some View {
        FavoritesScreen()
    }
}
