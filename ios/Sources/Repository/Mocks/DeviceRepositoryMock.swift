import Combine

public struct DeviceRepositoryMock: DeviceRepositoryProtocol {
    public init() {}
    public func updateDeviceToken(deviceToken: String?) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}
