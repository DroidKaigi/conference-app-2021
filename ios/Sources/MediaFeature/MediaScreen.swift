import ComposableArchitecture
import Introspect
import Model
import SwiftUI
import Styleguide

public struct MediaScreen: View {

    private let store: Store<MediaState, MediaAction>
    @ObservedObject var viewStore: ViewStore<ViewState, ViewAction>

    @SearchController private var searchController: UISearchController

    public init(store: Store<MediaState, MediaAction>) {
        self.store = store
        let viewStore = ViewStore(store.scope(state: ViewState.init(state:), action: MediaAction.init(action:)))
        self.viewStore = viewStore
        self._searchController = .init(searchBarPlaceHolder: L10n.MediaScreen.SearchBar.placeholder, searchTextDidChangeTo: { text in
            viewStore.send(.searchTextDidChange(to: text))
        }, willDismissSearchController: {
            viewStore.send(.willDismissSearchController)
        })
    }

    struct ViewState: Equatable {
        var isSearchBarEnabled: Bool
        var isInitialLoadingIndicatorVisible: Bool
        var isSearchResultVisible: Bool

        init(state: MediaState) {
            let isLoadingInitially = state.listState == nil
            isSearchBarEnabled = !isLoadingInitially
            isInitialLoadingIndicatorVisible = isLoadingInitially
            isSearchResultVisible = state.listState?.searchText?.isEmpty == false
        }
    }

    enum ViewAction {
        case progressViewAppeared
        case searchTextDidChange(to: String)
        case willDismissSearchController
    }

    public var body: some View {
        searchController.searchBar.isUserInteractionEnabled = viewStore.isSearchBarEnabled
        return NavigationView {
            ZStack {
                IfLetStore(
                    store.scope(state: \.listState?.list),
                    then: MediaListView.init(store:),
                    else: { ProgressView().onAppear { viewStore.send(.progressViewAppeared) } }
                )
                .zIndex(0)

                if viewStore.isSearchResultVisible {
                    SearchResultView()
                        .zIndex(1)
                }
            }
            .navigationTitle(L10n.MediaScreen.title)
            .navigationBarItems(
                trailing: AssetImage.iconSetting.image
                    .renderingMode(.template)
                    .foregroundColor(AssetColor.Base.primary.color)
            )
            .introspectViewController { viewController in
                viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
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
        case let .searchTextDidChange(to: text):
            self = .searchTextDidChange(to: text)
        case .willDismissSearchController:
            self = .willDismissSearchController
        }
    }
}

public struct MediaScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            Group {
                MediaScreen(
                    store: .init(
                        initialState: MediaState(),
                        reducer: .empty,
                        environment: {}
                    )
                )
                MediaScreen(
                    store: .init(
                        initialState: MediaState(listState: .init(list: .mock)),
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
