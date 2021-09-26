import Combine
import Model

public struct TimetableRepositoryMock: TimetableRepositoryProtocol {
    public init() {}
    public func timetableContents() -> AnyPublisher<[AnyTimetableItem], KotlinError> {
        Empty().eraseToAnyPublisher()
    }
    public func addFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }

    public func removeFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }

}
