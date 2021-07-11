import Component
import Styleguide
import SwiftUI

public struct ContributorCell: View {
    enum Const {
        static let imageSize: CGFloat = 60
    }

    private let contributor: Contributor

    public init(contributor: Contributor) {
        self.contributor = contributor
    }

    public var body: some View {
        VStack {
            ImageView(
                imageURL: contributor.iconUrl,
                placeholderSize: .medium,
                width: Const.imageSize,
                height: Const.imageSize
            )
            .clipShape(Circle())
            .overlay(
                RoundedRectangle(cornerRadius: Const.imageSize / 2)
                    .stroke(AssetColor.Separate.image.color, lineWidth: 2)
            )
            Text(contributor.name)
                .foregroundColor(AssetColor.Base.primary.color)
                .font(.caption)
                .fontWeight(.medium)
        }
    }
}

public struct ContributorCell_Preview: PreviewProvider {
    public static var previews: some View {
        ContributorCell(
            contributor: Contributor(
                name: "dummy name",
                iconUrl: URL(string: "https://example.com")!
            )
        )
        .frame(width: 111, height: 116)
        .environment(\.colorScheme, .light)
        .previewLayout(.sizeThatFits)
        ContributorCell(
            contributor: Contributor(
                name: "dummy name",
                iconUrl: URL(string: "https://example.com")!
            )
        )
        .frame(width: 111, height: 116)
        .environment(\.colorScheme, .dark)
        .previewLayout(.sizeThatFits)
    }
}
