import Component
import ComposableArchitecture
import Model
import Repository

public struct FavoritesState: Equatable {
    public var feedContents: [FeedContent]
    public var language: Lang

    public init(feedContents: [FeedContent], language: Lang) {
        self.feedContents = feedContents
        self.language = language
    }
}

public enum FavoritesAction {
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
    case tapPlay(FeedContent)
    case showSetting
}

public struct FavoritesEnvironment {
    public init() {}
}

public let favoritesReducer = Reducer<FavoritesState, FavoritesAction, FavoritesEnvironment> { _, action, _ in
    switch action {
    case .tap:
        return .none
    case .tapFavorite(isFavorited: let isFavorited, id: let id):
        return .none
    case .tapPlay:
        return .none
    case .showSetting:
        return .none
    }
}
