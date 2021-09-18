import DroidKaigiMPP

public protocol TimetableItem {
    var id: String { get set }
    var title: MultiLangText { get set }
    var startsAt: Date { get set }
    var endsAt: Date { get set }
}

@dynamicMemberLookup
public struct AnyTimetableItem: Equatable {

    public var wrappedValue: TimetableItem

    public init(_ timetableItem: TimetableItem) {
        self.wrappedValue = timetableItem
    }

    public static func == (lhs: Self, rhs: Self) -> Bool {
        switch (lhs.wrappedValue, rhs.wrappedValue) {
        case let (lhs, rhs) as (Blog, Blog):
            return lhs == rhs
        case let (lhs, rhs) as  (Video, Video):
            return lhs == rhs
        case let (lhs, rhs) as  (Podcast, Podcast):
            return lhs == rhs
        default:
            return false
        }
    }

    public subscript<T>(dynamicMember keyPath: KeyPath<TimetableItem, T>) -> T {
        self.wrappedValue[keyPath: keyPath]
    }
}
