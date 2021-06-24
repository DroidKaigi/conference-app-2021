import DroidKaigiMPP

public struct FeedContent: Equatable, Identifiable {
    public var item: AnyFeedItem
    public var isFavorited: Bool
    public var id: String { item.id }

    public init(item: AnyFeedItem, isFavorited: Bool) {
        self.item = item
        self.isFavorited = isFavorited
    }

    public init?(from model: DroidKaigiMPP.KotlinPair<DroidKaigiMPP.FeedItem, KotlinBoolean>) {
        guard let item = model.first.flatMap(AnyFeedItem.from(_:)) else { return nil }
        self.item = item
        self.isFavorited = model.second?.boolValue ?? false
    }
}

extension Array where Element == FeedContent {
    public init(from model: DroidKaigiMPP.FeedContents) {
        self = model.contents.compactMap(FeedContent.init(from:))
    }
}
