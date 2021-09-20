import DroidKaigiMPP

public enum TimetableItemType {
    case session
    case special
}

public struct TimetableItem: Equatable, Identifiable {
    public var id: String
    public var type: TimetableItemType
    public var lang: Lang
    public var title: MultiLangText
    public var category: String
    public var speakers: [Speaker]
    public var startsAt: Date
    public var endsAt: Date
    
    public init(
        id: String,
        type: TimetableItemType,
        lang: Lang,
        title: MultiLangText,
        category: String,
        speakers: [Speaker],
        startsAt: Date,
        endsAt: Date
    ) {
        self.id = id
        self.type = type
        self.lang = lang
        self.title = title
        self.category = category
        self.speakers = speakers
        self.startsAt = startsAt
        self.endsAt = endsAt
    }
    
    // TODO: Convert from KMM Model
}

#if DEBUG
public extension TimetableItem {
    static func mock(
        id: String = UUID().uuidString,
        type: TimetableItemType = .session,
        lang: Lang = .ja,
        title: MultiLangText = .init(enTitle: "Timetable Item", jaTitle: "タイムテーブルアイテム"),
        category: String = "Beginner",
        speakers: [Speaker] = [.mock()],
        startsAt: Date = Date(timeIntervalSince1970: 0),
        endsAt: Date = Date(timeIntervalSince1970: 100.0)
    ) -> Self {
        .init(
            id: id,
            type: type,
            lang: lang,
            title: title,
            category: category,
            speakers: speakers,
            startsAt: startsAt,
            endsAt: endsAt
        )
    }
}
#endif
