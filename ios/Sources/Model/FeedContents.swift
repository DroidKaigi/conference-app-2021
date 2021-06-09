import DroidKaigiMPP

public struct FeedContents: Equatable {
    public struct Content: Equatable {
        public var item: FeedItemType
        public var isFavorited: Bool

        public init(item: FeedItemType, isFavorited: Bool) {
            self.item = item
            self.isFavorited = isFavorited
        }
    }

    public var contents: [Content]
    public var favorites: Set<String>
    public var feedItemContents: [FeedItemType]
}
