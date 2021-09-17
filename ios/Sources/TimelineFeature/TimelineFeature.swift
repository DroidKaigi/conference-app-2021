import ComposableArchitecture

public struct TimelineState: Equatable {
    public init() {}
}

public enum TimelineAction {}

public struct TimelineEnvironment {
    public init() {}
}

public let timelineReducer = Reducer<TimelineState, TimelineAction, TimelineEnvironment> { _, _, _ in
    .none
}
