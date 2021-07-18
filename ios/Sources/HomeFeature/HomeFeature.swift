import Component
import ComposableArchitecture
import Model
import Repository

public struct HomeState: Equatable {
    let feedContents: [FeedContent]
    public var message: String {
        "Finished! 🤖"
    }

    public var topic: FeedContent? {
        feedContents.first
    }

    public var listFeedContents: [FeedContent] {
        Array(feedContents.dropFirst())
    }

    public init(feedContents: [FeedContent]) {
        self.feedContents = feedContents
    }
}

public enum HomeAction {
    case selectFeedContent
    case tapFavorite(isFavorited: Bool, id: String)
    case answerQuestionnaire
}

public struct HomeEnvironment {
    public let feedRepository: FeedRepositoryProtocol

    public init(
        feedRepository: FeedRepositoryProtocol
    ) {
        self.feedRepository = feedRepository
    }
}
