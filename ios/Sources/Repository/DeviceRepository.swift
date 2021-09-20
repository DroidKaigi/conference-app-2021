import Combine
import DroidKaigiMPP
import Model

public protocol DeviceRepositoryProtocol {
    func updateDeviceToken(deviceToken: String?) -> AnyPublisher<Void, KotlinError>
}

public struct DeviceRepository: DeviceRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosDeviceRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    public func updateDeviceToken(deviceToken: String?) -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.updateDeviceToken(deviceToken: deviceToken),
            scopeProvider: scopeProvider
        )
        .map { _ in }
        .eraseToAnyPublisher()
    }
}
