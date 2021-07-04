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
    case favorite(String)
}

public struct FavoritesEnvironment {
    public init() {}
}

public let favoritesReducer = Reducer<FavoritesState, FavoritesAction, FavoritesEnvironment> { state, action, _ in
    switch action {
    case .refresh:
        state.contents = [.mock(), .mock(), .mock(), .mock(), .mock(), .mock()]
        return .none
    case .tap(let content):
        return .none
    case .favorite:
        return .none
    }
}

// TODO: remove
private extension FeedContent {
    static func mock(id: UUID = UUID()) -> Self {
        .init(
            item: AnyFeedItem(
                Blog(
                    id: id.uuidString,
                    image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                    link: "",
                    media: .medium,
                    publishedAt: Date(timeIntervalSince1970: 0),
                    summary: .init(enTitle: "", jaTitle: ""),
                    title: .init(enTitle: "", jaTitle: "DroidKaigi 2020でのCodelabsについて"),
                    author: .init(link: "", name: ""),
                    language: ""
                )
            ),
            isFavorited: false
        )
    }
}
