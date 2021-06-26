import Combine
import Model

public struct ContributorRepositoryMock: ContributorRepositoryProtocol {
    public init() {}
    public func contributorContents() -> AnyPublisher<[Model.Contributor], KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}
