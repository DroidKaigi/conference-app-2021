import DroidKaigiMPP

@dynamicMemberLookup
public struct Video: Equatable, Identifiable {
    public var feedItem: FeedItem

    public var id: String {
        feedItem.id
    }

    public init(
        id: String,
        image: Image,
        link: String,
        media: Media,
        publishedAt: Date,
        summary: MultiLangText,
        title: MultiLangText
    ) {
        self.feedItem = .init(
            id: id,
            image: image,
            link: link,
            media: media,
            publishedAt: publishedAt,
            summary: summary,
            title: title
        )
    }

    public init(from model: DroidKaigiMPP.FeedItem.Video) {
        self.feedItem = .init(
            id: model.id,
            image: Image(from: model.image),
            link: model.link,
            media: Media.from(model.media),
            publishedAt: model.publishedAt.toNSDate(),
            summary: MultiLangText(from: model.summary),
            title: MultiLangText(from: model.title)
        )
    }

    public subscript<T>(dynamicMember keyPath: KeyPath<FeedItem, T>) -> T {
        self.feedItem[keyPath: keyPath]
    }
}

public extension Video {
    var kmmModel: DroidKaigiMPP.FeedItem.Video {
        .init(
            id: id,
            publishedAt: ConvertersKt.toKotlinInstant(feedItem.publishedAt),
            image: feedItem.image.kmmModel,
            media: feedItem.media.kmmModel,
            title: feedItem.title.kmmModel,
            summary: feedItem.summary.kmmModel,
            link: feedItem.link
        )
    }
}
