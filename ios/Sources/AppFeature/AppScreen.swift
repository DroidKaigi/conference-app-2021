import AboutFeature
import Component
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
import SettingFeature
import Styleguide
import SwiftUI
import UIKit

public struct AppScreen: View {
    @State var selection = 0

    private let store: Store<AppState, AppAction>

    public init(store: Store<AppState, AppAction>) {
        self.store = store
        UITabBar.appearance().configureWithDefaultStyle()
        UINavigationBar.appearance().configureWithDefaultStyle()
    }

    public var body: some View {
        SwitchStore(store) {
            CaseLet(
                state: /AppState.needToInitialize,
                action: AppAction.loading,
                then: LoadingView.init(store:))
            CaseLet(
                state: /AppState.initialized,
                action: AppAction.appTab,
                then: AppTabScreen.init(store:)
            )
            CaseLet(
                state: /AppState.errorOccurred,
                action: AppAction.error,
                then: ErrorView.init(store:)
            )
        }
        .accentColor(AssetColor.primary.color)
        .background(AssetColor.Background.primary.color)
    }
}

#if DEBUG
 public struct AppScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            AppScreen(
                store: .init(
                    initialState: .needToInitialize,
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            AppScreen(
                store: .init(
                    initialState: .errorOccurred,
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            AppScreen(
                store: .init(
                    initialState: .initialized(
                        .init(
                            feedContents: [
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock()
                            ]
                        )
                    ),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)
        }
    }
 }
#endif
