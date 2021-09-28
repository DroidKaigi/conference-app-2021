import Styleguide
import SwiftUI

public struct ErrorView: View {
    private var tapReload: () -> Void

    public init(tapReload: @escaping () -> Void) {
        self.tapReload = tapReload
    }

    public var body: some View {
        VStack(spacing: 16) {
            Text(L10n.Component.ErrorView.title)

            Button(
                action: tapReload,
                label: {
                    Text(L10n.Component.ErrorView.reload)
                        .foregroundColor(AssetColor.primary.color)
            })
        }
    }
}

#if DEBUG
public struct ErrorView_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            ErrorView(tapReload: {})
                .background(AssetColor.Background.primary.color)
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)
        }
    }
}
#endif
