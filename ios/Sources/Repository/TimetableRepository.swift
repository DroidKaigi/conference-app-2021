import Combine
import DroidKaigiMPP
import Model

public protocol TimetableRepositoryProtocol {
    func timetableContents() -> AnyPublisher<[Model.TimetableItem], KotlinError>
}

public struct TimetableRepository: TimetableRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosTimetableRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    public func timetableContents() -> AnyPublisher<[Model.TimetableItem], KotlinError> {
        FlowWrapperPublisher(
            flowWrapper: repository.timetableContents(),
            scopeProvider: scopeProvider
        )
        .map { $0.timetableItems.compactMap(Model.TimetableItem.init(from:)) }
        .print("hoge: ")
        .eraseToAnyPublisher()
    }
}
