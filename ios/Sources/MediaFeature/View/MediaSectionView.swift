import Component
import ComposableArchitecture
import Model
import SwiftUI
import Styleguide

public struct MediaSectionView: View {
    let type: MediaType
    let feedContent: [FeedContent]
    let moreAction: () -> Void
    let tapAction: (FeedContent) -> Void
    let tapFavoriteAction: (_ isFavorited: Bool, _ id: String) -> Void

    public var body: some View {
        VStack(spacing: .zero) {
            MediaSectionHeader(
                type: type,
                moreAction: moreAction
            )
            ScrollView(.horizontal, showsIndicators: false) {
                LazyHStack(spacing: .zero) {
                    ForEach(feedContent) { content in
                        MediumCard(
                            content: content,
                            tapAction: {
                                tapAction(content)
                            },
                            tapFavoriteAction: {
                                tapFavoriteAction(content.isFavorited, content.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

private extension MediumCard {
    init(
        content: FeedContent,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        self.init(
            title: content.item.title.jaTitle,
            imageURL: URL(string: content.item.image.largeURLString),
            media: content.item.media,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoriteAction
        )
    }
}

#if DEBUG
public struct MediaSectionView_Previews: PreviewProvider {
    public static var previews: some View {
        let sizeCategories: [ContentSizeCategory] = [
            .large, // Default
            .extraExtraExtraLarge
        ]
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            ForEach(sizeCategories, id: \.self) { sizeCategory in
                MediaSectionView(
                    type: .blog,
                    feedContent: [
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock()
                    ],
                    moreAction: {},
                    tapAction: { _ in },
                    tapFavoriteAction: { _, _ in }
                )
                .background(AssetColor.Background.primary.color)
                .environment(\.sizeCategory, sizeCategory)
                .environment(\.colorScheme, colorScheme)
            }
        }
        .frame(width: 375, height: 301)
        .previewLayout(.sizeThatFits)
        .accentColor(AssetColor.primary.color)
    }
}
#endif
