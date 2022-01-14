import Model
import SwiftUI
import Styleguide

public struct Tag: View {
    private let media: Media

    public init(
        media: Media
    ) {
        self.media = media
    }

    public var body: some View {
        Text(media.title)
            .font(.caption)
            .foregroundColor(AssetColor.Base.white.color)
            .background(media.backgroundColor)
            .clipShape(
                CutCornerRectangle(
                    targetCorners: [.topLeft, .bottomRight],
                    radius: 8
                )
            )
            .minimumScaleFactor(0.1)
    }
}

public struct Tag_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            ForEach(Media.allCases, id: \.self) { media in
                Tag(media: media)
                    .frame(width: 103, height: 24)
                    .environment(\.colorScheme, .light)
            }

            ForEach(Media.allCases, id: \.self) { media in
                Tag(media: media)
                    .frame(width: 103, height: 24)
                    .environment(\.colorScheme, .dark)
            }
        }
        .previewLayout(.sizeThatFits)
    }
}

private extension Media {
    var title: String {
        switch self {
        case .droidKaigiFm:
            return L10n.Component.Tag.droidKaigiFm
        case .medium:
            return L10n.Component.Tag.medium
        case .youtube:
            return L10n.Component.Tag.youtube
        case .other:
            return L10n.Component.Tag.other
        }
    }

    var backgroundColor: Color {
        switch self {
        case .droidKaigiFm:
            return AssetColor.secondary.color
        case .medium:
            return AssetColor.Tag.medium.color
        case .youtube:
            return AssetColor.Tag.video.color
        case .other:
            return AssetColor.Tag.other.color
        }
    }
}
