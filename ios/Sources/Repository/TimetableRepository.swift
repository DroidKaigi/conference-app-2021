import Combine
import DroidKaigiMPP
import Model

public protocol TimetableRepositoryProtocol {
    func timetableContents() -> AnyPublisher<[Model.TimetableItem], KotlinError>
    func addFavorite(id: String) -> AnyPublisher<Void, KotlinError>
    func removeFavorite(id: String) -> AnyPublisher<Void, KotlinError>
}

public struct TimetableRepository: TimetableRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosTimetableRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    private func refresh() -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.refresh(),
            scopeProvider: scopeProvider
        )
        .map { _ in }
        .eraseToAnyPublisher()
    }

    public func timetableContents() -> AnyPublisher<[Model.TimetableItem], KotlinError> {
        refresh()
            .flatMap {
                FlowWrapperPublisher(
                    flowWrapper: repository.timetableContents(),
                    scopeProvider: scopeProvider
                )
            }
            .map { $0.timetableItems.compactMap(Model.TimetableItem.init(from:)) }
            .eraseToAnyPublisher()
    }

    public func addFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.addFavorite(id: id),
            scopeProvider: scopeProvider
        )
        .first()
        .map { _ in }
        .eraseToAnyPublisher()
    }

    public func removeFavorite(id: String) -> AnyPublisher<Void, KotlinError> {
        SuspendWrapperPublisher(
            suspendWrapper: repository.removeFavorite(id: id),
            scopeProvider: scopeProvider
        )
        .first()
        .map { _ in }
        .eraseToAnyPublisher()
    }
}
