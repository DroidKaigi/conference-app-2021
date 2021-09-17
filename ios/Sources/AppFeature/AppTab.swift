import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Styleguide
import SwiftUI
import TimelineFeature

enum AppTab: CaseIterable {
    case home
    case timeline
    case media
    case favorites
    case about

    @ViewBuilder
    func view(_ store: Store<AppTabState, AppTabAction>) -> some View {
        switch self {
        case .home:
            HomeScreen(
                store: store.scope(
                    state: \.homeState,
                    action: { (action: HomeAction) in
                        AppTabAction.init(action: action)
                    }
                )
            )
        case .timeline:
            TimelineScreen(
                store: store.scope(
                    state: \.timelineState,
                    action: AppTabAction.timeline
                )
            )
        case .media:
            MediaScreen(
                store: store.scope(
                    state: \.mediaState,
                    action: AppTabAction.media
                )
            )
        case .favorites:
            FavoritesScreen(
                store: store.scope(
                    state: \.favoritesState,
                    action: { (action: FavoritesAction) in
                        AppTabAction.init(action: action)
                    }
                )
            )
        case .about:
            AboutScreen(
                store: store.scope(
                    state: \.aboutState,
                    action: AppTabAction.about
                )
            )
        }
    }
}
