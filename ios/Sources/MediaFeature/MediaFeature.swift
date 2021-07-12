import Component
import ComposableArchitecture
import Model
import Repository
import UIApplicationClient

public enum MediaState: Equatable {
    case needToInitialize
    case initialized(MediaListState)

    public init() {
        self = .needToInitialize
    }
}

public enum MediaAction {
    case refresh
    case refreshResponse(Result<[FeedContent], KotlinError>)
    case mediaList(MediaListAction)
}

public struct MediaEnvironment {
    public let feedRepository: FeedRepositoryProtocol
    public let applicationClient: UIApplicationClientProtocol

    public init(
        feedRepository: FeedRepositoryProtocol,
        applicationClient: UIApplicationClientProtocol
    ) {
        self.feedRepository = feedRepository
        self.applicationClient = applicationClient
    }
}

public let mediaReducer = Reducer<MediaState, MediaAction, MediaEnvironment>.combine(
    mediaListReducer.pullback(
        state: /MediaState.initialized,
        action: /MediaAction.mediaList,
        environment: {
            .init(
                feedRepository: $0.feedRepository,
                applicationClient: $0.applicationClient
            )
        }
    ),
    .init { state, action, environment in
        switch action {
        case .refresh:
            return environment.feedRepository.feedContents()
                .catchToEffect()
                .map(MediaAction.refreshResponse)
        case let .refreshResponse(.success(feedContents)):
            var blogs: [FeedContent] = []
            var videos: [FeedContent] = []
            var podcasts: [FeedContent] = []
            for feedContent in feedContents {
                switch feedContent.item.wrappedValue {
                case is Blog:
                    blogs.append(feedContent)
                case is Video:
                    videos.append(feedContent)
                case is Podcast:
                    podcasts.append(feedContent)
                default:
                    assertionFailure("Unexpected FeedItem: (\(feedContent.item.wrappedValue)")
                    break
                }
            }
            if var listState = (/MediaState.initialized).extract(from: state) {
                listState.blogs = blogs
                listState.videos = videos
                listState.podcasts = podcasts
                state = .initialized(listState)
            } else {
                state = .initialized(
                    .init(
                        feedContents: feedContents,
                        blogs: blogs,
                        videos: videos,
                        podcasts: podcasts
                    )
                )
            }
            return .none
        case let .refreshResponse(.failure(error)):
            print(error.localizedDescription)
            // TODO: Error handling
            return .none
        case .mediaList:
            return .none
        }
    }
)
