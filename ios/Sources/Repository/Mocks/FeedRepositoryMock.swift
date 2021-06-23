import Combine
import Model

public struct FeedRepositoryMock: FeedRepositoryProtocol {
    public init() {}
    public func feedContents() -> AnyPublisher<Model.FeedContents, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
    public func addFavorite(feedItem: Model.FeedItemType) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
    public func removeFavorite(feedItem: Model.FeedItemType) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}
