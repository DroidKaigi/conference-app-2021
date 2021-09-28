import DroidKaigiMPP

public protocol TimetableItem {
    var id: String { get set }
    var lang: String { get set }
    var title: MultiLangText { get set }
    var category: MultiLangText { get set }
    var targetAudience: String { get set }
    var asset: TimetableAsset { get set }
    var levels: [String] { get set }
    var speakers: [Speaker] { get set }
    var startsAt: Date { get set }
    var endsAt: Date { get set }
}

@dynamicMemberLookup
public struct AnyTimetableItem: Equatable, Identifiable {
    public var wrappedValue: TimetableItem
    
    public var id: String {
        wrappedValue.id
    }

    public init(_ timetableItem: TimetableItem) {
        self.wrappedValue = timetableItem
    }
    
    public init?(from model: DroidKaigiMPP.TimetableItem) {
        switch model {
        case let session as DroidKaigiMPP.TimetableItem.Session:
            self.init(Session(from: session))
        case let special as DroidKaigiMPP.TimetableItem.Special:
            self.init(Special(from: special))
        default:
            return nil
        }
    }
    
    public static func == (lhs: Self, rhs: Self) -> Bool {
        switch (lhs.wrappedValue, rhs.wrappedValue) {
        case let (lhs, rhs) as (Session, Session):
            return lhs == rhs
        case let (lhs, rhs) as (Special, Special):
            return lhs == rhs
        default:
            return false
        }
    }
    
    public subscript<T>(dynamicMember keyPath: KeyPath<TimetableItem, T>) -> T {
        self.wrappedValue[keyPath: keyPath]
    }
}

#if DEBUG
public extension AnyTimetableItem {
    static func sessionMock(
        id: String = UUID().uuidString,
        lang: String = "日本語",
        title: MultiLangText = .init(enTitle: "Session Item", jaTitle: "セッションアイテム"),
        category: MultiLangText = .init(enTitle: "Category", jaTitle: "カテゴリー"),
        targetAudience: String = "Target Audience",
        asset: TimetableAsset = .init(videoURLString: "", slideURLString: ""),
        levels: [String] = ["easy"],
        speakers: [Speaker] = [.mock()],
        startsAt: Date = Date(timeIntervalSince1970: 0),
        endsAt: Date = Date(timeIntervalSince1970: 100.0),
        description: String = "description",
        message: MultiLangText? = nil
    ) -> Self {
        .init(
            Session(
                id: id,
                lang: lang,
                title: title,
                category: category,
                targetAudience: targetAudience,
                asset: asset,
                levels: levels,
                speakers: speakers,
                startsAt: startsAt,
                endsAt: endsAt,
                description: description,
                message: message
            )
        )
    }
    
    static func specialMock(
        id: String = UUID().uuidString,
        lang: String = "日本語",
        title: MultiLangText = .init(enTitle: "Special Item", jaTitle: "スペシャルアイテム"),
        category: MultiLangText = .init(enTitle: "Category", jaTitle: "カテゴリー"),
        targetAudience: String = "Target Audience",
        asset: TimetableAsset = .init(videoURLString: "", slideURLString: ""),
        levels: [String] = ["easy"],
        speakers: [Speaker] = [.mock()],
        startsAt: Date = Date(timeIntervalSince1970: 0),
        endsAt: Date = Date(timeIntervalSince1970: 100.0)
    ) -> Self {
        .init(
            Special(
                id: id,
                lang: lang,
                title: title,
                category: category,
                targetAudience: targetAudience,
                asset: asset,
                levels: levels,
                speakers: speakers,
                startsAt: startsAt,
                endsAt: endsAt
            )
        )
    }
}
#endif
