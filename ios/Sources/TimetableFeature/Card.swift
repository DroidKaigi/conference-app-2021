import Component
import Model
import Styleguide
import SwiftUI

public struct TimetableCard: View {
    private let item: TimetableItem

    public init(item: TimetableItem) {
        self.item = item
    }

    private var isSpecial: Bool {
        item.type == .special
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(item.lang.title)
                .font(.caption2)
                .padding(.horizontal, 8)
                .padding(.vertical, 4)
                .foregroundColor(isSpecial ? AssetColor.Base.white.color : AssetColor.Base.secondary.color)
                .background(isSpecial ? AssetColor.Separate.navigation.color : AssetColor.Background.secondary.color)
                .clipShape(Capsule())
            Text(item.title.jaTitle)
                .lineLimit(3)
                .font(.subheadline)
                .foregroundColor(isSpecial ? AssetColor.Base.white.color : AssetColor.Base.primary.color)
            Text("#\(item.category)")
                .font(.caption2)
                .foregroundColor(isSpecial ? AssetColor.Base.white.color : AssetColor.Base.secondary.color)
            VStack {
                ForEach(item.speakers) { speaker in
                    HStack {
                        AvatarView(
                            avatarImageURL: URL(string: speaker.iconURLString),
                            style: .small
                        )
                        Text(speaker.name)
                            .font(.caption)
                            .foregroundColor(isSpecial ? AssetColor.Base.white.color : AssetColor.Base.secondary.color)
                    }
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .padding(.horizontal, 12)
        .padding(.vertical, 16)
        .foregroundColor(isSpecial ? AssetColor.Base.white.color : AssetColor.Base.primary.color)
        .background(isSpecial ? AssetColor.secondary.color : AssetColor.Background.contents.color)
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(Color.black, lineWidth: isSpecial ? 0 : 1)
        )
    }
}

private extension Lang {
    var title: String {
        switch self {
        case .ja:
            return L10n.TimetableScreen.Card.Lang.japanese
        case .en:
            return L10n.TimetableScreen.Card.Lang.english
        }
    }
}

#if DEBUG
public struct TimetableCard_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            Group {
                TimetableCard(
                    item: .mock(
                        type: .session
                    )
                )
                .frame(width: 300, height: 250, alignment: .center)
                .environment(\.colorScheme, colorScheme)
                .previewLayout(.sizeThatFits)
                TimetableCard(
                    item: .mock(
                        type: .special
                    )
                )
                .frame(width: 300, height: 250, alignment: .center)
                .environment(\.colorScheme, colorScheme)
                .previewLayout(.sizeThatFits)
                TimetableCard(
                    item: .mock(
                        type: .session,
                        speakers: [
                            .mock(name: "Speaker 1"),
                            .mock(name: "Speaker 2"),
                        ]
                    )
                )
                .frame(width: 300, height: 250, alignment: .center)
                .environment(\.colorScheme, colorScheme)
                .previewLayout(.sizeThatFits)
                TimetableCard(
                    item: .mock(
                        type: .special,
                        speakers: [
                            .mock(name: "Speaker 1"),
                            .mock(name: "Speaker 2"),
                        ]
                    )
                )
                .frame(width: 300, height: 250, alignment: .center)
                .environment(\.colorScheme, colorScheme)
                .previewLayout(.sizeThatFits)
            }
        }
    }
}
#endif
