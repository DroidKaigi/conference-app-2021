import ComposableArchitecture

public struct HomeState: Equatable {
    public init() {}
}

public enum HomeAction {
}

public struct HomeEnvironment {
    public init() {}
}

public let homeReducer = Reducer<HomeState, HomeAction, HomeEnvironment> { state, action, environment in
    .none
}
