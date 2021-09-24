import ComposableArchitecture
import Model
import Repository

public struct TimetableState: Equatable {
    public var timetableItems: [AnyTimetableItem]
    public var selectedType: SelectedType
    // TODO: Replace with detail state
    public var detail: AnyTimetableItem?

    var isShowingDetail: Bool {
        detail != nil
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
        detail: AnyTimetableItem? = nil
    ) {
        self.timetableItems = timetableItems
        self.selectedType = selectedType
        self.detail = detail
    }
}

public enum TimetableAction {
    case refresh
    case refreshResponse(Result<[AnyTimetableItem], KotlinError>)
    case selectedPicker(SelectedType)
    case content(TimetableContentAction)
    case hideDetail
}

public struct TimetableEnvironment {
    public let timetableRepository: TimetableRepositoryProtocol

    public init(
        timetableRepository: TimetableRepositoryProtocol
    ) {
        self.timetableRepository = timetableRepository
    }
}

public let timetableReducer = Reducer<TimetableState, TimetableAction, TimetableEnvironment> { state, action, environment in
    switch action {
    case .refresh:
        return environment.timetableRepository.timetableContents()
            .receive(on: DispatchQueue.main)
            .catchToEffect()
            .map(TimetableAction.refreshResponse)
    case let .refreshResponse(.success(items)):
        state.timetableItems = items.sorted { $0.startsAt < $1.startsAt }
        return .none
    case let .refreshResponse(.failure(error)):
        print(error.localizedDescription)
        return .none
    case let .selectedPicker(type):
        state.selectedType = type
        return .none
    case let .content(.tap(item)):
        state.detail = item
        return .none
    case .hideDetail:
        state.detail = nil
        return .none
    }
}
