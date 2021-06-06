import ComposableArchitecture
import Component

// TODO: Replace to real models
public struct FavoriteItem: Equatable, Identifiable {
    public let id: UUID = UUID()
    let title: String = "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトル"
    let isFavorite: Bool
    let imageURL: URL? = nil
    let tag: TagType = .medium
    let date: Date = Date()

    public init(isFavorite: Bool) {
        self.isFavorite = isFavorite
    }
}

public struct FavoritesState: Equatable {
    // TODO: Replace to real models
    public var items: [FavoriteItem]

    public init(items: [FavoriteItem]) {
        self.items = items
    }
}

public enum FavoritesAction {
    case tap(FavoriteItem)
    case favorite(FavoriteItem)
}

public struct FavoritesEnvironment {
    public init() {}
}

public let favoritesReducer = Reducer<FavoritesState, FavoritesAction, FavoritesEnvironment> { state, action, environment in
    switch action {
    case .tap(let item):
        return .none
    case .favorite:
        return .none
    }
}
