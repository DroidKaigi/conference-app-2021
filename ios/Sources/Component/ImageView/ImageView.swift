import SwiftUI
import Styleguide

public struct ImageView: View {
    private let imageURL: URL?
    private let width: CGFloat
    private let height: CGFloat

    public init(
        imageURL: URL?,
        width: CGFloat,
        height: CGFloat
    ) {
        self.imageURL = imageURL
        self.width = width
        self.height = height
    }

    public var body: some View {
        // TODO: add placeholder image
        Image("")
            .background(Color(AssetColor.Base.secondary.color))
            .frame(width: width, height: height, alignment: .center)
            .overlay(
                RoundedRectangle(cornerRadius: 2)
                    .stroke(Color(AssetColor.Separate.image.color), lineWidth: 1)
            )
    }
}

struct ImageView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ImageView(imageURL: URL(string: ""), width: 343, height: 190)
            ImageView(imageURL: URL(string: ""), width: 225, height: 114)
            ImageView(imageURL: URL(string: ""), width: 163, height: 114)
        }
        .previewLayout(.sizeThatFits)
    }
}
