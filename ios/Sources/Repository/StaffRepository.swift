import Combine
import DroidKaigiMPP
import Model

public protocol StaffRepositoryProtocol {
    func staffContents() -> AnyPublisher<[Model.Staff], KotlinError>
}

public struct StaffRepository: StaffRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosStaffRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    public func staffContents() -> AnyPublisher<[Model.Staff], KotlinError> {
        FlowWrapperPublisher(
            flowWrapper: repository.staffContents(),
            scopeProvider: scopeProvider
        )
        .compactMap { $0 as? [DroidKaigiMPP.Staff] }
        .map { $0.map(Model.Staff.init(from:)) }
        .eraseToAnyPublisher()
    }
}
