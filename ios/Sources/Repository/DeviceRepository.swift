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
        Future<Void, KotlinError> { promise in
            repository.updateDeviceToken(deviceToken: deviceToken)
                .subscribe(scope: scopeProvider.scope) { _ in
                    promise(.success(()))
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }

        }
        .eraseToAnyPublisher()
    }
}
