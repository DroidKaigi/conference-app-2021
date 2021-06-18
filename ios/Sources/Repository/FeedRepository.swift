import Combine
import DroidKaigiMPP
import Model

public protocol FeedRepositoryProtocol {
    func feedContents() -> AnyPublisher<Model.FeedContents, KotlinError>
    func addFavorite(feedItem: Model.FeedItemType) -> AnyPublisher<Void, KotlinError>
    func removeFavorite(feedItem: Model.FeedItemType) -> AnyPublisher<Void, KotlinError>
}

public struct FeedRepository: FeedRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosFeedRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    public func feedContents() -> AnyPublisher<Model.FeedContents, KotlinError> {
        Future<DroidKaigiMPP.FeedContents, KotlinError> { promise in
            repository.feedContents()
                .subscribe(scope: scopeProvider.scope) {
                    promise(.success($0))
                } onComplete: {
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }
        }
        .map(Model.FeedContents.init(from:))
        .eraseToAnyPublisher()
    }

    public func addFavorite(feedItem: Model.FeedItemType) -> AnyPublisher<Void, KotlinError> {
        Future<Void, KotlinError> { promise in
            repository.addFavorite(feedItem: feedItem.item)
                .subscribe(scope: scopeProvider.scope) { _ in
                    promise(.success(()))
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }

        }.eraseToAnyPublisher()
    }

    public func removeFavorite(feedItem: Model.FeedItemType) -> AnyPublisher<Void, KotlinError> {
        Future<Void, KotlinError> { promise in
            repository.removeFavorite(feedItem: feedItem.item)
                .subscribe(scope: scopeProvider.scope) { _ in
                    promise(.success(()))
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }

        }.eraseToAnyPublisher()
    }
}
