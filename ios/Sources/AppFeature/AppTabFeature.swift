import ComposableArchitecture
import HomeFeature
import MediaFeature
import FavoritesFeature
import AboutFeature
import SwiftUI
import Model
import Repository

public struct AppTabState: Equatable {
    public var feedContents: [FeedContent]
    public var homeState: HomeState
    public var mediaState: MediaState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState

    public init(
        feedContents: [FeedContent]
    ) {
        self.feedContents = feedContents
        self.homeState = HomeState(feedContents: feedContents)
        self.mediaState = MediaState(feedContents: feedContents)
        self.favoritesState = FavoritesState(feedContents: feedContents.filter(\.isFavorited))
        self.aboutState = AboutState()
    }
}

public enum AppTabAction {
    case reload
    case selectFeedContent
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
    case none

    init(action: HomeAction) {
        switch action {
        case .selectFeedContent:
            self = .selectFeedContent
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        case .answerQuestionnaire:
            self = .none
        }
    }

    init(action: MediaAction) {
        switch action {
        case .searchTextDidChange:
            self = .none
        case .isEditingDidChange:
            self = .none
        case .showMore:
            self = .none
        case .moreDismissed:
            self = .none
        case .tap:
            self = .selectFeedContent
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        }
    }

    init(action: FavoritesAction) {
        switch action {
        case .tap:
            self = .selectFeedContent
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        }
    }

    init(action: AboutAction) {
        switch action {
        case .selectedPicker:
            self = .none
        }
    }
}

public let appTabReducer = Reducer<AppTabState, AppTabAction, AppEnvironment>.combine(
    homeReducer.pullback(
        state: \.homeState,
        action: /AppTabAction.init(action:),
        environment: { _ in
            .init()
        }
    ),
    mediaReducer.pullback(
        state: \.mediaState,
        action: /AppTabAction.init(action:),
        environment: { _ in
            .init()
        }
    ),
    favoritesReducer.pullback(
        state: \.favoritesState,
        action: /AppTabAction.init(action:),
        environment: { _ in
            .init()
        }
    ),
    aboutReducer.pullback(
        state: \.aboutState,
        action: /AppTabAction.init(action:),
        environment: { _ -> AboutEnvironment in
            .init()
        }
    ),
    .init { state, action, environment in
        switch action {
        case .reload:
            return .none
        case .selectFeedContent:
            return .none
        case .answerQuestionnaire:
            return .none
        case .tapFavorite(let isFavorited, let id):
            let publisher = isFavorited
                ? environment.feedRepository.removeFavorite(id: id)
                : environment.feedRepository.addFavorite(id: id)
            return publisher
                .map { id }
                .catchToEffect()
                .map(AppTabAction.favoriteResponse)
        case let .favoriteResponse(.success(id)):
            if let index = state.feedContents.map(\.id).firstIndex(of: id) {
                state.feedContents[index].isFavorited.toggle()
            }
            state.homeState.feedContents = state.feedContents
            state.mediaState.feedContents = state.feedContents
            state.favoritesState.feedContents = state.feedContents.filter(\.isFavorited)
            return .none
        case let .favoriteResponse(.failure(error)):
            print(error.localizedDescription)
            return .none
        case .none:
            return .none
        }
    }
)
