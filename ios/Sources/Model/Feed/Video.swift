import DroidKaigiMPP

public struct Video: FeedItem, Equatable {
    public var id: String
    public var image: Image
    public var link: String
    public var media: Media
    public var publishedAt: Date
    public var summary: MultiLangText
    public var title: MultiLangText

    public init(
        id: String,
        image: Image,
        link: String,
        media: Media,
        publishedAt: Date,
        summary: MultiLangText,
        title: MultiLangText
    ) {
        self.id = id
        self.image = image
        self.link = link
        self.media = media
        self.publishedAt = publishedAt
        self.summary = summary
        self.title = title
    }

    public init(from model: DroidKaigiMPP.FeedItem.Video) {
        self.id = model.id as? String ?? ""  // non-null Kotlin String is always passed
        self.image = Image(from: model.image)
        self.link = model.link
        self.media = Media.from(model.media)
        self.publishedAt = model.publishedAt.toNSDate()
        self.summary = MultiLangText(from: model.summary)
        self.title = MultiLangText(from: model.title)
    }
}
