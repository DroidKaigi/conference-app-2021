import Combine
import UIKit

public protocol UIApplicationClientProtocol {
    func openSettings() -> AnyPublisher<Bool, Never>
}

public struct UIApplicationClient: UIApplicationClientProtocol {

    public init() {}
    public func openSettings() -> AnyPublisher<Bool, Never> {
        return open(urlString: UIApplication.openSettingsURLString, options: [:])
    }

    private func open(urlString: String, options: [UIApplication.OpenExternalURLOptionsKey: Any]) -> AnyPublisher<Bool, Never> {
        Future<Bool, Never> { promise in
            guard let url = URL(string: urlString), UIApplication.shared.canOpenURL(url) else {
                return promise(.success(false))
            }
            UIApplication.shared.open(url, options: options) { success in
                promise(.success(success))
            }
        }
        .eraseToAnyPublisher()
    }
}
