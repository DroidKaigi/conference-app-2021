import Model
import Component
import ComposableArchitecture

public struct FavoritesState: Equatable {
    public var contents: [FeedContent]

    public init(contents: [FeedContent] = []) {
        self.contents = contents
    }
}

public enum FavoritesAction {
    case refresh
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
}

public struct FavoritesEnvironment {
    public init() {}
}

public let favoritesReducer = Reducer<FavoritesState, FavoritesAction, FavoritesEnvironment> { _, action, _ in
    switch action {
    case .refresh:
        return .none
    case .tap(let content):
        return .none
    case .tapFavorite:
        return .none
    }
}
