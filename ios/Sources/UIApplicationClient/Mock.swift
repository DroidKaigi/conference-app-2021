import Combine

public struct UIApplicationClientMock: UIApplicationClientProtocol {
    public init() {}

    public func openSettings() -> AnyPublisher<Bool, Never> {
        Empty().eraseToAnyPublisher()
    }
}
