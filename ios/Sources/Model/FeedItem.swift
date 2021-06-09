import DroidKaigiMPP

public enum FeedItemType: Equatable {
    case blog(Blog)
    case podcast(Podcast)
    case video(Video)
}

public struct FeedItem: Equatable, Identifiable {
    public var id: String
    public var image: Image
    public var link: String
    public var media: Media
    public var publishedAt: Date
    public var summary: MultiLangText
    public var title: MultiLangText
    public var publishedDateString: String?

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
        self.publishedDateString = nil
    }

    public init(from model: DroidKaigiMPP.FeedItem) {
        self.id = model.id
        self.image = Image(from: model.image)
        self.link = model.link
        self.media = Media.from(model.media)
        self.publishedAt = Date(
            timeIntervalSince1970: Double(model.publishedAt.toEpochMilliseconds())
        )
        self.summary = MultiLangText(from: model.summary)
        self.title = MultiLangText(from: model.title)
        self.publishedDateString = model.publishedDateString()
    }
}

