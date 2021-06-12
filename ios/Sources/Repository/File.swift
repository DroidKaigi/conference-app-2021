import Combine
import DroidKaigiMPP
import Model

public protocol ThemeRepositoryProtocol: KMMRepositoryProtocol {
    associatedtype RepositoryType = IosThemeRepository

    func changeTheme(theme: Model.Theme) -> AnyPublisher<Void, KotlinError>
    func currentTheme() -> AnyPublisher<Model.Theme?, KotlinError>
}

public struct ThemeRepository: ThemeRepositoryProtocol {
    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init() {
        self.scopeProvider = DIContainer.shared.get(type: ScopeProvider.self)
        self.repository = DIContainer.shared.get(type: RepositoryType.self)
    }

    public func changeTheme(theme: Model.Theme) -> AnyPublisher<Void, KotlinError> {
        Future<Void, KotlinError> { promise in
            repository.changeTheme(theme: theme.kmmTheme)
                .subscribe(scope: scopeProvider.scope) { _ in
                    promise(.success(()))
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }
        }
        .eraseToAnyPublisher()
    }

    public func currentTheme() -> AnyPublisher<Model.Theme?, KotlinError> {
        Future<DroidKaigiMPP.Theme?, KotlinError> { promise in
            repository.theme().subscribe(scope: scopeProvider.scope) {
                promise(.success($0))
            } onComplete: {
            } onFailure: {
                promise(.failure(KotlinError.fetchFailed($0.description())))
            }
        }
        .map { $0.map(Model.Theme.from(_:)) }
        .eraseToAnyPublisher()
    }
}

