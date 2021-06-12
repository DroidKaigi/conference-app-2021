import Combine
import DroidKaigiMPP
import Model

public protocol DeviceRepositoryProtocol: KMMRepositoryProtocol {
    associatedtype RepositoryType = IosDeviceRepository

    func updateDeviceToken(deviceToken: String?) -> AnyPublisher<Void, KotlinError>
}

public struct DeviceRepository: DeviceRepositoryProtocol {
    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init() {
        self.scopeProvider = DIContainer.shared.get(type: ScopeProvider.self)
        self.repository = DIContainer.shared.get(type: RepositoryType.self)
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
