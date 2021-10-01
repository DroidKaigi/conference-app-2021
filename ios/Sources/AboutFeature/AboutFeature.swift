import Combine
import ComposableArchitecture
import Model
import Repository
import UIApplicationClient

public enum AboutState: Equatable {
    case needToInitialize
    case initialized(AboutLoadedState)
    case errorOccurred

    public init() {
        self = .needToInitialize
    }
}

public enum AboutAction {
    case refresh
    case refreshResponse(Result<([Contributor], [Staff]), KotlinError>)
    case loaded(AboutLoadedAction)
}

public struct AboutEnvironment {
    public let applicationClient: UIApplicationClientProtocol
    public let contributorRepository: ContributorRepositoryProtocol
    public let staffRepository: StaffRepositoryProtocol

    public init(
        applicationClient: UIApplicationClientProtocol,
        contributorRepository: ContributorRepositoryProtocol,
        staffRepository: StaffRepositoryProtocol
    ) {
        self.applicationClient = applicationClient
        self.contributorRepository = contributorRepository
        self.staffRepository = staffRepository
    }
}

public let aboutReducer = Reducer<AboutState, AboutAction, AboutEnvironment>.combine(
    aboutLoadedReducer.pullback(
        state: /AboutState.initialized,
        action: /AboutAction.loaded,
        environment: { environment in
            .init(applicationClient: environment.applicationClient)
        }
    ),
    .init { state, action, environment in
        switch action {
        case .refresh:
            return Publishers.CombineLatest(
                environment.contributorRepository.contributorContents(),
                environment.staffRepository.staffContents()
            )
            .catchToEffect()
            .map(AboutAction.refreshResponse)
        case .refreshResponse(.success((let contributors, let staffs))):
            state = .initialized(AboutLoadedState(
                staffs: staffs,
                contributors: contributors
            ))
            return .none
        case .refreshResponse(.failure):
            state = .errorOccurred
            return .none
        case .loaded:
            return .none
        }
    }

)
