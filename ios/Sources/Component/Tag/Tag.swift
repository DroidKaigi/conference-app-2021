import SwiftUI
import Styleguide

public struct Tag: View {
    private let type: TagType
    private let tapAction: () -> Void

    public init(
        type: TagType,
        tapAction: @escaping () -> Void
    ) {
        self.type = type
        self.tapAction = tapAction
    }

    public var body: some View {
        Text(type.title)
            .font(.caption)
            .foregroundColor(Color(AssetColor.Base.white.color))
            .padding(.vertical, 4)
            .padding(.horizontal, 12)
            .background(type.backgroundColor)
            .clipShape(CutCornerRectangle(radius: 8))
    }
}

struct Tag_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ForEach(TagType.allCases, id: \.self) { type in
                Tag(type: type, tapAction: {})
                    .frame(width: 103, height: 24)
            }
        }
        .previewLayout(.sizeThatFits)
    }
}

private extension TagType {
    var title: String {
        switch self {
        case .droidKaigiFm:
            return L10n.Component.Tag.droidKaigiFm
        case .medium:
            return L10n.Component.Tag.medium
        case .youtube:
            return L10n.Component.Tag.youtube
        }
    }

    var backgroundColor: Color {
        switch self {
        case .droidKaigiFm:
            return Color(AssetColor.secondary.color)
        case .medium:
            return Color(AssetColor.Tag.medium.color)
        case .youtube:
            return Color(AssetColor.Tag.video.color)
        }
    }
}
