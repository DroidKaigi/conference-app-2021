import ComposableArchitecture
import Model
import Repository

public struct FavoritesListState: Equatable {
    public var feedContents: [FeedContent]

    public init(feedContents: [FeedContent] = []) {
        self.feedContents = feedContents
    }
}

public enum FavoritesListAction {
    case tap(FeedContent)
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
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
    case .tap:
        // TODO: open content page
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
    }
}
