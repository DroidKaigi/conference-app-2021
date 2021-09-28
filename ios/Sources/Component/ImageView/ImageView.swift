import Nuke
import NukeUI
import SwiftUI
import Styleguide

public enum PlaceHolder {
    case noImage
    case noImagePodcast

    var image: SwiftUI.Image {
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
    enum Const {
        static let roundedLineWidth: CGFloat = 1
    }

    private let imageURL: URL?
    private let placeholder: PlaceHolder
    private let placeholderSize: PlaceHolder.Size
    private let width: CGFloat
    private let height: CGFloat
    private let allowsHitTesting: Bool

    public init(
        imageURL: URL?,
        placeholder: PlaceHolder = .noImage,
        placeholderSize: PlaceHolder.Size,
        width: CGFloat,
        height: CGFloat,
        allowsHitTesting: Bool = true
    ) {
        self.imageURL = imageURL
        self.placeholder = placeholder
        self.placeholderSize = placeholderSize
        self.width = width
        self.height = height
        self.allowsHitTesting = allowsHitTesting
    }

    public var body: some View {
        LazyImage(source: imageURL) { state in
            if let image = state.image {
                image
                    .resizingMode(.aspectFill)
                    .frame(width: width, height: height)
                    .clipped()
            } else if state.error != nil {
                placeholderView
            } else {
                placeholderView
            }
        }
        .frame(width: width, height: height)
        .clipShape(RoundedRectangle(cornerRadius: 20))
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(
                    AssetColor.Separate.image.color,
                    lineWidth: Const.roundedLineWidth
                )
        )
        .allowsHitTesting(allowsHitTesting)
    }
}

extension ImageView {
    private var placeholderView: some View {
        placeholder.image
            .resizable()
            .frame(
                width: placeholderSize.frame.width,
                height: placeholderSize.frame.height
            )
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(AssetColor.Background.secondary.color.colorScheme(.light))
    }
}

public struct ImageView_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            ImageView(
                imageURL: nil,
                placeholder: .noImage,
                placeholderSize: .large,
                width: 343,
                height: 190
            )
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImagePodcast,
                placeholderSize: .large,
                width: 343,
                height: 190
            )
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImage,
                placeholderSize: .medium,
                width: 225,
                height: 114
            )
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImagePodcast,
                placeholderSize: .medium,
                width: 225,
                height: 114
            )
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImage,
                placeholderSize: .small,
                width: 163,
                height: 114
            )
            .previewLayout(.sizeThatFits)

            ImageView(
                imageURL: nil,
                placeholder: .noImagePodcast,
                placeholderSize: .small,
                width: 163,
                height: 114
            )
            .previewLayout(.sizeThatFits)
        }
    }
}
