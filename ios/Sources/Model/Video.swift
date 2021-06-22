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
        self.id = model.id
        self.image = Image(from: model.image)
        self.link = model.link
        self.media = Media.from(model.media)
        self.publishedAt = model.publishedAt.toNSDate()
        self.summary = MultiLangText(from: model.summary)
        self.title = MultiLangText(from: model.title)
    }
}

public extension Video {
    var kmmModel: DroidKaigiMPP.FeedItem.Video {
        .init(
            id: id,
            publishedAt: ConvertersKt.toKotlinInstant(publishedAt),
            image: image.kmmModel,
            media: media.kmmModel,
            title: title.kmmModel,
            summary: summary.kmmModel,
            link: link
        )
    }

    var _kmmModel: DroidKaigiMPP.FeedItem { kmmModel }  // swiftlint:disable:this identifier_name
}
