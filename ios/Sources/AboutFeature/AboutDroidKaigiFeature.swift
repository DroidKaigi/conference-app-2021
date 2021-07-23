import ComposableArchitecture

public struct AboutDroidKaigiState: Equatable {
}

public enum AboutDroidKaigiAction {
    case behaviorCode
    case opensourceLicense
    case privacyPolicy
}

public struct AboutDroidKaigiEnvironment {
    public init() {}
}

public let aboutDroidKaigiReducer = Reducer<AboutDroidKaigiState, AboutDroidKaigiAction, AboutDroidKaigiEnvironment> { state, action, _ in
    switch action {
    case .behaviorCode:
        return .none
    case .opensourceLicense:
        return .none
    case .privacyPolicy:
        return .none
    }
}
