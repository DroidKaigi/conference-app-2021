import Combine
import DroidKaigiMPP
import Model

public protocol StaffRepositoryProtocol: KMMRepositoryProtocol {
    associatedtype RepositoryType = IosStaffRepository

    func staffContents() -> AnyPublisher<[Model.Staff], KotlinError> 
}

public struct StaffRepository: StaffRepositoryProtocol {
    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init() {
        self.scopeProvider = DIContainer.shared.get(type: ScopeProvider.self)
        self.repository = DIContainer.shared.get(type: RepositoryType.self)
    }

    public func staffContents() -> AnyPublisher<[Model.Staff], KotlinError> {
        Future<NSArray, KotlinError> { promise in
            repository.staffContents()
                .subscribe(scope: scopeProvider.scope) {
                    promise(.success($0))
                } onComplete: {
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }
        }
        .flatMap { staffs -> AnyPublisher<[Model.Staff], Never> in
            guard let staffs = staffs as? [DroidKaigiMPP.Staff] else {
                return Empty().eraseToAnyPublisher()
            }
            return Just(staffs.map(Model.Staff.init(from:))).eraseToAnyPublisher()
        }
        .eraseToAnyPublisher()
    }
}
