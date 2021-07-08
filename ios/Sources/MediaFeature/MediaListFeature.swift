import ComposableArchitecture
import Model

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

public enum MediaListAction: Equatable {
    case searchTextDidChange(to: String?)
    case isEditingDidChange(to: Bool)
    case showMore(for: MediaType)
    case moreDismissed
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
}

public enum MediaType {
    case blog
    case video
    case podcast
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
