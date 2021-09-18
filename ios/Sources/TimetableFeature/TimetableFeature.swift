import ComposableArchitecture
import Model

public struct TimetableState: Equatable {
    public var timetableItems: [TimetableItem]

    public init(
        timetableItems: [TimetableItem] = []
    ) {
        self.timetableItems = timetableItems
    }
}

public enum TimetableAction {}

public struct TimetableEnvironment {
    public init() {}
}

public let timetableReducer = Reducer<TimetableState, TimetableAction, TimetableEnvironment> { _, _, _ in
    .none
}
