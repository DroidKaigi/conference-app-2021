import DroidKaigiMPP

public struct Session: TimetableItem, Equatable {
    public var id: String
    public var title: MultiLangText
    public var speakers: [Speaker]
    public var startsAt: Date
    public var endsAt: Date

    public init(
        id: String,
        title: MultiLangText,
        speakers: [Speaker],
        startsAt: Date,
        endsAt: Date
    ) {
        self.id = id
        self.title = title
        self.speakers = speakers
        self.startsAt = startsAt
        self.endsAt = endsAt
    }

    // TODO: Convert from KMM Model
}

#if DEBUG
public extension Session {
    static func mock(
        id: String = UUID().uuidString,
        title: MultiLangText = .init(enTitle: "Session", jaTitle: "セッション"),
        speakers: [Speaker] = [Speaker.mock()],
        startsAt: Date = Date(timeIntervalSince1970: 0),
        endsAt: Date = Date(timeIntervalSince1970: 100)
    ) -> Self {
        .init(
            id: id,
            title: title,
            speakers: speakers,
            startsAt: startsAt,
            endsAt: endsAt
        )
    }
}
#endif
