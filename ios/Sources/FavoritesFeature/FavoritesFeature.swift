import Model
import Component
import ComposableArchitecture

// TODO: Replace to real models
public struct FavoriteItem: Equatable, Identifiable {
    public let id: UUID = UUID()
    let title: String = "タイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトルタイトル"
    let isFavorited: Bool
    let imageURL: URL? = nil
    let tag: Media = .medium
    let date: Date = Date(timeIntervalSince1970: 0)

    public init(isFavorited: Bool) {
        self.isFavorited = isFavorited
    }
}

public struct FavoritesState: Equatable {
    // TODO: Replace to real models
    public var items: [FavoriteItem]

    public init(items: [FavoriteItem] = []) {
        self.items = items
    }
}

public enum FavoritesAction {
    case refresh
    case tap(FavoriteItem)
    case favorite(FavoriteItem)
    case showSettings
}

public struct FavoritesEnvironment {
    public init() {}
}

public let favoritesReducer = Reducer<FavoritesState, FavoritesAction, FavoritesEnvironment> { state, action, _ in
    switch action {
    case .refresh:
        state.items = [
            .init(isFavorited: true),
            .init(isFavorited: false),
            .init(isFavorited: true),
            .init(isFavorited: false),
            .init(isFavorited: true),
            .init(isFavorited: false)
        ]
        return .none
    case .tap(let item):
        return .none
    case .favorite:
        return .none
    default:
        return .none
    }
}
