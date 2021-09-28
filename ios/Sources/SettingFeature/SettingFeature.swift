import ComposableArchitecture
import Model

public struct SettingState: Equatable {
    public var language: Lang

    public init(language: Lang) {
        self.language = language
    }
}

public enum SettingAction {
    case changeTheme(Theme)
    case changeLanguage(Lang)
}

public struct SettingEnvironment {
    public init() {}
}

public let settingReducer = Reducer<SettingState, SettingAction, SettingEnvironment> { _, action, _ in
    switch action {
    case let .changeTheme(theme):
        return .none
    case let .changeLanguage(language):
        return .none
    }
}
