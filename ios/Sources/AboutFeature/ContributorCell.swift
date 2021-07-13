import Component
import Model
import Styleguide
import SwiftUI

public struct ContributorCell: View {
    enum Const {
        static let imageSize: CGFloat = 60
    }

    private let contributor: Contributor
    private let onTap: (Contributor) -> Void

    public init(contributor: Contributor, onTap: @escaping (Contributor) -> Void) {
        self.contributor = contributor
        self.onTap = onTap
    }

    public var body: some View {
        VStack {
            ImageView(
                imageURL: URL(string: contributor.imageURLString),
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
        .onTapGesture {
            onTap(contributor)
        }
    }
}

public struct ContributorCell_Preview: PreviewProvider {
    public static var previews: some View {
        ContributorCell(
            contributor: .mock(),
            onTap: { _ in }
        )
        .frame(width: 111, height: 116)
        .environment(\.colorScheme, .light)
        .previewLayout(.sizeThatFits)
        ContributorCell(
            contributor: .mock(),
            onTap: { _ in }
        )
        .frame(width: 111, height: 116)
        .environment(\.colorScheme, .dark)
        .previewLayout(.sizeThatFits)
    }
}
