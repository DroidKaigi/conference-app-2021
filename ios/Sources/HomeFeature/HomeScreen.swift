import SwiftUI
import Styleguide

public struct HomeScreen: View {
    public init() {}

    public var body: some View {
        Text(L10n.HomeScreen.title)
    }
}

public struct HomeScreen_Previews: PreviewProvider {
    public static var previews: some View {
        HomeScreen()
    }
}
