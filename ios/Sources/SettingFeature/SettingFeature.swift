import ComposableArchitecture

public enum SettingModel: Hashable {
    case darkMode(_ isOn: Bool)
    case language(_ isOn: Bool)
}

public struct SettingState: Equatable {
    var items: [SettingModel]

    public init(items: [SettingModel]) {
        self.items = items
    }
}

public enum SettingAction {
    case toggle(SettingModel)
}

public struct SettingEnvironment {
    public init() {}
}

public let settingReducer = Reducer<SettingState, SettingAction, SettingEnvironment> { state, action, _ in
    switch action {
    case let .toggle(settingModel):
        switch settingModel {
        case let .darkMode(isOn):
//            state.darkModeIsOn = true
            state.items[0] = .darkMode(isOn)
        case let .language(isOn):
            state.items[1] = .language(isOn)
        }
        return .none
    }
}
