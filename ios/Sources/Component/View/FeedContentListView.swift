import Model
import SwiftUI

public struct FeedContentListView: View {
    private var feedContents: [FeedContent]
    private let tapContent: (FeedContent) -> Void
    private let tapFavorite: (_ isFavorited: Bool, _ id: String) -> Void

    public init(
        feedContents: [FeedContent],
        tapContent: @escaping (FeedContent) -> Void,
        tapFavorite: @escaping (_ isFavorited: Bool, _ id: String) -> Void
    ) {
        self.feedContents = feedContents
        self.tapContent = tapContent
        self.tapFavorite = tapFavorite
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
                    }
                )
            }
        }
    }
}

extension FeedContentListView {
    private func createCard(
        content: FeedContent,
        tapAction: @escaping () -> Void,
        tapFavoritesAction: @escaping () -> Void
    ) -> some View {
        SmallCard(
            title: content.item.title.jaTitle,
            imageURL: URL(string: content.item.image.smallURLString),
            tag: content.item.media,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoritesAction
        )
    }
}

#if DEBUG
public struct FeedContentListView_Previews: PreviewProvider {
    public static var previews: some View {
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
            tapFavorite: { _, _ in }
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .light)

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
            tapFavorite: { _, _ in }
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .dark)
    }
}
#endif
