import ComposableArchitecture
import Model
import Repository

public struct SettingState: Equatable {
    public init() {}
}

public enum SettingAction {
    case changeTheme(Theme)
}

public struct SettingEnvironment {
    public init() {}
}

public let settingReducer = Reducer<SettingState, SettingAction, SettingEnvironment> { _, action, _ in
    switch action {
    case let .changeTheme(theme):
        return .none
    }
}
