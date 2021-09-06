import ComposableArchitecture

public struct SettingState: Equatable {
    var darkModeIsOn: Bool
    var languageIsOn: Bool

    public init(darkModeIsOn: Bool = false, languageIsOn: Bool = false) {
        self.darkModeIsOn = darkModeIsOn
        self.languageIsOn = languageIsOn
    }
}

public enum SettingAction {
    case darkMode(Bool)
    case language(Bool)
}

public struct SettingEnvironment {
    public init() {}
}

public let settingReducer = Reducer<SettingState, SettingAction, SettingEnvironment> { state, action, _ in
    switch action {
    case .darkMode(let isOn):
        state.darkModeIsOn = isOn
        return .none
    case .language(let isOn):
        state.languageIsOn = isOn
        return .none
    }
}
