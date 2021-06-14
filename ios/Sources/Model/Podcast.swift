import DroidKaigiMPP

@dynamicMemberLookup
public struct Podcast: Equatable, Identifiable {
    public var feedItem: FeedItem

    public var id: String {
        feedItem.id
    }
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
        self.feedItem = .init(
            id: id,
            image: image,
            link: link,
            media: media,
            publishedAt: publishedAt,
            summary: summary,
            title: title
        )
        self.podcastLink = podcastLink
        self.speakers = speakers
    }

    public init(from model: DroidKaigiMPP.FeedItem.Podcast) {
        self.feedItem = .init(
            id: model.id,
            image: Image(from: model.image),
            link: model.link,
            media: Media.from(model.media),
            publishedAt: model.publishedAt.toNSDate(),
            summary: MultiLangText(from: model.summary),
            title: MultiLangText(from: model.title)
        )
        self.podcastLink = model.podcastLink
        self.speakers = model.speakers.map(Speaker.init(from:))
    }

    public subscript<T>(dynamicMember keyPath: KeyPath<FeedItem, T>) -> T {
        self.feedItem[keyPath: keyPath]
    }
}
