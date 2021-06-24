import DroidKaigiMPP

public protocol FeedItem {
    var id: String { get set }
    var image: Image { get set }
    var link: String { get set }
    var media: Media { get set }
    var publishedAt: Date { get set }
    var summary: MultiLangText { get set }
    var title: MultiLangText { get set }
    var _kmmModel: DroidKaigiMPP.FeedItem { get } // swiftlint:disable:this identifier_name
}

@dynamicMemberLookup
public struct AnyFeedItem: Equatable {

    public var wrappedValue: FeedItem
    public var kmmModel: DroidKaigiMPP.FeedItem { wrappedValue._kmmModel }

    public init(_ feedItem: FeedItem) {
        self.wrappedValue = feedItem
    }

    public static func from(_ model: DroidKaigiMPP.FeedItem) -> AnyFeedItem? {
        switch model {
        case let blog as DroidKaigiMPP.FeedItem.Blog:
            return .init(Blog(from: blog))
        case let podcast as DroidKaigiMPP.FeedItem.Podcast:
            return .init(Podcast(from: podcast))
        case let video as DroidKaigiMPP.FeedItem.Video:
            return .init(Video(from: video))
        default:
            return nil
        }
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

    public subscript<T>(dynamicMember keyPath: KeyPath<FeedItem, T>) -> T {
        self.wrappedValue[keyPath: keyPath]
    }
}
