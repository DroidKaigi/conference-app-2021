import SwiftUI
import Styleguide

public struct MediaScreen: View {
    public init() {}

    public var body: some View {
        NavigationView {
            list
                .navigationTitle(L10n.MediaScreen.title)
                .navigationBarItems(
                    trailing: AssetImage.iconSetting.image
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                )
        }
    }

    private var list: some View {
        ScrollView {

        }
    }
}

public struct MediaScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            MediaScreen()
                .environment(\.colorScheme, colorScheme)
        }
    }
}
