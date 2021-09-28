import Combine
import ComposableArchitecture
import Model
import Repository

public struct AboutState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType: SelectedType
    public var isSheetPresented: SheetType?

    public var isShowingSheet: Bool {
        isSheetPresented != nil
    }

    public enum SheetType: Equatable {
        case url(URL)
        case aboutDroidKaigi
    }

    public init(
        staffs: [Staff] = [],
        contributors: [Contributor] = [],
        selectedType: SelectedType = .staff,
        isSheetPresented: SheetType? = nil
    ) {
        self.staffs = staffs
        self.contributors = contributors
        self.selectedType = selectedType
        self.isSheetPresented = isSheetPresented
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
}

public struct AboutEnvironment {
    public let contributorRepository: ContributorRepositoryProtocol
    public let staffRepository: StaffRepositoryProtocol

    public init(
        contributorRepository: ContributorRepositoryProtocol,
        staffRepository: StaffRepositoryProtocol
    ) {
        self.contributorRepository = contributorRepository
        self.staffRepository = staffRepository
    }
}

public let aboutReducer = Reducer<AboutState, AboutAction, AboutEnvironment> { state, action, environment in
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
        state.isSheetPresented = .url(url)
        return .none
    case let .tapContributor(contributor):
        guard let url = URL(string: contributor.urlString) else { return .none }
        state.isSheetPresented = .url(url)
        return .none
    case .tapBanner:
        state.isSheetPresented = .aboutDroidKaigi
        return .none
    case .hideSheet:
        state.isSheetPresented = nil
        return .none
    }
}
