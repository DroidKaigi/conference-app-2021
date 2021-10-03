import Combine
import DroidKaigiMPP
import Model

public protocol LanguageRepositoryProtocol {
    func changeLanguage(language: Model.Lang) -> AnyPublisher<Void, KotlinError>
    func currentLanguage() -> AnyPublisher<Model.Lang?, KotlinError>
}

public struct LanguageRepository: LanguageRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosLanguageRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    public func changeLanguage(language: Model.Lang) -> AnyPublisher<Void, KotlinError> {
        Future { promise in
            repository.changeLanguage(language: language.kmmLang).subscribe(scope: scopeProvider.scope) { _ in
                promise(.success(()))
            } onFailure: { error in
                promise(.failure(KotlinError.fetchFailed(error.description())))
            }
        }
        .eraseToAnyPublisher()
    }

    public func currentLanguage() -> AnyPublisher<Model.Lang?, KotlinError> {
        OptionalFlowWrapperPublisher(
            flowWrapper: repository.language(),
            scopeProvider: scopeProvider
        )
        .map { $0.map(Model.Lang.from(_:)) }
        .eraseToAnyPublisher()
    }
}
