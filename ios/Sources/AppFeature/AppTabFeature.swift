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
    public var showingURL: URL?
    public var isSettingPresented: Bool
    public var homeState: HomeState
    public var mediaState: MediaState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState

    public init(
        feedContents: [FeedContent],
        showingURL: URL? = nil,
        isSettingPresented: Bool = false
    ) {
        self.feedContents = feedContents
        self.showingURL = showingURL
        self.homeState = HomeState(feedContents: feedContents)
        self.mediaState = MediaState(feedContents: feedContents)
        self.favoritesState = FavoritesState(feedContents: feedContents.filter(\.isFavorited))
        self.aboutState = AboutState()
        self.isSettingPresented = isSettingPresented
    }
}

public enum AppTabAction {
    case reload
    case tap(FeedContent)
    case hideWebView
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
    case mediaView(MediaScreen.ViewAction)
    case about(AboutAction)
    case showSetting
    case hideSetting
    case none

    init(action: HomeAction) {
        switch action {
        case .tap(let feedContent):
            self = .tap(feedContent)
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        case .answerQuestionnaire:
            self = .none
        case .showSetting:
            self = .showSetting
        }
    }

    init(action: MediaAction) {
        switch action {
        case .tap(let feedContent):
            self = .tap(feedContent)
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        case .showSetting:
            self = .showSetting
        }
    }

    init(action: FavoritesAction) {
        switch action {
        case .tap(let feedContent):
            self = .tap(feedContent)
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        case .showSetting:
            self = .showSetting
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
    mediaViewReducer.pullback(
        state: \.mediaState,
        action: /AppTabAction.mediaView,
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
        action: /AppTabAction.about,
        environment: { _ -> AboutEnvironment in
            .init()
        }
    ),
    .init { state, action, environment in
        switch action {
        case .reload:
            return .none
        case .tap(let feedContent):
            state.showingURL = URL(string: feedContent.item.link)
            return .none
        case .hideWebView:
            state.showingURL = nil
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
            if let index = state.mediaState.searchedFeedContents.map(\.id).firstIndex(of: id) {
                state.mediaState.searchedFeedContents[index].isFavorited.toggle()
            }
            state.favoritesState.feedContents = state.feedContents.filter(\.isFavorited)
            return .none
        case let .favoriteResponse(.failure(error)):
            print(error.localizedDescription)
            return .none
        case .none:
            return .none
        case .mediaView:
            return .none
        case .about:
            return .none
        case .showSetting:
            state.isSettingPresented = true
            return .none
        case .hideSetting:
            state.isSettingPresented = false
            return .none
        }
    }
)
