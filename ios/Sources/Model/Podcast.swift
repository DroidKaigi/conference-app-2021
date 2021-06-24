import DroidKaigiMPP

public struct Podcast: FeedItem, Equatable {
    public var id: String
    public var image: Image
    public var link: String
    public var media: Media
    public var publishedAt: Date
    public var summary: MultiLangText
    public var title: MultiLangText
    public var podcastLink: String
    public var speakers: [Speaker]

    public init(
        id: String,
        image: Image,
        link: String,
        media: Media,
        podcastLink: String,
        publishedAt: Date,
        speakers: [Speaker],
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
        self.podcastLink = podcastLink
        self.speakers = speakers
    }

    public init(from model: DroidKaigiMPP.FeedItem.Podcast) {
        self.id = model.id
        self.image = Image(from: model.image)
        self.link = model.link
        self.media = Media.from(model.media)
        self.publishedAt = model.publishedAt.toNSDate()
        self.summary = MultiLangText(from: model.summary)
        self.title = MultiLangText(from: model.title)
        self.podcastLink = model.podcastLink
        self.speakers = model.speakers.map(Speaker.init(from:))
    }
}
