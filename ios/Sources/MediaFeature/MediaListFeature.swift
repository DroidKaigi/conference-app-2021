import ComposableArchitecture
import Model
import Repository

public struct MediaListState: Equatable {

    enum Next: Equatable {
        case searchText(String)
        case isEditingDidChange(Bool)
        case more(for: MediaType)
    }

    // In order not to use any networks for searching feature,
    // `feedContents` is storage to search from & `searchedFeedContents` is searched result from `feedContents`
    var feedContents: [FeedContent]
    var searchedFeedContents: [FeedContent]
    var blogs: [FeedContent]
    var videos: [FeedContent]
    var podcasts: [FeedContent]
    var next: Next?

    init(
        feedContents: [FeedContent],
        searchedFeedContents: [FeedContent] = [],
        blogs: [FeedContent],
        videos: [FeedContent],
        podcasts: [FeedContent],
        next: Next?
    ) {
        self.feedContents = feedContents
        self.searchedFeedContents = searchedFeedContents
        self.blogs = blogs
        self.videos = videos
        self.podcasts = podcasts
        self.next = next
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
        switch state.next {
        case nil, .searchText, .isEditingDidChange:
            state.next = searchText.map { .searchText($0) }
            if let searchText = searchText {
                state.searchedFeedContents = state.feedContents.filter { content in
                    content.item.title.jaTitle.lowercased().contains(searchText.lowercased())
                }
            }
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
        // TODO: open content page
        return .none
    case .tapFavorite(let isFavorited, let id):
        let publisher = isFavorited
            ? environment.feedRepository.removeFavorite(id: id)
            : environment.feedRepository.addFavorite(id: id)
        return publisher
            .map { id }
            .catchToEffect()
            .map(MediaListAction.favoriteResponse)
    case let .favoriteResponse(.success(id)):
        if let index = state.feedContents.map(\.id).firstIndex(of: id) {
            state.feedContents[index].isFavorited.toggle()
            state.searchedFeedContents[index].isFavorited.toggle()
        }
        return .none
    case let .favoriteResponse(.failure(error)):
        print(error.localizedDescription)
        return .none
    }
}
