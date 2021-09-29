import ComposableArchitecture
import Model
import UIApplicationClient

public struct AboutLoadedState: Equatable {
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

public enum AboutLoadedAction {
    case selectedPicker(SelectedType)
    case tapStaff(Staff)
    case tapContributor(Contributor)
    case tapBanner
    case hideSheet
    case aboutDroidKaigi(AboutDroidKaigiAction)
}

public struct AboutLoadedEnvironment {
    public let applicationClient: UIApplicationClientProtocol
    
    public init(
        applicationClient: UIApplicationClientProtocol
    ) {
        self.applicationClient = applicationClient
    }
}

public let aboutLoadedReducer = Reducer<AboutLoadedState, AboutLoadedAction, AboutLoadedEnvironment>.combine(
    aboutDroidKaigiReducer.optional().pullback(
        state: \.aboutDroidKaigiState,
        action: /AboutLoadedAction.aboutDroidKaigi,
        environment: { environment in
            .init(
                applicationClient: environment.applicationClient
            )
        }
    ),
    .init { state, action, _ in
        switch action {
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
