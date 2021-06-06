import SwiftUI
import Styleguide

public struct ImageView: View {
    private let imageURL: URL?

    public init(
        imageURL: URL?
    ) {
        self.imageURL = imageURL
    }

    public var body: some View {
        // TODO: add placeholder image
        Image("")
            .resizable()
            .background(AssetColor.Base.secondary.color)
            .overlay(
                RoundedRectangle(cornerRadius: 2)
                    .stroke(AssetColor.Separate.image.color, lineWidth: 1)
            )
    }
}

struct ImageView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ImageView(imageURL: URL(string: ""))
            ImageView(imageURL: URL(string: ""))
            ImageView(imageURL: URL(string: ""))
        }
        .previewLayout(.sizeThatFits)
    }
}
