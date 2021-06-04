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
            .foregroundColor(AssetColor.Base.white())
            .padding(.vertical, 4)
            .padding(.horizontal, 12)
            .background(type.backgroundColor)
            .clipShape(
                CutCornerRectangle(
                    targetCorners: [.topLeft, .bottomRight],
                    radius: 8
                )
            )
    }
}

struct Tag_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ForEach(TagType.allCases, id: \.self) { type in
                Tag(type: type, tapAction: {})
                    .frame(width: 103, height: 24)
                    .environment(\.colorScheme, .light)
            }

            ForEach(TagType.allCases, id: \.self) { type in
                Tag(type: type, tapAction: {})
                    .frame(width: 103, height: 24)
                    .environment(\.colorScheme, .dark)
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
            return AssetColor.secondary()
        case .medium:
            return AssetColor.Tag.medium()
        case .youtube:
            return AssetColor.Tag.video()
        }
    }
}
