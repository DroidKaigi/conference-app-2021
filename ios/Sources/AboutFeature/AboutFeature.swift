import ComposableArchitecture
import Model
import UIApplicationClient

public struct AboutState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType: SelectedType
    public var showingURL: URL?

    public var isShowingWebView: Bool {
        showingURL != nil
    }

    public init(staffs: [Staff] = [], contributors: [Contributor] = [], selectedType: SelectedType = .staff) {
        self.staffs = staffs
        self.contributors = contributors
        self.selectedType = selectedType
    }
}

public enum AboutAction {
    case refresh
    case selectedPicker(SelectedType)
    case tapStaff(Staff)
    case tapContributor(Contributor)
    case hideWebView
}

public struct AboutEnvironment {
    public init() {}
}

public let aboutReducer = Reducer<AboutState, AboutAction, AboutEnvironment> { state, action, _ in
    switch action {
    case .refresh:
        // TODO: Fetch data from server
        state.staffs = [.mock(), .mock(), .mock()]
        state.contributors = [.mock(), .mock(), .mock()]
        return .none
    case .selectedPicker(let selectedType):
        state.selectedType = selectedType
        return .none
    case let .tapStaff(staff):
        state.showingURL = URL(string: staff.urlString)
        return .none
    case let .tapContributor(contributor):
        state.showingURL = URL(string: contributor.urlString)
        return .none
    case .hideWebView:
        state.showingURL = nil
        return .none
    }
}
