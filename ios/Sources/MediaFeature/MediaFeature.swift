import Component
import ComposableArchitecture
import Model

public struct MediaState: Equatable {
    var listState: MediaListState?

    public init() {}
}

public struct MediaListState: Equatable {
    var list: MediaList
    var searchText: String?
}

public enum MediaAction: Equatable {
    case loadItems
    case itemsLoads(blogs: [Blog], videos: [Video], podcasts: [Podcast])
    case searchTextDidChange(to: String?)
}

public struct MediaList: Equatable {
    var blogs: [Blog]
    var videos: [Video]
    var podcasts: [Podcast]
}

public let mediaReducer = Reducer<MediaState, MediaAction, Void> { state, action, _ in
    switch action {
    case .loadItems:
        // TODO: Load items from the repository
        return Effect(value: .mockItemsLoads)
            .delay(for: 1, scheduler: DispatchQueue.main)
            .eraseToEffect()
    case let .itemsLoads(blogs, videos, podcasts):
        state.listState = .init(list: .init(blogs: blogs, videos: videos, podcasts: podcasts), searchText: nil)
        return .none
    case let .searchTextDidChange(to: searchText):
        state.listState?.searchText = searchText
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
                title: .init(enTitle: "", jaTitle: "DroidKaigi 2020でのCodelabsについて"),
                author: .init(link: "", name: ""),
                language: ""
            ),
            .init(
                id: "1",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .medium,
                publishedAt: .init(),
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Codelabs"),
                author: .init(link: "", name: ""),
                language: ""
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
                podcastLink: "",
                publishedAt: .init(),
                speakers: [],
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "2. Android 11 Talks")
            ),
            .init(
                id: "1",
                image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                link: "",
                media: .droidkaigifm,
                podcastLink: "",
                publishedAt: .init(),
                speakers: [],
                summary: .init(enTitle: "", jaTitle: ""),
                title: .init(enTitle: "", jaTitle: "5. Notificiationよもやま話")
            ),
        ]
    )
}
