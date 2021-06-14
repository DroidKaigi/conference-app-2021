import ComposableArchitecture

public struct AboutState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType: SelectedType

    public init(staffs: [Staff] = [], contributors: [Contributor] = [], selectedType: SelectedType = .staff) {
        // TODO: Replace with real data.
        self.staffs = dummyStaffs
        // TODO: Replace with real data.
        self.contributors = dummyContributors
        self.selectedType = selectedType
    }
}

public enum AboutAction {
    case selectedPicker(SelectedType)
}

public struct AboutEnvironment {
    public init() {}
}

public let aboutReducer = Reducer<AboutState, AboutAction, AboutEnvironment> { state, action, _ in
    switch action {
    case .selectedPicker(let selectedType):
        state.selectedType = selectedType
        return .none
    }
}
