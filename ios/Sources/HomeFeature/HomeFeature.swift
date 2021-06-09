import Component
import ComposableArchitecture

// dummy feed model
public struct FeedItem: Equatable, Identifiable {
    public var id: String
    public var imageURLString: String
    public var link: String
    public var media: TagType
    public var publishedAt: Date
    public var summary: String
    public var title: String

    public init(id: String, imageURLString: String, link: String, media: TagType, publishedAt: Date, summary: String, title: String) {
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
    public var topic: FeedItem
    // TODO: Replace to real models
    public var contents: [FeedItem]
    public var message: String

    public init(
        topic: FeedItem,
        contents: [FeedItem] = [],
        message: String = ""
    ) {
        self.topic = topic
        self.contents = contents
        self.message = message
    }
}

public enum HomeAction {
    case answerQuestionnaire
}

public struct HomeEnvironment {
    public init() {}
}

public let homeReducer = Reducer<HomeState, HomeAction, HomeEnvironment> { state, action, environment in
    switch action {
    case .answerQuestionnaire:
        return .none
    }
}
