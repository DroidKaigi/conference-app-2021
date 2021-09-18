import DroidKaigiMPP

public struct Special: TimetableItem, Equatable {
    public var id: String
    public var title: MultiLangText
    public var startsAt: Date
    public var endsAt: Date

    public init(
        id: String,
        title: MultiLangText,
        startsAt: Date,
        endsAt: Date
    ) {
        self.id = id
        self.title = title
        self.startsAt = startsAt
        self.endsAt = endsAt
    }

    // TODO: Convert from KMM Model
}

#if DEBUG
public extension Special {
    static func mock(
        id: String = UUID().uuidString,
        title: MultiLangText = .init(enTitle: "Special", jaTitle: "スペシャル"),
        startsAt: Date = Date(timeIntervalSince1970: 0),
        endsAt: Date = Date(timeIntervalSince1970: 100)
    ) -> Self {
        .init(
            id: id,
            title: title,
            startsAt: startsAt,
            endsAt: endsAt
        )
    }
}
#endif
