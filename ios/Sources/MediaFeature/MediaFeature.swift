import Component
import ComposableArchitecture
import Model
import Repository

public struct MediaState: Equatable {
    // In order not to use any networks for searching feature,
    // `feedContents` is storage to search from & `searchedFeedContents` is searched result from `feedContents`
    public var feedContents: [FeedContent]
    public var searchedFeedContents: [FeedContent]
    var isSearchResultVisible: Bool
    var isSearchTextEditing: Bool
    var moreActiveType: MediaType?

    public init(
        feedContents: [FeedContent],
        searchedFeedContents: [FeedContent] = [],
        isSearchResultVisible: Bool = false,
        isSearchTextEditing: Bool = false,
        moreActiveType: MediaType? = nil
    ) {
        self.feedContents = feedContents
        self.searchedFeedContents = searchedFeedContents
        self.isSearchResultVisible = isSearchResultVisible
        self.isSearchTextEditing = isSearchTextEditing
        self.moreActiveType = moreActiveType
    }
}

extension MediaState {
    var blogs: [FeedContent] {
        feedContents.filter { ($0.item.wrappedValue as? Blog) != nil }
    }

    var videos: [FeedContent] {
        feedContents.filter { ($0.item.wrappedValue as? Video) != nil }
    }

    var podcasts: [FeedContent] {
        feedContents.filter { ($0.item.wrappedValue as? Podcast) != nil }
    }

    var hasBlogs: Bool {
        !blogs.isEmpty
    }

    var hasVideos: Bool {
        !videos.isEmpty
    }

    var hasPodcasts: Bool {
        !podcasts.isEmpty
    }

    var isMoreActive: Bool {
        moreActiveType != nil
    }
}

public enum MediaAction {
    case searchTextDidChange(to: String?)
    case isEditingDidChange(to: Bool)
    case showMore(for: MediaType)
    case moreDismissed
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
    case showSetting
}

public struct MediaEnvironment {
    public init() {}
}

public let mediaReducer = Reducer<MediaState, MediaAction, MediaEnvironment> { state, action, _ in
    switch action {
    case let .searchTextDidChange(to: searchText):
        state.isSearchResultVisible = !(searchText?.isEmpty ?? true)
        if let searchText = searchText {
            state.searchedFeedContents = state.feedContents.filter { content in
                content.item.title.jaTitle.filterForSeaching.contains(searchText.filterForSeaching)
                    || content.item.title.enTitle.filterForSeaching.contains(searchText.filterForSeaching)
            }
        } else {
            state.searchedFeedContents = []
        }
        return .none
    case let .isEditingDidChange(isEditing):
        state.isSearchTextEditing = isEditing
        return .none
    case let .showMore(mediaType):
        state.moreActiveType = mediaType
        return .none
    case .moreDismissed:
        state.moreActiveType = nil
        return .none
    case .tap:
        return .none
    case .tapFavorite(let isFavorited, let id):
        return .none
    case .showSetting:
        return .none
    }
}

private extension String {
    var filterForSeaching: Self {
        self.replacingOccurrences(of: " ", with: "")
            .trimmingCharacters(in: .whitespaces)
            .lowercased()
    }
}
