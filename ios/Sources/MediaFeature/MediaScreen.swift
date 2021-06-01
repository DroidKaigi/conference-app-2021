import SwiftUI
import Styleguide

public struct MediaScreen: View {
    public init() {}

    public var body: some View {
        Text(L10n.MediaScreen.title)
    }
}

public struct MediaScreen_Previews: PreviewProvider {
    public static var previews: some View {
        MediaScreen()
    }
}
