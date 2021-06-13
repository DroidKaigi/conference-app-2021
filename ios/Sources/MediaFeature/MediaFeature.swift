import Component
import ComposableArchitecture
import Model

public struct MediaState: Equatable {

    public var blogs: [FeedItem]
    public var videos: [FeedItem]
    public var podcasts: [FeedItem]

    public init(blogs: [FeedItem], videos: [FeedItem], podcasts: [FeedItem]) {
        self.blogs = blogs
        self.videos = videos
        self.podcasts = podcasts
    }
}

public enum MediaAction: Equatable {

}

public extension MediaState {
    static let mock: Self = .init(
        blogs: [
            .init(
                id: "0",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .medium,
                publishedAt: .init(),
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "DroidKaigi 2020でのCodelabsについて")
            ),
            .init(
                id: "1",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .medium,
                publishedAt: .init(),
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Codelabs")
            ),
        ],
        videos: [
            .init(
                id: "0",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .youtube,
                publishedAt: .init(),
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Lite - KotlinのDelegated Propertiesを活用してAndroidアプリ開発をもっと便利にする / chibatching [JA]")
            ),
            .init(
                id: "1",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .youtube,
                publishedAt: .init(),
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Lite - Day 2 Night Session")
            ),
        ],
        podcasts: [
            .init(
                id: "0",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .droidkaigifm,
                publishedAt: .init(),
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "2. Android 11 Talks")
            ),
            .init(
                id: "1",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .droidkaigifm,
                publishedAt: .init(),
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "5. Notificiationよもやま話")
            ),
        ]
    )
}
