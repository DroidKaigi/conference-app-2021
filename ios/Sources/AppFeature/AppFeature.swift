import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Model
import Repository

public struct AppState: Equatable {
    public enum AppCoreState: Equatable {
        case needToInitialize
        case initialized(AppTabState)
        case errorOccurred
    }

    public var feedContents: [FeedContent]
    public var coreState: AppCoreState

    public init(
        feedContents: [FeedContent] = [],
        coreState: AppCoreState = .needToInitialize
    ) {
        self.feedContents = feedContents
        self.coreState = coreState
    }
}

public enum AppAction {
    case refresh
    case needRefresh
    case selectFeedContent
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case appTab(AppTabAction)
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment> { state, action, environment in
    switch action {
    case .refresh:
        return environment.feedRepository.feedContents()
            .catchToEffect()
            .map(AppAction.refreshResponse)
    case .needRefresh:
        state.coreState = .needToInitialize
        return .none
    case let .refreshResponse(.success(feedContents)):
        state.coreState = .initialized(.init(feedContent: feedContents))
        return .none
    case let .refreshResponse(.failure(error)):
        state.coreState = .errorOccurred
        return .none
    case .selectFeedContent:
        return .none
    case .tapFavorite(let isFavorited, let id):
        if let index = state.feedContents.map(\.id).firstIndex(of: id) {
            state.feedContents[index].isFavorited.toggle()
        }
        let publisher = isFavorited
            ? environment.feedRepository.removeFavorite(id: id)
            : environment.feedRepository.addFavorite(id: id)
        return publisher
            .map { id }
            .catchToEffect()
            .map(AppAction.favoriteResponse)
    case let .favoriteResponse(.success(id)):
        return .none
    case let .favoriteResponse(.failure(error)):
        print(error.localizedDescription)
        return .none
    case .appTab:
        return .none
    }
}
