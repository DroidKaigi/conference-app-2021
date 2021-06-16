import Component
import ComposableArchitecture
import Model

public struct MediaState: Equatable {
    var listState: MediaListState?

    public init() {}

    init(listState: MediaListState?) {
        self.listState = listState
    }
}

public struct MediaListState: Equatable {
    var list: MediaList
    var next: Next?

    enum Next: Equatable {
        case searchText(String)
        case more(for: MediaType)
    }
}

public struct MediaList: Equatable {
    var blogs: [Blog]
    var videos: [Video]
    var podcasts: [Podcast]
}

public enum MediaType {
    case blog
    case video
    case podcast
}

public enum MediaAction: Equatable {
    case loadItems
    case itemsLoaded(blogs: [Blog], videos: [Video], podcasts: [Podcast])
    case mediaList(MediaListAction)
}

public enum MediaListAction: Equatable {
    case searchTextDidChange(to: String?)
    case willDismissSearchController
    case showMore(for: MediaType)
}

public struct MediaEnvironment {
    public init() {}
}

let mediaListReducer = Reducer<MediaListState, MediaListAction, Void> { state, action, _ in
    switch action {
    case let .searchTextDidChange(to: searchText):
        switch state.next {
        case nil,
             .searchText:
            state.next = searchText.map { .searchText($0) }
        default:
            break
        }
        return .none
    case .willDismissSearchController:
        if case .searchText = state.next {
            state.next = nil
        }
        return .none
    case let .showMore(mediaType):
        if state.next == nil {
            state.next = .more(for: mediaType)
        }
        return .none
    }
}

public let mediaReducer = Reducer<MediaState, MediaAction, MediaEnvironment>.combine(
    mediaListReducer.optional().pullback(
        state: \.listState,
        action: /MediaAction.mediaList,
        environment: { _ in () }
    ),
    .init { state, action, _ in
        switch action {
        case .loadItems:
            // TODO: Load items from the repository
            return Effect(value: .mockItemsLoads)
                .delay(for: 1, scheduler: DispatchQueue.main)
                .eraseToEffect()
        case let .itemsLoaded(blogs, videos, podcasts):
            let list = MediaList(blogs: blogs, videos: videos, podcasts: podcasts)
            if var listState = state.listState {
                listState.list = list
                state.listState = listState
            } else {
                state.listState = .init(list: list, next: nil)
            }
            return .none
        case .mediaList:
            return .none
        }
    }
)

private extension MediaAction {
    static let mockItemsLoads: Self = {
        let mock = MediaList.mock
        return .itemsLoaded(blogs: mock.blogs, videos: mock.videos, podcasts: mock.podcasts)
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
