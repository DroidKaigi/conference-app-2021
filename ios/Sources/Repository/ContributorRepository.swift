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
        Future<NSArray, KotlinError> { promise in
            repository.contributorContents()
                .subscribe(scope: scopeProvider.scope) {
                    promise(.success($0))
                } onComplete: {
                } onFailure: {
                    promise(.failure(KotlinError.fetchFailed($0.description())))
                }
        }
        .flatMap { contributors -> AnyPublisher<[Model.Contributor], Never> in
            guard let contributors = contributors as? [DroidKaigiMPP.Contributor] else {
                return Empty().eraseToAnyPublisher()
            }
            return Just(contributors.map(Model.Contributor.init(from:))).eraseToAnyPublisher()
        }
        .eraseToAnyPublisher()
    }
}
