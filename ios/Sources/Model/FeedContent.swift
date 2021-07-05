import DroidKaigiMPP

public struct FeedContent: Equatable, Identifiable {
    public var item: AnyFeedItem
    public var isFavorited: Bool
    public var id: String { item.id }

    public init(item: AnyFeedItem, isFavorited: Bool) {
        self.item = item
        self.isFavorited = isFavorited
    }

    public init?(from model: DroidKaigiMPP.KotlinPair<DroidKaigiMPP.FeedItem, KotlinBoolean>) {
        guard let item = model.first.flatMap(AnyFeedItem.from(_:)) else { return nil }
        self.item = item
        self.isFavorited = model.second?.boolValue ?? false
    }
}

extension Array where Element == FeedContent {
    public init(from model: DroidKaigiMPP.FeedContents) {
        self = model.contents.compactMap(FeedContent.init(from:))
    }
}

#if DEBUG
public extension FeedContent {
    static func mock(
        id: String = UUID().uuidString,
        imageURLString: String = "",
        link: String = "",
        media: Media = .medium,
        publishedAt: Date = Date(timeIntervalSince1970: 0),
        summary: String = "",
        title: String = "DroidKaigi 2021とその他活動予定についてのお知らせ"
    ) -> FeedContent {
        .init(
            item: .init(
                Video(
                    id: id,
                    image: .init(largeURLString: imageURLString, smallURLString: "", standardURLString: ""),
                    link: link,
                    media: media,
                    publishedAt: publishedAt,
                    summary: .init(enTitle: summary, jaTitle: summary),
                    title: .init(enTitle: title, jaTitle: title)
                )
            ),
            isFavorited: false
        )
    }
}
#endif
