import ComposableArchitecture
import Model

public struct TimetableState: Equatable {
    public var timetableItems: [TimetableItem]
    public var selectedType: SelectedType
    // TODO: Replace with detail state
    public var detail: TimetableItem?

    var isShowingDetail: Bool {
        detail != nil
    }

    public var selectedTypeItems: [TimetableItem] {
        let jaCalendar = Calendar(identifier: .japanese)
        let selectedDateComponents = selectedType.dateComponents
        return timetableItems
            .filter {
                let month = jaCalendar.component(.month, from: $0.startsAt)
                let day = jaCalendar.component(.day, from: $0.startsAt)
                return selectedDateComponents.month == month
                    && selectedDateComponents.day == day
            }
    }

    public init(
        timetableItems: [TimetableItem] = [],
        selectedType: SelectedType = .day1,
        detail: TimetableItem? = nil
    ) {
        self.timetableItems = timetableItems
        self.selectedType = selectedType
        self.detail = detail
    }
}

public enum TimetableAction {
    case selectedPicker(SelectedType)
    case content(TimetableContentAction)
    case hideDetail
}

public struct TimetableEnvironment {
    public init() {}
}

public let timetableReducer = Reducer<TimetableState, TimetableAction, TimetableEnvironment> { state, action, _ in
    switch action {
    case let .selectedPicker(type):
        state.selectedType = type
        return .none
    case let .content(.tap(item)):
        state.detail = item
        return .none
    case .content:
        return .none
    case .hideDetail:
        state.detail = nil
        return .none
    }
}
