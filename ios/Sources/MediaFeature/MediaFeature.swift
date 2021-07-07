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
        case isEditingDidChange(Bool)
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
    case showSetting
}

public enum MediaListAction: Equatable {
    case searchTextDidChange(to: String?)
    case isEditingDidChange(to: Bool)
    case showMore(for: MediaType)
    case moreDismissed
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
}

public struct MediaEnvironment {
    public init() {}
}

let mediaListReducer = Reducer<MediaListState, MediaListAction, Void> { state, action, _ in
    switch action {
    case let .searchTextDidChange(to: searchText):
        switch state.next {
        case nil, .searchText, .isEditingDidChange:
            state.next = searchText.map { .searchText($0) }
        default:
            break
        }
        return .none
    case let .isEditingDidChange(isEditing):
        switch state.next {
        case nil, .searchText, .isEditingDidChange:
            state.next = .isEditingDidChange(isEditing)
            if !isEditing {
                state.next = nil
            }
        default:
            break
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
    case .tap(let content):
        return .none
    case .tapFavorite(let isFavorited, let contentId):
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
                switch content.item.wrappedValue {
                case is Blog:
                    blogs.append(content)
                case is Video:
                    videos.append(content)
                case is Podcast:
                    podcasts.append(content)
                default:
                    assertionFailure("Unexpected FeedItem: (\(content.item.wrappedValue)")
                    break
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
        case .showSetting:
            return .none
        }
    }
)

private extension MediaAction {
    static let mockItemsLoads: Self = .itemsLoaded([])
}
