import ComposableArchitecture
import Model
import UIApplicationClient

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
    case refresh
    case selectedPicker(SelectedType)
    case tapStaff(Staff)
    case tapContributor(Contributor)
    case urlOpened(Bool)
}

public struct AboutEnvironment {
    public let applicationClient: UIApplicationClientProtocol

    public init(
        applicationClient: UIApplicationClientProtocol
    ) {
        self.applicationClient = applicationClient
    }
}

public let aboutReducer = Reducer<AboutState, AboutAction, AboutEnvironment> { state, action, environment in
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
        if let url = URL(string: staff.urlString) {
            return environment.applicationClient.open(
                url: url,
                options: [:]
            )
            .eraseToEffect()
            .map(AboutAction.urlOpened)
        }
        return .none
    case let .tapContributor(contributor):
        if let url = URL(string: contributor.urlString) {
            return environment.applicationClient.open(
                url: url,
                options: [:]
            )
            .eraseToEffect()
            .map(AboutAction.urlOpened)
        }
        return .none
    case .urlOpened:
        return .none
    }
}
