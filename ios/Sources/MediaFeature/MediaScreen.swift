import Component
import ComposableArchitecture
import Introspect
import Model
import SwiftUI
import Styleguide

public struct MediaScreen: View {

    private let store: Store<MediaState, MediaAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, ViewAction>
    @SearchController private var searchController: UISearchController

    public init(store: Store<MediaState, MediaAction>) {
        self.store = store
        let viewStore = ViewStore<ViewState, ViewAction>(store.scope(state: ViewState.init(state:), action: MediaAction.init(action:)))
        self.viewStore = viewStore
        self._searchController = .init(
            searchBarPlaceHolder: L10n.MediaScreen.SearchBar.placeholder,
            searchTextDidChangeTo: { text in
                viewStore.send(.searchTextDidChange(to: text))
            },
            isEditingDidChangeTo: { isEditing in
                viewStore.send(.isEditingDidChange(to: isEditing))
            }
        )
    }

    struct ViewState: Equatable {
        var isSearchBarEnabled: Bool

        init(state: MediaState) {
            if case .initialized = state {
                isSearchBarEnabled = true
            } else {
                isSearchBarEnabled = false
            }
        }
    }

    enum ViewAction {
        case progressViewAppeared
        case searchTextDidChange(to: String)
        case isEditingDidChange(to: Bool)
    }

    public var body: some View {
        searchController.searchBar.isUserInteractionEnabled = viewStore.isSearchBarEnabled
        return NavigationView {
            SwitchStore(store) {
                CaseLet(
                    state: /MediaState.initialized,
                    action: MediaAction.init(action:),
                    then: MediaListView.init(store:)
                )
                CaseLet(
                    state: /MediaState.needToInitialize,
                    action: MediaAction.init(action:)) { _ in
                    ProgressView()
                        .onAppear { viewStore.send(.progressViewAppeared) }
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

private extension MediaAction {
    init(action: MediaScreen.ViewAction) {
        switch action {
        case .progressViewAppeared:
            self = .refresh
        case let .searchTextDidChange(to: text):
            self = .mediaList(.searchTextDidChange(to: text))
        case let .isEditingDidChange(isEditing):
            self = .mediaList(.isEditingDidChange(to: isEditing))
        }
    }

    init(action: MediaListAction) {
        self = .mediaList(action)
    }
}

#if DEBUG
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
                        initialState: MediaState.initialized(
                            .init(
                                feedContents: [],
                                blogs: [.blogMock(), .blogMock()],
                                videos: [.videoMock(), .videoMock()],
                                podcasts: [.podcastMock(), .podcastMock()]
                            )
                        ),
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
#endif
