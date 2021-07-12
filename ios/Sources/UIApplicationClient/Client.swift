import Combine
import UIKit

public protocol UIApplicationClientProtocol {
    func open(url: URL, options: [UIApplication.OpenExternalURLOptionsKey: Any]) -> AnyPublisher<Bool, Never>
}

public struct UIApplicationClient: UIApplicationClientProtocol {
    public init() {}

    public func open(url: URL, options: [UIApplication.OpenExternalURLOptionsKey: Any]) -> AnyPublisher<Bool, Never> {
        Future<Bool, Never> { promise in
            UIApplication.shared.open(url, options: options) { success in
                promise(.success(success))
            }
        }
        .eraseToAnyPublisher()
    }
}
