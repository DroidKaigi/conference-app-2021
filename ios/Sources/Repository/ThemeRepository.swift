import Combine
import DroidKaigiMPP
import Model

public protocol ThemeRepositoryProtocol {
    func changeTheme(theme: Model.Theme) -> AnyPublisher<Void, KotlinError>
    func currentTheme() -> AnyPublisher<Model.Theme?, KotlinError>
}

public struct ThemeRepository: ThemeRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosThemeRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    public func changeTheme(theme: Model.Theme) -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.changeTheme(theme: theme.kmmTheme),
            scopeProvider: scopeProvider
        )
        .map { _ in }
        .eraseToAnyPublisher()
    }

    public func currentTheme() -> AnyPublisher<Model.Theme?, KotlinError> {
        OptionalFlowWrapperPublisher(
            flowWrapper: repository.theme(),
            scopeProvider: scopeProvider
        )
        .map { $0.map(Model.Theme.from(_:)) }
        .eraseToAnyPublisher()
    }
}
