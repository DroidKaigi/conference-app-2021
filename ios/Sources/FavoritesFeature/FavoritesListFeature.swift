import ComposableArchitecture
import Model
import Repository
import UIApplicationClient

public struct FavoritesListState: Equatable {
    public var feedContents: [FeedContent]
    public var showingURL: URL?

    public var isShowingWebView: Bool {
        showingURL != nil
    }

    public init(feedContents: [FeedContent] = []) {
        self.feedContents = feedContents
        self.showingURL = nil
    }
}

public enum FavoritesListAction {
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case hideWebView
}

public struct FavoritesListEnvironment {
    public let feedRepository: FeedRepositoryProtocol

    public init(
        feedRepository: FeedRepositoryProtocol
    ) {
        self.feedRepository = feedRepository
    }
}

public let favoritesListReducer = Reducer<FavoritesListState, FavoritesListAction, FavoritesListEnvironment> { state, action, environment in
    switch action {
    case let .tap(feedContent):
        state.showingURL = URL(string: feedContent.item.link)
        return .none
    case .tapFavorite(let isFavorited, let id):
        let publisher = isFavorited
            ? environment.feedRepository.removeFavorite(id: id)
            : environment.feedRepository.addFavorite(id: id)
        return publisher
            .map { id }
            .catchToEffect()
            .map(FavoritesListAction.favoriteResponse)
    case let .favoriteResponse(.success(id)):
        if let index = state.feedContents.map(\.id).firstIndex(of: id) {
            state.feedContents[index].isFavorited.toggle()
        }
        return .none
    case let .favoriteResponse(.failure(error)):
        print(error.localizedDescription)
        return .none
    case .hideWebView:
        state.showingURL = nil
        return .none
    }
}
