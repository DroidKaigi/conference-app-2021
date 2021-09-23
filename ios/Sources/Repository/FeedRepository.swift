import Combine
import DroidKaigiMPP
import Model

public protocol FeedRepositoryProtocol {
    func feedContents() -> AnyPublisher<[FeedContent], KotlinError>
    func addFavorite(id: String) -> AnyPublisher<Void, KotlinError>
    func removeFavorite(id: String) -> AnyPublisher<Void, KotlinError>
}

public struct FeedRepository: FeedRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosFeedRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    private func refresh() -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.refresh(),
            scopeProvider: scopeProvider
        )
        .map { _ in }
        .eraseToAnyPublisher()
    }

    public func feedContents() -> AnyPublisher<[FeedContent], KotlinError> {
        refresh()
            .flatMap {
                FlowWrapperPublisher(
                    flowWrapper: repository.feedContents(),
                    scopeProvider: scopeProvider
                )
            }
            .map([FeedContent].init(from:))
            .eraseToAnyPublisher()
    }

    public func addFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.addFavorite(id: id),
            scopeProvider: scopeProvider
        )
        .map { _ in }
        .eraseToAnyPublisher()
    }

    public func removeFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.removeFavorite(id: id),
            scopeProvider: scopeProvider
        )
        .map { _ in }
        .eraseToAnyPublisher()
    }
}
