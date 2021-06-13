import ComposableArchitecture
import Introspect
import Model
import SwiftUI
import Styleguide

public struct MediaScreen: View {

    private let store: Store<MediaState, MediaAction>
    @ObservedObject var viewStore: ViewStore<ViewState, ViewAction>

    @SearchController(
        searchBarPlaceHolder: L10n.MediaScreen.SearchBar.placeholder,
        willBecomeActive: {
            // Send a action
        },
        willResignActive: {
            // Send a action
        },
        searchTextDidChange: { _ in
            // Send a action
        }
    ) private var searchController

    public init(store: Store<MediaState, MediaAction>) {
        self.store = store
        self.viewStore = .init(store.scope(state: ViewState.init(state:), action: MediaAction.init(action:)))
    }

    struct ViewState: Equatable {
        var isInitialLoadingIndicatorVisible: Bool

        init(state: MediaState) {
            isInitialLoadingIndicatorVisible = state.mediaList == nil
        }
    }

    enum ViewAction {
        case progressViewAppeared
    }

    public var body: some View {
        searchController.searchBar.isUserInteractionEnabled = !viewStore.isInitialLoadingIndicatorVisible
        return NavigationView {
            IfLetStore(
                store.scope(state: \.mediaList),
                then: MediaListView.init(store:),
                else: { ProgressView().onAppear { viewStore.send(.progressViewAppeared) } }
            )
            .navigationTitle(L10n.MediaScreen.title)
            .navigationBarItems(
                trailing: AssetImage.iconSetting.image
                    .renderingMode(.template)
                    .foregroundColor(AssetColor.Base.primary.color)
            )
            .introspectViewController { viewController in
                guard viewController.navigationItem.searchController == nil else { return }
                viewController.navigationItem.searchController = searchController
                viewController.navigationItem.hidesSearchBarWhenScrolling = false
                // To keep the navigation bar expanded
                viewController.navigationController?.navigationBar.sizeToFit()
            }
        }
    }
}

extension MediaAction {
    init(action: MediaScreen.ViewAction) {
        switch action {
        case .progressViewAppeared:
            self = .loadItems
        }
    }
}

public struct MediaScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            Group {
                var initialState = MediaState()
                MediaScreen(
                    store: .init(
                        initialState: initialState,
                        reducer: .empty,
                        environment: {}
                    )
                )
                let _ = initialState.mediaList = .mock
                MediaScreen(
                    store: .init(
                        initialState: initialState,
                        reducer: .empty,
                        environment: {}
                    )
                )
            }
            .environment(\.colorScheme, colorScheme)
        }
        .accentColor(AssetColor.primary.color)
    }
}
