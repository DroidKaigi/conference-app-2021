import ComposableArchitecture
import UIApplicationClient

public enum AboutDroidKaigiModel: CaseIterable {
    case behaviorCode
    case opensourceLicense
    case privacyPolicy
}

public struct AboutDroidKaigiState: Equatable {
}

public enum AboutDroidKaigiAction {
    case tap(AboutDroidKaigiModel)
    case openApplicationSettings(Result<Bool, Never>)
}

public struct AboutDroidKaigiEnvironment {
    public let applicationClient: UIApplicationClientProtocol
    public init(
        applicationClient: UIApplicationClientProtocol
    ) {
        self.applicationClient = applicationClient
    }
}

public let aboutDroidKaigiReducer = Reducer<AboutDroidKaigiState, AboutDroidKaigiAction, AboutDroidKaigiEnvironment> { _, action, environment in
    switch action {
    case .tap(let model):
        switch model {
        case .behaviorCode:
            // TODO: add navigation
            break
        case .opensourceLicense:
            return environment.applicationClient
                .openSettings()
                .catchToEffect(AboutDroidKaigiAction.openApplicationSettings)
        case .privacyPolicy:
            // TODO: add navigation
            break
        }
        return .none
    case .openApplicationSettings(_):
        return .none
    }
}
