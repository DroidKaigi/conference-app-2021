import Combine
import Model

public struct ThemeRepositoryMock: ThemeRepositoryProtocol {
    public init() {}
    public func changeTheme(theme: Model.Theme) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
    public func currentTheme() -> AnyPublisher<Model.Theme?, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}
