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
    @ObservedObject private var viewStore: ViewStore<ViewState, ViewAction>

    public init(store: Store<AppState, AppAction>) {
        self.store = store
        self.viewStore = ViewStore<ViewState, ViewAction>(
            store.scope(
                state: ViewState.init(state:),
                action: AppAction.init(action:)
            )
        )
        UITabBar.appearance().configureWithDefaultStyle()
        UINavigationBar.appearance().configureWithDefaultStyle()
    }

    internal struct ViewState: Equatable {
        init(state: AppState) {}
    }

    internal enum ViewAction {
        case progressViewAppeared
        case reload
    }

    public var body: some View {
        SwitchStore(store) {
            CaseLet(
                state: /AppState.needToInitialize,
                action: { (action: AppScreen.ViewAction) in
                    AppAction.init(action: action)
                },
                then: { _ in
                ProgressView()
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .background(AssetColor.Background.primary.color.ignoresSafeArea())
                    .onAppear { viewStore.send(.progressViewAppeared) }
                }
            )
            CaseLet(
                state: /AppState.initialized,
                action: AppAction.appTab,
                then: AppTabScreen.init(store:)
            )
            CaseLet(
                state: /AppState.errorOccurred,
                action: { (action: AppScreen.ViewAction) in
                    AppAction.init(action: action)
                },
                then: { _ in
                    ErrorView(tapReload: {
                        viewStore.send(.reload)
                    })
                }
            )
        }
        .accentColor(AssetColor.primary.color)
        .background(AssetColor.Background.primary.color)
    }
}

private extension AppAction {
    init(action: AppScreen.ViewAction) {
        switch action {
        case .progressViewAppeared:
            self = .refresh
        case .reload:
            self = .needRefresh
        }
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
                            ], language: .system
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
