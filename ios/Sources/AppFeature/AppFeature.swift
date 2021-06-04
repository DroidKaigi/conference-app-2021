import ComposableArchitecture
import HomeFeature

public struct AppState: Equatable {
    public var homeState: HomeState

    public init(
        homeState: HomeState = .init()
    ) {
        self.homeState = homeState
    }
}

public enum AppAction {
    case home(HomeAction)
}

public struct AppEnvironment {
    public init() {}
}

public let appReducer = Reducer<AppState, AppAction, AppEnvironment>.combine(
    homeReducer.pullback(
        state: \.homeState,
        action: /AppAction.home,
        environment: { _ -> HomeEnvironment in
            .init()
        }
    ),
    .init { _, action, _ in
        switch action {
        case .home:
            return .none
        }
    }
)
