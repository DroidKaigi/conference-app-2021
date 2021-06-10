import Styleguide
import SwiftUI

public struct AvatarView: View {
    public enum Size: CGFloat {
        // Avatar size - border width * 2
        case large = 58
        case small = 22

        public var placeholderImageSize: CGFloat {
            switch self {
            case .large:
                return 44
            case .small:
                return 16
            }
        }
    }

    private let avatarImageURL: URL?
    private let size: Size

    public init(
        avatarImageURL: URL?,
        size: Size = .large
    ) {
        self.avatarImageURL = avatarImageURL
        self.size = size
    }

    public var body: some View {
        // TODO: Replace with lazy loaded image component
        if let avatarImageURL = avatarImageURL {
            Image("")
                .resizable()
                .frame(width: size.rawValue, height: size.rawValue)
                .background(AssetColor.Background.secondary.color.colorScheme(.light))
                .overlay(
                    Circle()
                        .stroke(AssetColor.Separate.image.color, lineWidth: 1)
                )
                .clipShape(Circle())
                .background(Color.clear)
        } else {
            AssetImage.noImage.image
                .resizable()
                .frame(width: size.placeholderImageSize, height: size.placeholderImageSize)
                .frame(width: size.rawValue, height: size.rawValue)
                .background(AssetColor.Background.secondary.color.colorScheme(.light))
                .clipShape(Circle())
                .overlay(
                    Circle()
                        .stroke(AssetColor.Separate.image.color, lineWidth: 1)
                )
                .background(Color.clear)
        }
    }
}

struct SwiftUIView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            AvatarView(avatarImageURL: URL(string: ""), size: .large)
            AvatarView(avatarImageURL: URL(string: ""), size: .small)
                .frame(width: 24, height: 24, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
            AvatarView(avatarImageURL: URL(string: "https://example.com"), size: .large)
            AvatarView(avatarImageURL: URL(string: "https://example.com"), size: .small)
        }
        .previewLayout(.sizeThatFits)
    }
}
