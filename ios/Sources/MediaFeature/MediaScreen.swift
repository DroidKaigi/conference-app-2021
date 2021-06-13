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
                then: list(store:),
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

    struct ListViewState: Equatable {
        var hasBlogs: Bool
        var hasVideos: Bool
        var hasPodcasts: Bool

        init(state: MediaList) {
            hasBlogs = !state.blogs.isEmpty
            hasVideos = !state.videos.isEmpty
            hasPodcasts = !state.podcasts.isEmpty
        }
    }

    private func list(store: Store<MediaList, MediaAction>) -> some View {
        ScrollView {
            WithViewStore(store.scope(state: ListViewState.init(state:))) { viewStore in
                VStack(spacing: 0) {
                    if viewStore.hasBlogs {
                        MediaSection(
                            icon: AssetImage.iconBlog.image.renderingMode(.template),
                            title: L10n.MediaScreen.Session.Blog.title,
                            store: store.scope { $0.blogs }
                        )
                        divider
                    }
                    if viewStore.hasVideos {
                        MediaSection(
                            icon: AssetImage.iconVideo.image.renderingMode(.template),
                            title: L10n.MediaScreen.Session.Video.title,
                            store: store.scope { $0.videos }
                        )
                        divider
                    }
                    if viewStore.hasPodcasts {
                        MediaSection(
                            icon: AssetImage.iconPodcast.image.renderingMode(.template),
                            title: L10n.MediaScreen.Session.Podcast.title,
                            store: store.scope { $0.podcasts }
                        )
                    }
                }
            }
        }
        .background(AssetColor.Background.primary.color.ignoresSafeArea())
    }

    private var divider: some View {
        Divider()
            .padding()
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
            MediaScreen(
                store: .init(
                    initialState: .init(),
                    reducer: .empty,
                    environment: {}
                )
            )
                .environment(\.colorScheme, colorScheme)
        }
        .accentColor(AssetColor.primary.color)
    }
}
