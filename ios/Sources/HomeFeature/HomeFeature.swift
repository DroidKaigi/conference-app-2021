import ComposableArchitecture

public struct HomeState: Equatable {
    // TODO: Replace to real models
    public var contents: [String]

    public init(contents: [String] = []) {
        self.contents = contents
    }
}

public enum HomeAction {
    case answerQuestionnaire
}

public struct HomeEnvironment {
    public init() {}
}

public let homeReducer = Reducer<HomeState, HomeAction, HomeEnvironment> { state, action, environment in
    switch action {
    case .answerQuestionnaire:
        return .none
    }
}
