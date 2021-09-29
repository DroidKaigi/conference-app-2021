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
        WithViewStore(store) { viewStore in
            VStack(alignment: .center, spacing: 0) {
                switch viewStore.type {
                case .needToInitialize:
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                        .background(AssetColor.Background.primary.color.ignoresSafeArea())
                        .onAppear { viewStore.send(.refresh) }
                case .initialized:
                    AppTabScreen(store: store.scope(state: \.appTabState, action: { (action: AppTabAction) in
                        AppAction.appTab(action)
                    }))
                case .errorOccurred:
                    ErrorView(tapReload: {
                        viewStore.send(.needRefresh)
                    })
                }
            }
            .accentColor(AssetColor.primary.color)
            .background(AssetColor.Background.primary.color)
            .onAppear {
                viewStore.send(.onAppear)
            }
        }
    }
}

#if DEBUG
 public struct AppScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            AppScreen(
                store: .init(
                    initialState: .init(type: .needToInitialize, language: .en),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            AppScreen(
                store: .init(
                    initialState: .init(type: .errorOccurred, language: .en),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            AppScreen(
                store: .init(
                    initialState: .init(type: .initialized, language: .en, feedContents: [
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock()
                    ]),
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
