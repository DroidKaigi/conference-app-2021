import ComposableArchitecture
import Component
import Introspect
import Repository
import Model
import SwiftUI
import Styleguide

public struct FavoritesScreen: View {
    private let store: Store<FavoritesState, FavoritesAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, ViewAction>

    public init(store: Store<FavoritesState, FavoritesAction>) {
        self.store = store
        self.viewStore = ViewStore<ViewState, ViewAction>(
            store.scope(
                state: ViewState.init(state:),
                action: FavoritesAction.init(action:)
            )
        )
    }

    internal struct ViewState: Equatable {
        init(state: FavoritesState) {}
    }

    internal enum ViewAction {
        case progressViewAppeared
    }

    public var body: some View {
        NavigationView {
            SwitchStore(store) {
                CaseLet(
                    state: /FavoritesState.needToInitialize,
                    action: FavoritesAction.init(action:)) { _ in
                    ProgressView()
                        .onAppear { viewStore.send(.progressViewAppeared) }
                }
                CaseLet(
                    state: /FavoritesState.emptyInitialized,
                    action: FavoritesAction.init(action:)) { _ in
                    // TODO: create view when empty
                    Text("表示するコンテンツがありません")
                }
                CaseLet(
                    state: /FavoritesState.initialized,
                    action: FavoritesAction.init(action:),
                    then: FavoritesListView.init(store:)
                )
            }
            .navigationBarTitle(L10n.FavoriteScreen.title, displayMode: .large)
            .navigationBarItems(
                trailing: AssetImage.iconSetting.image
                    .renderingMode(.template)
                    .foregroundColor(AssetColor.Base.primary.color)
            )
            .introspectViewController { viewController in
                viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
            }
        }
    }
}

private extension FavoritesAction {
    init(action: FavoritesScreen.ViewAction) {
        switch action {
        case .progressViewAppeared:
            self = .refresh
        }
    }

    init(action: FavoritesListAction) {
        self = .favoritesList(action)
    }
}

#if DEBUG
public struct FavoritesScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            FavoritesScreen(
                store: .init(
                    initialState: .needToInitialize,
                    reducer: .empty,
                    environment: FavoritesEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            FavoritesScreen(
                store: .init(
                    initialState: .needToInitialize,
                    reducer: .empty,
                    environment: FavoritesEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)

            FavoritesScreen(
                store: .init(
                    initialState: .emptyInitialized,
                    reducer: .empty,
                    environment: FavoritesEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            FavoritesScreen(
                store: .init(
                    initialState: .emptyInitialized,
                    reducer: .empty,
                    environment: FavoritesEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)

            FavoritesScreen(
                store: .init(
                    initialState: .initialized(.init(feedContents: [.videoMock(), .videoMock()])),
                    reducer: .empty,
                    environment: FavoritesEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            FavoritesScreen(
                store: .init(
                    initialState: .initialized(.init(feedContents: [.videoMock(), .videoMock()])),
                    reducer: .empty,
                    environment: FavoritesEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}
#endif
