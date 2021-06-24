import Combine
import Model

public struct FeedRepositoryMock: FeedRepositoryProtocol {
    public init() {}
    public func feedContents() -> AnyPublisher<[FeedContent], KotlinError> {
        Empty().eraseToAnyPublisher()
    }
    public func addFavorite(feedItem: AnyFeedItem) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
    public func removeFavorite(feedItem: AnyFeedItem) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}
