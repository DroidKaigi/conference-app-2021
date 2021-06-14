import NukeUI
import Styleguide
import SwiftUI

public struct AvatarView: View {
    public enum Style {
        case large
        case small

        // Avatar size - border width * 2
        var size: CGFloat {
            switch self {
            case .large:
                return 58
            case .small:
                return 22
            }
        }

        var placeholderImageSize: CGFloat {
            switch self {
            case .large:
                return 44
            case .small:
                return 16
            }
        }
    }

    private let avatarImageURL: URL?
    private let style: Style

    public init(
        avatarImageURL: URL?,
        style: Style = .large
    ) {
        self.avatarImageURL = avatarImageURL
        self.style = style
    }

    public var body: some View {
        LazyImage(source: avatarImageURL) { state in
            if let image = state.image {
                image
            } else if state.error != nil {
                placeholderView
            } else {
                placeholderView
            }
        }
        .frame(width: style.size, height: style.size)
        .background(AssetColor.Background.secondary.color.colorScheme(.light))
        .overlay(
            Circle()
                .stroke(AssetColor.Separate.image.color, lineWidth: 1)
        )
        .clipShape(Circle())
        .background(Color.clear)
    }
}

extension AvatarView {
    private var placeholderView: some View {
        AssetImage.noImage.image
            .resizable()
            .frame(width: style.placeholderImageSize, height: style.placeholderImageSize)
    }
}

struct AvatarView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            // Failed
            AvatarView(avatarImageURL: nil, style: .large)
            AvatarView(avatarImageURL: nil, style: .small)
            // Placeholder
            AvatarView(avatarImageURL: URL(string: "https://example.com"), style: .large)
            AvatarView(avatarImageURL: URL(string: "https://example.com"), style: .small)
            // Success
            AvatarView(avatarImageURL: URL(string: "https://github.com/DroidKaigi.png"), style: .large)
            AvatarView(avatarImageURL: URL(string: "https://github.com/DroidKaigi.png"), style: .small)
        }
        .previewLayout(.sizeThatFits)
    }
}
