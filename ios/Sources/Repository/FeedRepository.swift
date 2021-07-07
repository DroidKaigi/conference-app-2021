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
        Future<Void, KotlinError> { promise in
            repository.refresh()
                .subscribe(scope: scopeProvider.scope) { _ in
                    promise(.success(()))
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }
        }
        .eraseToAnyPublisher()
    }

    public func feedContents() -> AnyPublisher<[FeedContent], KotlinError> {
        refresh()
            .flatMap {
                Future<FeedContents, KotlinError> { promise in
                    repository.feedContents()
                        .subscribe(scope: scopeProvider.scope) {
                            promise(.success($0))
                        } onComplete: {
                        } onFailure: {
                            promise(.failure(KotlinError.fetchFailed($0.description())))
                        }
                }
                .map([FeedContent].init(from:))
                .eraseToAnyPublisher()
            }
            .eraseToAnyPublisher()
    }

    public func addFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        Future<Void, KotlinError> { promise in
            repository.addFavorite(id: id)
                .subscribe(scope: scopeProvider.scope) { _ in
                    promise(.success(()))
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }

        }.eraseToAnyPublisher()
    }

    public func removeFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        Future<Void, KotlinError> { promise in
            repository.removeFavorite(id: id)
                .subscribe(scope: scopeProvider.scope) { _ in
                    promise(.success(()))
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }

        }.eraseToAnyPublisher()
    }
}
