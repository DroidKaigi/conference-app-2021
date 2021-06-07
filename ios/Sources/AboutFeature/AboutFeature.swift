import ComposableArchitecture

public struct AboutState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType: SelectedType
    
    public init(staffs: [Staff] = [], contributors: [Contributor] = [], selectedType: SelectedType = .staff) {
        self.staffs = staffs
        self.contributors = contributors
        self.selectedType = selectedType
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

