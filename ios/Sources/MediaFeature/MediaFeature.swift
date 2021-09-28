import Component
import ComposableArchitecture
import Model
import Repository

public struct MediaState: Equatable {
    // In order not to use any networks for searching feature,
    // `feedContents` is storage to search from & `searchedFeedContents` is searched result from `feedContents`
    public var feedContents: [FeedContent]
    public var searchedFeedContents: [FeedContent]? {
        guard isSearchTextEditing else { return nil }
        let containsInSearchText = { (text: String) -> Bool in
            text.contains(searchText.filterForSeaching)
        }
        return feedContents
            .filter { content in
                containsInSearchText(content.item.title.jaTitle.filterForSeaching)
                || containsInSearchText(content.item.title.enTitle.filterForSeaching)
            }
    }
    var searchText: String
    var isSearchTextEditing: Bool
    var moreActiveType: MediaType?

    public init(
        feedContents: [FeedContent],
        searchText: String = "",
        isSearchTextEditing: Bool = false,
        moreActiveType: MediaType? = nil
    ) {
        self.feedContents = feedContents
        self.searchText = searchText
        self.isSearchTextEditing = isSearchTextEditing
        self.moreActiveType = moreActiveType
    }
}

// Only use to scope `Store`
extension MediaState {
    var blogs: [FeedContent] {
        feedContents.filter { $0.item.wrappedValue is Blog }
    }

    var videos: [FeedContent] {
        feedContents.filter { $0.item.wrappedValue is Video }
    }

    var podcasts: [FeedContent] {
        feedContents.filter { $0.item.wrappedValue is Podcast }
    }
}

public enum MediaAction {
    case searchTextDidChange(to: String?)
    case isEditingDidChange(to: Bool)
    case showMore(for: MediaType)
    case moreDismissed
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
    case tapPlay(FeedContent)
    case showSetting
}

public struct MediaEnvironment {
    public init() {}
}

public let mediaReducer = Reducer<MediaState, MediaAction, MediaEnvironment> { state, action, _ in
    switch action {
    case let .searchTextDidChange(to: searchText):
        state.searchText = searchText ?? ""
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
    case .tapPlay:
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
