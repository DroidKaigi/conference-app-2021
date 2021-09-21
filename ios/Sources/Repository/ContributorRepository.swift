import Combine
import DroidKaigiMPP
import Model

public protocol ContributorRepositoryProtocol {
    func contributorContents() -> AnyPublisher<[Model.Contributor], KotlinError>
}

public struct ContributorRepository: ContributorRepositoryProtocol, KMMRepositoryProtocol {
    public typealias RepositoryType = IosContributorRepository

    let scopeProvider: ScopeProvider
    let repository: RepositoryType

    public init(container: DIContainer) {
        self.scopeProvider = container.get(type: ScopeProvider.self)
        self.repository = container.get(type: RepositoryType.self)
    }

    public func contributorContents() -> AnyPublisher<[Model.Contributor], KotlinError> {
        FlowWrapperPublisher(
            flowWrapper: repository.contributorContents(),
            scopeProvider: scopeProvider
        )
        .first()
        .compactMap { $0 as? [DroidKaigiMPP.Contributor] }
        .map { $0.map(Model.Contributor.init(from:)) }
        .eraseToAnyPublisher()
    }
}
