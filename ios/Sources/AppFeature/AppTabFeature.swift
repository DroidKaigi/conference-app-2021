import Combine
import ComposableArchitecture
import HomeFeature
import MediaFeature
import FavoritesFeature
import SettingFeature
import AboutFeature
import SwiftUI
import Model
import Repository
import TimetableFeature

public struct AppTabState: Equatable {
    public var feedContents: [FeedContent] {
        didSet {
            homeState.feedContents = feedContents
            mediaState.feedContents = feedContents
            favoritesState.feedContents = feedContents.filter(\.isFavorited)
        }
    }
    public var isSheetPresented: AppTabSheet?
    public var homeState: HomeState
    public var timetableState: TimetableState
    public var mediaState: MediaState
    public var favoritesState: FavoritesState
    public var aboutState: AboutState
    public var language: Lang

    public enum AppTabSheet: Equatable {
        case url(URL)
        case setting
    }

    public init(
        feedContents: [FeedContent],
        language: Lang,
        isSheetPresented: AppTabSheet? = nil
    ) {
        self.feedContents = feedContents
        self.isSheetPresented = isSheetPresented
        self.language = language
        self.homeState = HomeState(feedContents: feedContents, language: language)
        self.mediaState = MediaState(feedContents: feedContents, language: language)
        self.favoritesState = FavoritesState(feedContents: feedContents.filter(\.isFavorited), language: language)
        self.aboutState = AboutState()
        self.timetableState = TimetableState(language: language)
    }

    public var settingState: SettingState {
        get {
            SettingState(language: language)
        }
        set {
            language = newValue.language
        }
    }
}

public enum AppTabAction {
    case reload
    case tap(FeedContent)
    case hideSheet
    case tapFavorite(isFavorited: Bool, id: String)
    case tapPlay(FeedContent)
    case favoriteResponse(Result<String, KotlinError>)
    case answerQuestionnaire
    case timetable(TimetableAction)
    case media(MediaAction)
    case about(AboutAction)
    case showSetting
    case setting(SettingAction)
    case none

    init(action: HomeAction) {
        switch action {
        case .tap(let feedContent):
            self = .tap(feedContent)
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        case .tapPlay(let feedContent):
            self = .tapPlay(feedContent)
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
        case .tapPlay(let feedContent):
            self = .tapPlay(feedContent)
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
    timetableReducer.pullback(
        state: \.timetableState,
        action: /AppTabAction.timetable,
        environment: { environment in
            .init(
                timetableRepository: environment.timetableRepository
            )
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
    settingReducer.pullback(
        state: \.settingState,
        action: /AppTabAction.setting,
        environment: { environment in
            .init(languageRepository: environment.languageRepository)
        }
    ),
    aboutReducer.pullback(
        state: \.aboutState,
        action: /AppTabAction.about,
        environment: { environment in
            .init(
                applicationClient: environment.applicationClient,
                contributorRepository: environment.contributorRepository,
                staffRepository: environment.staffRepository
            )
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
                .receive(on: DispatchQueue.main)
                .catchToEffect()
                .map(AppTabAction.favoriteResponse)
        case let .tapPlay(content):
            if let podcast = content.item.wrappedValue as? Podcast, let index = state.feedContents.map(\.id).firstIndex(of: content.id) {
                if environment.player.isPlaying {
                    environment.player.stop()
                    state.feedContents[index].item.wrappedValue.media = .droidKaigiFm(isPlaying: false)
                } else {
                    environment.player.setUpPlayer(url: URL(string: podcast.podcastLink)!)
                    if let playingIndex = state.feedContents.firstIndex(where: {
                        $0.item.media == .droidKaigiFm(isPlaying: true)
                    }) {
                        state.feedContents[playingIndex].item.wrappedValue.media = .droidKaigiFm(isPlaying: false)
                    }
                    state.feedContents[index].item.wrappedValue.media = .droidKaigiFm(isPlaying: true)
                }
            }
            return .none
        case let .favoriteResponse(.success(id)):
            if let index = state.feedContents.map(\.id).firstIndex(of: id) {
                state.feedContents[index].isFavorited.toggle()
            }
            return .none
        case let .favoriteResponse(.failure(error)):
            print(error.localizedDescription)
            return .none
        case .showSetting, .media(.showSetting), .timetable(.loaded(.showSetting)):
            state.isSheetPresented = .setting
            return .none
        case .none:
            return .none
        case .media:
            return .none
        case .about:
            return .none
        case .timetable:
            return .none
        case .setting:
            return .none
        }
    }
)
