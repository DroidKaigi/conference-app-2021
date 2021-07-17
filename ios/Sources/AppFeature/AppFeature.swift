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
        case initialized(FavoritesListState)
        case errorOccurred
    }

    public var homeState: HomeState
    public var mediaState: MediaState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState
    public var feedContents: [FeedContent]
    public var coreState: AppCoreState

    public init(
        homeState: HomeState = .init(),
        mediaState: MediaState = .init(),
        favoritesState: FavoritesState = .init(),
        aboutState: AboutState = .init(),
        feedContents: [FeedContent] = [],
        coreState: AppCoreState = .needToInitialize
    ) {
        self.homeState = homeState
        self.mediaState = mediaState
        self.favoritesState = favoritesState
        self.aboutState = aboutState
        self.feedContents = feedContents
        self.coreState = coreState
    }
}

public enum AppAction {
    case home(HomeAction)
    case media(MediaAction)
    case favorites(FavoritesAction)
    case about(AboutAction)
    case refresh
    case needRefresh
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    homeReducer.pullback(
        state: \.homeState,
        action: /AppAction.home,
        environment: { environment -> HomeEnvironment in
            .init(feedRepository: environment.feedRepository)
        }
    ),
    mediaReducer.pullback(
        state: \.mediaState,
        action: /AppAction.media,
        environment: { environment in
            .init(feedRepository: environment.feedRepository)
        }
    ),
    favoritesReducer.pullback(
        state: \.favoritesState,
        action: /AppAction.favorites,
        environment: { environment -> FavoritesEnvironment in
            .init(feedRepository: environment.feedRepository)
        }
    ),
    aboutReducer.pullback(
        state: \.aboutState,
        action: /AppAction.about,
        environment: { _ -> AboutEnvironment in
            .init()
        }
    ),
    .init { state, action, environment in
        switch action {
        case .home:
            return .none
        case .media:
            return .none
        case .favorites:
            return .none
        case .about:
            return .none
        case .refresh:
            return environment.feedRepository.feedContents()
                .catchToEffect()
                .map(AppAction.refreshResponse)
        case .needRefresh:
            state.coreState = .needToInitialize
            return .none
        case let .refreshResponse(.success(feedContents)):
            state.coreState = .initialized(.init(feedContents: feedContents))
            return .none
        case let .refreshResponse(.failure(error)):
            state.coreState = .errorOccurred
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
        }
    }
)
