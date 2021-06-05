import ComposableArchitecture

public struct AboutState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType = SelectedType.staff
    
    public init(staffs: [Staff] = [], contributors: [Contributor] = []) {
        // TODO: Replace with real data.
        self.staffs = dummyStaffs
        // TODO: Replace with real data.
        self.contributors = dummyContributors
    }
}

public enum AboutAction {
    case selectedPicker(SelectedType)
}

public struct AboutEnvironment {
    public init() {}
}

public let aboutReducer = Reducer<AboutState, AboutAction, AboutEnvironment> { state, action, environment in
    switch action {
    case .selectedPicker(let selectedType):
        state.selectedType = selectedType
        return .none
    }
}

