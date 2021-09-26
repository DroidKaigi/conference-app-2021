import ComposableArchitecture
import Model

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
    case selectedPicker(SelectedType)
    case tapStaff(Staff)
    case tapContributor(Contributor)
    case tapBanner
    case hideSheet
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
