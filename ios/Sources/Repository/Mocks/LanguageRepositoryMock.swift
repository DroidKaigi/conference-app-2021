import Combine
import Model

public struct LanguageRepositoryMock: LanguageRepositoryProtocol {
    public init() {}
    public func changeLanguage(language: Model.Lang) -> AnyPublisher<Void, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
    public func currentLanguage() -> AnyPublisher<Model.Lang?, KotlinError> {
        Empty().eraseToAnyPublisher()
    }
}
