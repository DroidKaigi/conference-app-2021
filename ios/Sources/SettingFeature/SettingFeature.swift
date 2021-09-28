import Combine
import Component
import ComposableArchitecture
import Model
import Repository
import Styleguide

public struct SettingState: Equatable {
    public var language: Lang

    public var languageType: String {
        switch language {
        case .system: return L10n.SettingScreen.ListItem.LanguageType.system
        case .ja: return L10n.SettingScreen.ListItem.LanguageType.japanese
        case .en: return L10n.SettingScreen.ListItem.LanguageType.english
        }
    }

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
