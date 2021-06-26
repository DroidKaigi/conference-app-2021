import Combine
import Model

public struct StaffRepositoryMock: StaffRepositoryProtocol {
    public init() {}
    public func staffContents() -> AnyPublisher<[Model.Staff], KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}

