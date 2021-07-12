import Combine
import UIKit

public struct UIApplicationClientMock: UIApplicationClientProtocol {
    public init() {}

    public func open(url: URL, options: [UIApplication.OpenExternalURLOptionsKey: Any]) -> AnyPublisher<Bool, Never> {
        Empty().eraseToAnyPublisher()
    }
}
