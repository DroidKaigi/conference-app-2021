import ComposableArchitecture

public enum SettingModel: Hashable {
    case darkMode(isOn: Bool)
    case language(isOn: Bool)
}

public struct SettingState: Equatable {
    var items: [SettingModel]
}

public enum SettingAction {
    case darkMode(isOn: Bool)
    case language(isOn: Bool)
}

public struct SettingEnvironment {
    public init() {}
}

public let settingReducer = Reducer<SettingState, SettingAction, SettingEnvironment> { state, action, _ in
    switch action {
    case let .darkMode(isOn):
        state.items[0] = .darkMode(isOn: isOn)
        return .none
    case let .language(isOn):
        state.items[1] = .language(isOn: isOn)
        return .none
    }
}
