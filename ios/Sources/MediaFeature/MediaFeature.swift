import Component
import ComposableArchitecture
import Model

public enum MediaState: Equatable {

    case needToInitialize
    case initialized(MediaListState)

    public init() {
        self = .needToInitialize
    }
}

public struct MediaListState: Equatable {

    enum Next: Equatable {
        case searchText(String)
        case more(for: MediaType)
    }

    var blogs: [FeedContent]
    var videos: [FeedContent]
    var podcasts: [FeedContent]
    var next: Next?
}

public enum MediaType {
    case blog
    case video
    case podcast
}

public enum MediaAction: Equatable {
    case loadItems
    case itemsLoaded([FeedContent])
    case mediaList(MediaListAction)
}

public enum MediaListAction: Equatable {
    case searchTextDidChange(to: String?)
    case willDismissSearchController
    case showMore(for: MediaType)
    case moreDismissed
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
    case .moreDismissed:
        if case .more = state.next {
            state.next = nil
        }
        return .none
    }
}

public let mediaReducer = Reducer<MediaState, MediaAction, MediaEnvironment>.combine(
    mediaListReducer.pullback(
        state: /MediaState.initialized,
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
        case let .itemsLoaded(contents):
            var blogs: [FeedContent] = .init()
            var videos: [FeedContent] = .init()
            var podcasts: [FeedContent] = .init()
            for content in contents {
                switch content.item {
                case .blog:
                    blogs.append(content)
                case .podcast:
                    videos.append(content)
                case .video:
                    podcasts.append(content)
                }
            }
            if var listState = (/MediaState.initialized).extract(from: state) {
                listState.blogs = blogs
                listState.videos = videos
                listState.podcasts = podcasts
                state = .initialized(listState)
            } else {
                state = .initialized(.init(blogs: blogs, videos: videos, podcasts: podcasts, next: nil))
            }
            return .none
        case .mediaList:
            return .none
        }
    }
)

private extension MediaAction {
    static let mockItemsLoads: Self = .itemsLoaded(.mockBlogs + .mockVideos + .mockPodcasts)
}

extension MediaListState {
    static let mock: Self = .init(blogs: .mockBlogs, videos: .mockVideos, podcasts: .mockPodcasts, next: nil)
}

extension Array where Element == FeedContent {
    static let mockBlogs: Self = [
        .blog(.init(
            id: "0",
            image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
            link: "",
            media: .medium,
            publishedAt: .init(),
            summary: .init(enTitle: "", jaTitle: ""),
            title: .init(enTitle: "", jaTitle: "DroidKaigi 2020でのCodelabsについて"),
            author: .init(link: "", name: ""),
            language: ""
        )),
        .blog(.init(
            id: "1",
            image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
            link: "",
            media: .medium,
            publishedAt: .init(),
            summary: .init(enTitle: "", jaTitle: ""),
            title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Codelabs"),
            author: .init(link: "", name: ""),
            language: ""
        )),
    ]
    .map { .init(item: $0, isFavorited: false) }

    static let mockVideos: Self = [
        .video(.init(
            id: "0",
            image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
            link: "",
            media: .youtube,
            publishedAt: .init(),
            summary: .init(enTitle: "", jaTitle: ""),
            title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Lite - KotlinのDelegated Propertiesを活用してAndroidアプリ開発をもっと便利にする / chibatching [JA]")
        )),
        .video(.init(
            id: "1",
            image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
            link: "",
            media: .youtube,
            publishedAt: .init(),
            summary: .init(enTitle: "", jaTitle: ""),
            title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Lite - Day 2 Night Session")
        )),
    ]
    .map { .init(item: $0, isFavorited: false) }

    static let mockPodcasts: Self = [
        .podcast(.init(
            id: "0",
            image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
            link: "",
            media: .droidKaigiFm,
            podcastLink: "",
            publishedAt: .init(),
            speakers: [],
            summary: .init(enTitle: "", jaTitle: ""),
            title: .init(enTitle: "", jaTitle: "2. Android 11 Talks")
        )),
        .podcast(.init(
            id: "1",
            image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
            link: "",
            media: .droidKaigiFm,
            podcastLink: "",
            publishedAt: .init(),
            speakers: [],
            summary: .init(enTitle: "", jaTitle: ""),
            title: .init(enTitle: "", jaTitle: "5. Notificiationよもやま話")
        )),
    ]
    .map { .init(item: $0, isFavorited: false) }
}
