import Combine
import ComposableArchitecture
import Model
import Repository
import UIApplicationClient

public struct AboutState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType: SelectedType
    public var aboutDroidKaigiState: AboutDroidKaigiState?
    public var showingURL: URL?

    public var isShowingSheet: Bool {
        showingURL != nil || aboutDroidKaigiState != nil
    }

    public init(
        staffs: [Staff] = [],
        contributors: [Contributor] = [],
        selectedType: SelectedType = .staff
    ) {
        self.staffs = staffs
        self.contributors = contributors
        self.selectedType = selectedType
    }
}

public enum AboutAction {
    case refresh
    case refreshResponse(Result<([Contributor], [Staff]), KotlinError>)
    case selectedPicker(SelectedType)
    case tapStaff(Staff)
    case tapContributor(Contributor)
    case tapBanner
    case hideSheet
    case aboutDroidKaigi(AboutDroidKaigiAction)
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
    aboutDroidKaigiReducer.optional().pullback(
        state: \.aboutDroidKaigiState,
        action: /AboutAction.aboutDroidKaigi,
        environment: { environment in
            .init(
                applicationClient: environment.applicationClient
            )
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
            state.contributors = contributors
            state.staffs = staffs
            return .none
        case .refreshResponse(.failure):
            return .none
        case .selectedPicker(let selectedType):
            state.selectedType = selectedType
            return .none
        case let .tapStaff(staff):
            guard let url = URL(string: staff.profileURLString) else { return .none }
            state.showingURL = url
            return .none
        case let .tapContributor(contributor):
            guard let url = URL(string: contributor.urlString) else { return .none }
            state.showingURL = url
            return .none
        case .tapBanner:
            state.aboutDroidKaigiState = .init()
            return .none
        case .hideSheet:
            state.aboutDroidKaigiState = nil
            state.showingURL = nil
            return .none
        case .aboutDroidKaigi:
            return .none
        }
    }

)
