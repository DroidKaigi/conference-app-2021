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
    public var isSheetPresented: AppTabSheet?
    public var homeState: HomeState
    public var mediaState: MediaState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState

    public enum AppTabSheet: Equatable {
        case url(URL)
        case setting
    }

    public init(
        feedContents: [FeedContent],
        isSheetPresented: AppTabSheet? = nil
    ) {
        self.feedContents = feedContents
        self.isSheetPresented = isSheetPresented
        self.homeState = HomeState(feedContents: feedContents)
        self.mediaState = MediaState(feedContents: feedContents)
        self.favoritesState = FavoritesState(feedContents: feedContents.filter(\.isFavorited))
        self.aboutState = AboutState()
    }
}

public enum AppTabAction {
    case reload
    case tap(FeedContent)
    case hideSheet
    case tapFavorite(isFavorited: Bool, id: String)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
    case media(MediaAction)
    case about(AboutAction)
    case showSetting
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
        action: /AppTabAction.media,
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
        case .tap(let feedContent), .media(.tap(let feedContent)):
            state.isSheetPresented = .url(URL(string: feedContent.item.link)!)
            return .none
        case .hideSheet:
            state.isSheetPresented = nil
            return .none
        case .answerQuestionnaire:
            return .none
        case .tapFavorite(let isFavorited, let id), .media(.tapFavorite(let isFavorited, let id)):
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
        case .showSetting, .media(.showSetting):
            state.isSheetPresented = .setting
            return .none
        case .none:
            return .none
        case .media:
            return .none
        case .about:
            return .none
        }
    }
)
