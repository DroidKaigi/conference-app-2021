import ComposableArchitecture
import UIApplicationClient
import Component

public enum AboutDroidKaigiModel: CaseIterable {
    case behaviorCode
    case opensourceLicense
    case privacyPolicy
}

public struct AboutDroidKaigiState: Equatable {
    public var webViewState: WebViewState?

    public var isPresentingSheet: Bool {
        webViewState != nil
    }

    public init() {}
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
            state.webViewState = URL(string: "http://www.association.droidkaigi.jp/code-of-conduct.html")
                .map(WebViewState.init(url:))
            return .none
        case .opensourceLicense:
            return environment.applicationClient
                .openSettings()
                .catchToEffect(AboutDroidKaigiAction.openApplicationSettings)
        case .privacyPolicy:
            state.webViewState = URL(string: "http://www.association.droidkaigi.jp/privacy.html")
                .map(WebViewState.init(url:))
            return .none
        }
    case .openApplicationSettings:
        return .none
    case .hideSheet:
        state.webViewState = nil
        return .none
    }
}
