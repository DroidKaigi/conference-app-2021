import ComposableArchitecture
import Model

public struct AboutState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType: SelectedType
    public var showingURL: URL?
    public var isShowingAboutDroidKaigi: Bool

    public var isShowingWebView: Bool {
        showingURL != nil
    }

    public init(
        staffs: [Staff] = [],
        contributors: [Contributor] = [],
        selectedType: SelectedType = .staff,
        isShowingAboutDroidKaigi: Bool = false
    ) {
        self.staffs = staffs
        self.contributors = contributors
        self.selectedType = selectedType
        self.isShowingAboutDroidKaigi = isShowingAboutDroidKaigi
    }
}

public enum AboutAction {
    case refresh
    case selectedPicker(SelectedType)
    case tapStaff(Staff)
    case tapContributor(Contributor)
    case tapBanner
    case hideAboutDroidKaigi
    case hideWebView
}

public struct AboutEnvironment {
    public init() {}
}

public let aboutReducer = Reducer<AboutState, AboutAction, AboutEnvironment> { state, action, _ in
    switch action {
    case .refresh:
        return .none
    case .selectedPicker(let selectedType):
        state.selectedType = selectedType
        return .none
    case let .tapStaff(staff):
        state.showingURL = URL(string: staff.profileURLString)
        return .none
    case let .tapContributor(contributor):
        state.showingURL = URL(string: contributor.urlString)
        return .none
    case .tapBanner:
        state.isShowingAboutDroidKaigi = true
        return .none
    case .hideAboutDroidKaigi:
        state.isShowingAboutDroidKaigi = false
        return .none
    case .hideWebView:
        state.showingURL = nil
        return .none
    }
}
