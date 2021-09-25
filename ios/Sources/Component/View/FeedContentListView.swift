import Model
import Styleguide
import SwiftUI

public struct FeedContentListView: View {
    private var feedContents: [FeedContent]
    private let tapContent: (FeedContent) -> Void
    private let tapFavorite: (_ isFavorited: Bool, _ id: String) -> Void
    private let tapPlay: (FeedContent) -> Void

    public init(
        feedContents: [FeedContent],
        tapContent: @escaping (FeedContent) -> Void,
        tapFavorite: @escaping (_ isFavorited: Bool, _ id: String) -> Void,
        tapPlay: @escaping (FeedContent) -> Void
    ) {
        self.feedContents = feedContents
        self.tapContent = tapContent
        self.tapFavorite = tapFavorite
        self.tapPlay = tapPlay
    }

    public var body: some View {
        LazyVGrid(
            columns: Array(
                repeating: GridItem(.flexible(), spacing: .zero),
                count: 2
            ),
            spacing: 16
        ) {
            ForEach(feedContents) { content in
                createCard(
                    content: content,
                    tapAction: {
                        tapContent(content)
                    },
                    tapFavoritesAction: {
                        tapFavorite(content.isFavorited, content.id)
                    },
                    tapPlayAction: {

                    }
                )
            }
        }
        .padding(.horizontal, SmallCard.Const.margin)
    }
}

extension FeedContentListView {
    private func createCard(
        content: FeedContent,
        tapAction: @escaping () -> Void,
        tapFavoritesAction: @escaping () -> Void,
        tapPlayAction: @escaping () -> Void
    ) -> some View {
        SmallCard(
            title: content.item.title.jaTitle,
            imageURL: URL(string: content.item.image.smallURLString),
            media: content.item.media,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoritesAction,
            tapPlay: tapPlayAction
        )
    }
}

#if DEBUG
public struct FeedContentListView_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            FeedContentListView(
                feedContents: [
                    .blogMock(),
                    .blogMock(),
                    .videoMock(),
                    .videoMock(),
                    .podcastMock(),
                    .podcastMock()
                ],
                tapContent: { _ in },
                tapFavorite: { _, _ in },
                tapPlay: { _ in }
            )
            .background(AssetColor.Background.primary.color)
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)
        }
    }
}
#endif
