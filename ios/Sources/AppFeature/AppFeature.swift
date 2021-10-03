import AboutFeature
import Combine
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Model
import Repository

public enum AppState: Equatable {
    case needToInitialize
    case initialized(AppTabState)
    case errorOccurred

    public init() {
        self = .needToInitialize
    }
}

public enum AppAction {
    case refresh
    case needRefresh
    case refreshResponse(Result<([FeedContent], Lang?), KotlinError>)
    case appTab(AppTabAction)
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    appTabReducer.pullback(
        state: /AppState.initialized,
        action: /AppAction.appTab,
        environment: { $0 }
    ),
    .init { state, action, environment in
        switch action {
        case .refresh:
            return Publishers.CombineLatest(
                environment.feedRepository.feedContents(),
                environment.languageRepository.currentLanguage()
            )
                .catchToEffect()
                .map(AppAction.refreshResponse)
        case .needRefresh:
            state = .needToInitialize
            return .none
        case let .refreshResponse(.success((feedContents, language))):
            state = .initialized(.init(feedContents: feedContents, language: language ?? .system))
            return .none
        case let .refreshResponse(.failure(error)):
            state = .errorOccurred
            return .none
        case .appTab(.setting(.changeLaunguageResponse(.success))):
            state = .needToInitialize
            return .none
        case .appTab:
            return .none
        }
    }
)
