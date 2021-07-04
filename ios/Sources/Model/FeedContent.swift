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
    static func blogMock(id: UUID = UUID()) -> Self {
        .init(
            item: AnyFeedItem(
                Blog(
                    id: id.uuidString,
                    image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                    link: "",
                    media: .medium,
                    publishedAt: Date(timeIntervalSince1970: 0),
                    summary: .init(enTitle: "", jaTitle: ""),
                    title: .init(enTitle: "", jaTitle: "DroidKaigi 2020でのCodelabsについて"),
                    author: .init(link: "", name: ""),
                    language: ""
                )
            ),
            isFavorited: false
        )
    }

    static func podcastMock(id: UUID = UUID()) -> Self {
        .init(
            item: AnyFeedItem(
                Podcast(
                    id: id.uuidString,
                    image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                    link: "",
                    media: .droidKaigiFm,
                    podcastLink: "",
                    publishedAt: Date(timeIntervalSince1970: 0),
                    speakers: [],
                    summary: .init(enTitle: "", jaTitle: ""),
                    title: .init(enTitle: "", jaTitle: "2. Android 11 Talks")
                )
            ),
            isFavorited: false
        )
    }

    static func videoMock(id: UUID = UUID()) -> Self {
        .init(
            item: AnyFeedItem(
                Video(
                    id: id.uuidString,
                    image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                    link: "",
                    media: .youtube,
                    publishedAt: Date(timeIntervalSince1970: 0),
                    summary: .init(enTitle: "", jaTitle: ""),
                    title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Lite - KotlinのDelegated Propertiesを活用してAndroidアプリ開発をもっと便利にする / chibatching [JA]")
                )
            ),
            isFavorited: false
        )
    }
}
#endif
