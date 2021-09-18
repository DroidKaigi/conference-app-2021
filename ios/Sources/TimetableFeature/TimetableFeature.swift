import ComposableArchitecture

public struct TimetableState: Equatable {
    public init() {}
}

public enum TimetableAction {}

public struct TimetableEnvironment {
    public init() {}
}

public let timetableReducer = Reducer<TimetableState, TimetableAction, TimetableEnvironment> { _, _, _ in
    .none
}
