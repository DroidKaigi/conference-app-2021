import SwiftUI
import Styleguide

public struct MessageBar: View {
    private let title: String

    public init(
        title: String
    ) {
        self.title = title
    }

    public var body: some View {
        Text(title)
            .font(.subheadline)
            .foregroundColor(Color(AssetColor.Base.white.color))
            .padding(.vertical, 8)
            .padding(.horizontal, 12)
            .background(Color(AssetColor.primaryDark.color))
            .cornerRadius(4)
    }
}

struct MessageBar_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            MessageBar(title: "DroidKaigi 2021 is coming soon 🎉")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .light)
            MessageBar(title: "DroidKaigi 2021 (7/31) D-100  🤖")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .light)
            MessageBar(title: "DroidKaigi 2021 (7/31) D-7  💗")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .light)
            MessageBar(title: "DroidKaigi 2022 is coming soon 🎉")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .light)

            MessageBar(title: "DroidKaigi 2021 is coming soon 🎉")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .dark)
            MessageBar(title: "DroidKaigi 2021 (7/31) D-100  🤖")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .dark)
            MessageBar(title: "DroidKaigi 2021 (7/31) D-7  💗")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .dark)
            MessageBar(title: "DroidKaigi 2022 is coming soon 🎉")
                .frame(width: 280, height: 36)
                .environment(\.colorScheme, .dark)
        }
        .previewLayout(.sizeThatFits)
    }
}
