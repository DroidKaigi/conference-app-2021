import ComposableArchitecture
import Model

public struct TimetableLoadedState: Equatable {
    public var timetableItems: [AnyTimetableItem]
    public var selectedType: SelectedType
    public var detailState: TimetableDetailState?

    var isShowingDetail: Bool {
        detailState != nil
    }

    public var selectedTypeItems: [AnyTimetableItem] {
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
        timetableItems: [AnyTimetableItem] = [],
        selectedType: SelectedType = .day1,
        detailState: TimetableDetailState? = nil
    ) {
        self.timetableItems = timetableItems
        self.selectedType = selectedType
        self.detailState = detailState
    }
}

public enum TimetableLoadedAction {
    case selectedPicker(SelectedType)
    case content(TimetableContentAction)
    case detail(TimetableDetailAction)
    case hideDetail
}

public struct TimetableLoadedEnvironment {
    public init() {}
}

public let timetableLoadedReducer = Reducer<TimetableLoadedState, TimetableLoadedAction, TimetableLoadedEnvironment>.combine(
    timetableDetailReducer.optional().pullback(
        state: \.detailState,
        action: /TimetableLoadedAction.detail,
        environment: { _ in
            .init()
        }
    ),
    .init { state, action, _ in
        switch action {
        case let .selectedPicker(type):
            state.selectedType = type
            return .none
        case let .content(.tap(item)):
            state.detailState = TimetableDetailState(timetable: item)
            return .none
        case .hideDetail:
            state.detailState = nil
            return .none
        case .detail:
            return .none
        }
    }
)
