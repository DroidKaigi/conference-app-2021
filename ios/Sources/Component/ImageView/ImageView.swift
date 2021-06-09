import SwiftUI
import Styleguide

public enum PlaceHolder {
    case noImage
    case noImagePodcast

    var image: Image {
        switch self {
        case .noImage:
            return AssetImage.noImage.image
        case .noImagePodcast:
            return AssetImage.noImagePodcast.image
        }
    }

    public enum Size {
        case small
        case medium
        case large

        var frame: CGSize {
            switch self {
            case .small:
                return .init(width: 50, height: 50)
            case .medium:
                return .init(width: 50, height: 50)
            case .large:
                return .init(width: 90, height: 90)
            }
        }
    }
}

public struct ImageView: View {
    private let imageURL: URL?
    private let placeholder: PlaceHolder
    private let placeholderSize: PlaceHolder.Size

    public init(
        imageURL: URL?,
        placeholder: PlaceHolder = .noImage,
        placeholderSize: PlaceHolder.Size
    ) {
        self.imageURL = imageURL
        self.placeholder = placeholder
        self.placeholderSize = placeholderSize
    }

    public var body: some View {
        // TODO: load image from url
        if let imageURL = imageURL {
            Image("")
                .resizable()
                .background(AssetColor.Background.secondary.color.colorScheme(.light))
                .overlay(
                    RoundedRectangle(cornerRadius: 2)
                        .stroke(AssetColor.Separate.image.color, lineWidth: 1)
                )
        } else {
            placeholder.image
                .resizable()
                .frame(
                    width: placeholderSize.frame.width,
                    height: placeholderSize.frame.height,
                    alignment: .center
                )
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(AssetColor.Background.secondary.color.colorScheme(.light))
                .overlay(
                    RoundedRectangle(cornerRadius: 2)
                        .stroke(AssetColor.Separate.image.color, lineWidth: 1)
                )
        }
    }
}

struct ImageView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ImageView(
                imageURL: nil,
                placeholder: .noImage,
                placeholderSize: .large
            )
            .frame(width: 343, height: 190)
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImagePodcast,
                placeholderSize: .large
            )
            .frame(width: 343, height: 190)
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImage,
                placeholderSize: .medium
            )
            .frame(width: 225, height: 114)
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImagePodcast,
                placeholderSize: .medium
            )
            .frame(width: 225, height: 114)
            .previewLayout(.sizeThatFits)


            ImageView(
                imageURL: nil,
                placeholder: .noImage,
                placeholderSize: .small
            )
            .frame(width: 163, height: 114)
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImagePodcast,
                placeholderSize: .small
            )
            .frame(width: 163, height: 114)
            .previewLayout(.sizeThatFits)
        }
    }
}
