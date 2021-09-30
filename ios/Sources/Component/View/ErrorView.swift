import Styleguide
import SwiftUI

public struct ErrorView: View {
    private var tapReload: () -> Void

    public init(tapReload: @escaping () -> Void) {
        self.tapReload = tapReload
    }

    public var body: some View {
        VStack(spacing: 32) {
            Text(L10n.Component.ErrorView.title)
                .font(.title2)
                .bold()
                .foregroundColor(AssetColor.Base.primary.color)

            Button(action: tapReload, label: {
                Text(L10n.Component.ErrorView.reload)
                    .font(.headline)
                    .bold()
                    .foregroundColor(Color.white)
            })
            .padding(.vertical, 10)
            .padding(.horizontal, 24)
            .background(AssetColor.primary.color)
            .cornerRadius(20)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(AssetColor.Background.primary.color.ignoresSafeArea())
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
