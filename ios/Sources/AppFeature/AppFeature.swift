import AboutFeature
import Combine
import Component
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
    case needRefresh
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case appTab(AppTabAction)
    case loading(LoadingViewAciton)
    case error(ErrorViewAction)
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    appTabReducer.pullback(
        state: /AppState.initialized,
        action: /AppAction.appTab,
        environment: { $0 }
    ),
    loadingReducer.pullback(
        state: /AppState.needToInitialize,
        action: /AppAction.loading,
        environment: { _ in }
    ),
    errorViewReducer.pullback(
        state: /AppState.errorOccurred,
        action: /AppAction.error,
        environment: {_ in}
    ),
    .init { state, action, environment in
        switch action {
        case .needRefresh:
            state = .needToInitialize
            return .none
        case let .refreshResponse(.success((feedContents))):
            state = .initialized(.init(feedContents: feedContents))
            return .none
        case let .refreshResponse(.failure(error)):
            state = .errorOccurred
            return .none
        case .appTab:
            return .none
        case .loading(.onAppeared):
            return environment.feedRepository.feedContents()
                .catchToEffect()
                .map(AppAction.refreshResponse)
        case .error(.reload):
            state = .needToInitialize
            return .none
        }
    }
)
