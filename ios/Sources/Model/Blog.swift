import DroidKaigiMPP

public struct Blog: FeedItem, Equatable {
    public var id: String
    public var image: Image
    public var link: String
    public var media: Media
    public var publishedAt: Date
    public var summary: MultiLangText
    public var title: MultiLangText
    public var author: Author
    public var language: String

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
        self.id = id
        self.image = image
        self.link = link
        self.media = media
        self.publishedAt = publishedAt
        self.summary = summary
        self.title = title
        self.author = author
        self.language = language
    }

    public init(from model: DroidKaigiMPP.FeedItem.Blog) {
        self.id = model.id
        self.image = Image(from: model.image)
        self.link = model.link
        self.media = Media.from(model.media)
        self.publishedAt = model.publishedAt.toNSDate()
        self.summary = MultiLangText(from: model.summary)
        self.title = MultiLangText(from: model.title)
        self.author = Author(from: model.author)
        self.language = model.language
    }
}

public extension Blog {
    var kmmModel: DroidKaigiMPP.FeedItem {
        DroidKaigiMPP.FeedItem.Blog(
            id: id,
            publishedAt: ConvertersKt.toKotlinInstant(publishedAt),
            image: image.kmmModel,
            media: media.kmmModel,
            title: title.kmmModel,
            summary: summary.kmmModel,
            link: link,
            language: language,
            author: author.kmmModel
        )
    }
}
