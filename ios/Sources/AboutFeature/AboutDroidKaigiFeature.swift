import ComposableArchitecture
import UIApplicationClient

public enum AboutDroidKaigiModel: CaseIterable {
    case behaviorCode
    case opensourceLicense
    case privacyPolicy
}

public struct AboutDroidKaigiState: Equatable {
    public var showingURL: URL?

    public var isPresentingSheet: Bool {
        showingURL != nil
    }

    public init(
        showingURL: URL? = nil
    ) {
        self.showingURL = showingURL
    }
}

public enum AboutDroidKaigiAction {
    case tap(AboutDroidKaigiModel)
    case openApplicationSettings(Result<Bool, Never>)
    case hideSheet
}

public struct AboutDroidKaigiEnvironment {
    public let applicationClient: UIApplicationClientProtocol
    public init(
        applicationClient: UIApplicationClientProtocol
    ) {
        self.applicationClient = applicationClient
    }
}

public let aboutDroidKaigiReducer = Reducer<AboutDroidKaigiState, AboutDroidKaigiAction, AboutDroidKaigiEnvironment> { state, action, environment in
    switch action {
    case .tap(let model):
        switch model {
        case .behaviorCode:
            state.showingURL = URL(string: "http://www.association.droidkaigi.jp/code-of-conduct.html")
            return .none
        case .opensourceLicense:
            return environment.applicationClient
                .openSettings()
                .catchToEffect(AboutDroidKaigiAction.openApplicationSettings)
        case .privacyPolicy:
            state.showingURL = URL(string: "http://www.association.droidkaigi.jp/privacy.html")
            return .none
        }
    case .openApplicationSettings:
        return .none
    case .hideSheet:
        state.showingURL = nil
        return .none
    }
}
