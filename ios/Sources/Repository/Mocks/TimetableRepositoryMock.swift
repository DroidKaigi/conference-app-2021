import Combine
import Model

public struct TimetableRepositoryMock: TimetableRepositoryProtocol {
    public init() {}
    public func timetableContents() -> AnyPublisher<[TimetableItem], KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}
