import ComposableArchitecture
import Model
import Repository

public struct SettingState: Equatable {
    public var language: Lang

    public init(language: Lang) {
        self.language = language
    }
}

public enum SettingAction {
    case changeTheme(Theme)
    case changeLanguage(Lang)
    case changeLaunguageResponse(Result<Lang, KotlinError>)
}

public struct SettingEnvironment {
    public let languageRepository: LanguageRepositoryProtocol
    public let mainQueue: AnySchedulerOf<DispatchQueue>

    public init(languageRepository: LanguageRepositoryProtocol) {
        self.languageRepository = languageRepository
        self.mainQueue = .main
    }
}

public let settingReducer = Reducer<SettingState, SettingAction, SettingEnvironment> { state, action, environment in
    switch action {
    case let .changeTheme(theme):
        return .none
    case let .changeLanguage(language):
        return environment.languageRepository.changeLanguage(language: language)
            .map { language }
            .catchToEffect(SettingAction.changeLaunguageResponse)
    case let .changeLaunguageResponse(.success(language)):
        state.language = language
        return .none
    case .changeLaunguageResponse(.failure):
        return .none
    }
}
