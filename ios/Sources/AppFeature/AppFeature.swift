import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Model
import Repository

public struct AppState: Equatable {
    public var type: AppStateType
    public var appTabState: AppTabState
    public var language: Lang

    public init(type: AppStateType, language: Lang, feedContents: [FeedContent] = []) {
        self.type = type
        self.language = language
        self.appTabState = AppTabState(feedContents: feedContents, language: language)
    }
}

public enum AppStateType: Equatable {
    case needToInitialize
    case initialized
    case errorOccurred
}

public enum AppAction {
    case onAppear
    case refresh
    case needRefresh
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case appTab(AppTabAction)
    case currentLanguage(Result<Lang?, KotlinError>)
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    appTabReducer.pullback(
        state: \.appTabState,
        action: /AppAction.appTab,
        environment: { $0 }
    ),
    .init { state, action, environment in
        switch action {
        case .onAppear:
            return environment.languageRepository.currentLanguage().catchToEffect().map(AppAction.currentLanguage)
        case let .currentLanguage(.success(language)):
            state.language = language ?? .system
            return .none
        case let .currentLanguage(.failure(error)):
            return .none
        case .refresh:
            return environment.feedRepository.feedContents()
                .catchToEffect()
                .map(AppAction.refreshResponse)
        case .needRefresh:
            state.type = .needToInitialize
            return .none
        case let .refreshResponse(.success(feedContents)):
            state.type = .initialized
            state.appTabState = .init(feedContents: feedContents, language: state.language)
            return .none
        case let .refreshResponse(.failure(error)):
            state.type = .errorOccurred
            return .none
        case .appTab:
            return .none
        }
    }
)
