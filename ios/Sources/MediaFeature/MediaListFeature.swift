import ComposableArchitecture
import Model
import Repository

public struct MediaListState: Equatable {
    // In order not to use any networks for searching feature,
    // `feedContents` is storage to search from & `searchedFeedContents` is searched result from `feedContents`
    var feedContents: [FeedContent]
    var searchedFeedContents: [FeedContent]
    var blogs: [FeedContent]
    var videos: [FeedContent]
    var podcasts: [FeedContent]
    var isSearchResultVisible: Bool
    var isSearchTextEditing: Bool
    var moreActiveType: MediaType?

    init(
        feedContents: [FeedContent],
        searchedFeedContents: [FeedContent] = [],
        blogs: [FeedContent],
        videos: [FeedContent],
        podcasts: [FeedContent],
        isSearchResultVisible: Bool = false,
        isSearchTextEditing: Bool = false,
        moreActiveType: MediaType? = nil
    ) {
        self.feedContents = feedContents
        self.searchedFeedContents = searchedFeedContents
        self.blogs = blogs
        self.videos = videos
        self.podcasts = podcasts
        self.isSearchResultVisible = isSearchResultVisible
        self.isSearchTextEditing = isSearchTextEditing
        self.moreActiveType = moreActiveType
    }
}

public enum MediaListAction {
    case searchTextDidChange(to: String?)
    case isEditingDidChange(to: Bool)
    case showMore(for: MediaType)
    case moreDismissed
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
}

public enum MediaType {
    case blog
    case video
    case podcast
}

let mediaListReducer = Reducer<MediaListState, MediaListAction, MediaEnvironment> { state, action, environment in
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
    case .tap(let content):
        // TODO: open content page
        return .none
    case .tapFavorite(let isFavorited, let id):
        if let index = state.feedContents.map(\.id).firstIndex(of: id) {
            state.feedContents[index].isFavorited.toggle()
        }
        if let index = state.searchedFeedContents.map(\.id).firstIndex(of: id) {
            state.searchedFeedContents[index].isFavorited.toggle()
        }
        if let index = state.blogs.map(\.id).firstIndex(of: id) {
            state.blogs[index].isFavorited.toggle()
        }
        if let index = state.videos.map(\.id).firstIndex(of: id) {
            state.videos[index].isFavorited.toggle()
        }
        if let index = state.podcasts.map(\.id).firstIndex(of: id) {
            state.podcasts[index].isFavorited.toggle()
        }
        let publisher = isFavorited
            ? environment.feedRepository.removeFavorite(id: id)
            : environment.feedRepository.addFavorite(id: id)
        return publisher
            .map { id }
            .catchToEffect()
            .map(MediaListAction.favoriteResponse)
    case let .favoriteResponse(.success(id)):
        return .none
    case let .favoriteResponse(.failure(error)):
        print(error.localizedDescription)
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
