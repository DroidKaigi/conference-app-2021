import SwiftUI
import Styleguide

public struct AboutScreen: View {
    public init() {}

    public var body: some View {
        Text(L10n.AboutScreen.title)
    }
}

public struct AboutScreen_Previews: PreviewProvider {
    public static var previews: some View {
        AboutScreen()
    }
}
