import DroidKaigiMPP

public struct FeedContents: Equatable {
    public struct Content: Equatable {
        public var item: FeedItemType?
        public var isFavorited: Bool

        public init(item: FeedItemType, isFavorited: Bool) {
            self.item = item
            self.isFavorited = isFavorited
        }

        public init(from model: DroidKaigiMPP.KotlinPair<DroidKaigiMPP.FeedItem, KotlinBoolean>) {
            self.item = model.first.flatMap(FeedItemType.from(_:))
            self.isFavorited = model.second?.boolValue ?? false
        }
    }

    public var contents: [Content]
    public var favorites: Set<String>
    public var feedItemContents: [FeedItemType]

    public init(
        contents: [Content],
        favorites: Set<String>,
        feedItemContents: [FeedItemType]
    ) {
        self.contents = contents
        self.favorites = favorites
        self.feedItemContents = feedItemContents
    }

    public init(from model: DroidKaigiMPP.FeedContents) {
        self.contents = model.contents.map(Content.init(from:))
        self.favorites = model.favorites
        self.feedItemContents = model.feedItemContents.compactMap(FeedItemType.from(_:))
    }
}
