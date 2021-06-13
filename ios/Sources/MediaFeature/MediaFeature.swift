import Component
import ComposableArchitecture
import Model

public struct MediaState: Equatable {
    var mediaList: MediaList?

    public init() {}
}

public enum MediaAction: Equatable {
    case loadItems
    case itemsLoads(blogs: [FeedItem], videos: [FeedItem], podcasts: [FeedItem])
}

public struct MediaList: Equatable {
    var blogs: [FeedItem]
    var videos: [FeedItem]
    var podcasts: [FeedItem]
}

public let mediaReducer = Reducer<MediaState, MediaAction, Void> { state, action, _ in
    switch action {
    case .loadItems:
        // TODO: Load items from the repository
        return Effect(value: .mockItemsLoads)
            .delay(for: 1, scheduler: DispatchQueue.main)
            .eraseToEffect()
    case let .itemsLoads(blogs, videos, podcasts):
        state.mediaList = .init(blogs: blogs, videos: videos, podcasts: podcasts)
        return .none
    }
}

private extension MediaAction {
    static let mockItemsLoads: Self = {
        let mock = MediaList.mock
        return .itemsLoads(blogs: mock.blogs, videos: mock.videos, podcasts: mock.podcasts)
    }()
}

extension MediaList {
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
