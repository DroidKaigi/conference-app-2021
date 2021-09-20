import ComposableArchitecture
import SwiftUI

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
    case .tap(let model):
        switch model {
        case .behaviorCode:
            // TODO: add navigation
            break
        case .opensourceLicense:
            if let url = URL(string: UIApplication.openSettingsURLString), UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            }
            break
        case .privacyPolicy:
            // TODO: add navigation
            break
        }
    }
    return .none
}
