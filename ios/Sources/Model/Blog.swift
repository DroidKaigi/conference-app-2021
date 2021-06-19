import DroidKaigiMPP

@dynamicMemberLookup
public struct Blog: Equatable, Identifiable {
    public var feedItem: FeedItem
    public var author: Author
    public var language: String

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
        title: MultiLangText,
        author: Author,
        language: String
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
        self.author = author
        self.language = language
    }

    public init(from model: DroidKaigiMPP.FeedItem.Blog) {
        self.feedItem = .init(
            id: model.id,
            image: Image(from: model.image),
            link: model.link,
            media: Media.from(model.media),
            publishedAt: model.publishedAt.toNSDate(),
            summary: MultiLangText(from: model.summary),
            title: MultiLangText(from: model.title)
        )
        self.author = Author(from: model.author)
        self.language = model.language
    }

    public subscript<T>(dynamicMember keyPath: KeyPath<FeedItem, T>) -> T {
        self.feedItem[keyPath: keyPath]
    }
}

public extension Blog {
    var kmmModel: DroidKaigiMPP.FeedItem.Blog {
        .init(
            id: id,
            publishedAt: ConvertersKt.toKotlinInstant(feedItem.publishedAt),
            image: feedItem.image.kmmModel,
            media: feedItem.media.kmmModel,
            title: feedItem.title.kmmModel,
            summary: feedItem.summary.kmmModel,
            link: feedItem.link,
            language: language,
            author: author.kmmModel
        )
    }
}
