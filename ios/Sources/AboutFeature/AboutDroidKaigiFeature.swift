import ComposableArchitecture

public enum AboutDroidKaigiModel: CaseIterable {
    case behaviorCode
    case opensourceLicense
    case privacyPolicy
}

public struct AboutDroidKaigiState: Equatable {
}

public enum AboutDroidKaigiAction {
    case tap(AboutDroidKaigiModel)
}

public struct AboutDroidKaigiEnvironment {
    public init() {}
}

public let aboutDroidKaigiReducer = Reducer<AboutDroidKaigiState, AboutDroidKaigiAction, AboutDroidKaigiEnvironment> { _, action, _ in
    switch action {
    case .tap(_):
        return .none
    }
}
