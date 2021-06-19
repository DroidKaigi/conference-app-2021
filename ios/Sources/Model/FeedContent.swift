import DroidKaigiMPP

public struct FeedContent: Equatable, Identifiable {
    public var item: FeedItemType
    public var isFavorited: Bool
    public var feedItem: FeedItem {
        switch item {
        case let .blog(blog):
            return blog.feedItem
        case let .podcast(podcast):
            return podcast.feedItem
        case let .video(video):
            return video.feedItem
        }
    }
    public var id: String {
        feedItem.id
    }

    public init(item: FeedItemType, isFavorited: Bool) {
        self.item = item
        self.isFavorited = isFavorited
    }

    public init?(from model: DroidKaigiMPP.KotlinPair<DroidKaigiMPP.FeedItem, KotlinBoolean>) {
        guard let item = model.first.flatMap(FeedItemType.from(_:)) else { return nil }
        self.item = item
        self.isFavorited = model.second?.boolValue ?? false
    }
}

extension Array where Element == FeedContent {
    public init(from model: DroidKaigiMPP.FeedContents) {
        self = model.contents.compactMap(FeedContent.init(from:))
    }
}
