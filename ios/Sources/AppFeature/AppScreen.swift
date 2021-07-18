import AboutFeature
import ComposableArchitecture
import FavoritesFeature
import HomeFeature
import MediaFeature
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
                action: AppAction.init(action:)) { _ in
                ProgressView()
                    .onAppear { viewStore.send(.progressViewAppeared) }
            }
            CaseLet(
                state: /AppState.initialized,
                action: AppAction.appTab,
                then: AppTabScreen.init(store:)
            )
            CaseLet(
                state: /AppState.errorOccurred,
                action: AppAction.init(action:)) { _ in
                VStack(spacing: 16) {
                    Text("エラーが発生しました")

                    Button(action: {
                        viewStore.send(.reload)
                    }, label: {
                        Text("再度読み込み")
                            .foregroundColor(AssetColor.primary.color)
                    })
                }
            }
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
