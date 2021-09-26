import DroidKaigiMPP

public struct Special: TimetableItem, Equatable {
    public var id: String
    public var lang: String
    public var title: MultiLangText
    public var category: MultiLangText
    public var targetAudience: String
    public var asset: TimetableAsset
    public var levels: [String]
    public var speakers: [Speaker]
    public var startsAt: Date
    public var endsAt: Date

    public init(
        id: String,
        lang: String,
        title: MultiLangText,
        category: MultiLangText,
        targetAudience: String,
        asset: TimetableAsset,
        levels: [String],
        speakers: [Speaker],
        startsAt: Date,
        endsAt: Date
    ) {
        self.id = id
        self.lang = lang
        self.title = title
        self.category = category
        self.targetAudience = targetAudience
        self.asset = asset
        self.levels = levels
        self.speakers = speakers
        self.startsAt = startsAt
        self.endsAt = endsAt
    }

    public init(from model: DroidKaigiMPP.TimetableItem.Special) {
        self.init(
            id: model.id,
            lang: model.language,
            title: MultiLangText(from: model.title),
            category: MultiLangText(from: model.category.title),
            targetAudience: model.targetAudience,
            asset: TimetableAsset(from: model.asset),
            levels: model.levels,
            speakers: model.speakers.map(Speaker.init(from:)),
            startsAt: model.startsAt.toNSDate(),
            endsAt: model.endsAt.toNSDate()
        )
    }
}
