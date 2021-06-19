import Component
import ComposableArchitecture
import Model

// dummy feed model
public struct FeedItem: Equatable, Identifiable {
    public var id: String
    public var imageURLString: String
    public var link: String
    public var media: Media
    public var publishedAt: Date
    public var summary: String
    public var title: String

    public init(id: String, imageURLString: String, link: String, media: Media, publishedAt: Date, summary: String, title: String) {
        self.id = id
        self.imageURLString = imageURLString
        self.link = link
        self.media = media
        self.publishedAt = publishedAt
        self.summary = summary
        self.title = title
    }
}

public struct HomeState: Equatable {
    // TODO: Replace to real models
    private var feedItems: [FeedItem]
    public var message: String

    public var topic: FeedItem? {
        feedItems.first
    }

    public var listFeedItems: [FeedItem] {
        Array(feedItems.dropFirst())
    }

    public init(
        feedItems: [FeedItem] = [],
        message: String = ""
    ) {
        self.feedItems = feedItems
        self.message = message
    }
}

public enum HomeAction {
    case answerQuestionnaire
}

public struct HomeEnvironment {
    public init() {}
}

public let homeReducer = Reducer<HomeState, HomeAction, HomeEnvironment> { _, action, _ in
    switch action {
    case .answerQuestionnaire:
        return .none
    }
}
