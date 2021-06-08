import SwiftUI
import Styleguide

public struct ImageView: View {
    enum placeholderType {
        case noImage
        case noImagePodcast
    }
    private let imageURL: URL?
    private let placeholderType: placeholderType

    public init(
        imageURL: URL?
        placeholderType: placeholderType = .noImage
    ) {
        self.imageURL = imageURL
        self.placeholder = placeholder
    }

    public var body: some View {
        // TODO: load image from url
        let image = imageURL != nil ? Image("") : placeholder.image

        image
            .resizable()
            .background(AssetColor.Background.secondary.color)
            .overlay(
                RoundedRectangle(cornerRadius: 2)
                    .stroke(AssetColor.Separate.image.color, lineWidth: 1)
            )
    }
}

extension ImageView.placeholderType {
    private var image: Image {
        switch self {
        case .noImage:
            return AssetImage.noImage.image
        case .noImagePodcast:
            return AssetImage.noImagePodcast.image
        }
    }
}

struct ImageView_Previews: PreviewProvider {
    static var previews: some View {
        ImageView(imageURL: URL(string: ""))
            .previewLayout(.sizeThatFits)
    }
}
