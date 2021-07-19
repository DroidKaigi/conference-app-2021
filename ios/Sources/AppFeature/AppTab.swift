import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import Styleguide
import SwiftUI

enum AppTab: CaseIterable {
    case home
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
                    action: AppTabAction.init(action:)
                )
            )
        case .media:
            MediaScreen(
                store: store.scope(
                    state: \.mediaState,
                    action: AppTabAction.init(action:)
                )
            )
        case .favorites:
            FavoritesScreen(
                store: store.scope(
                    state: \.favoritesState,
                    action: AppTabAction.init(action:)
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
