import Component
import ComposableArchitecture
import Introspect
import Model
import SwiftUI
import Styleguide

public struct MediaScreen: View {
    private let store: Store<MediaState, MediaAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, MediaAction>
    @SearchController private var searchController: UISearchController

    public init(store: Store<MediaState, MediaAction>) {
        self.store = store
        let viewStore = ViewStore(store.scope(state: ViewState.init(state:)))
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
        var isSearchTextEditing: Bool
        var searchedFeedContents: [FeedContent]
        var isSearchResultVisible: Bool
        var isMoreActive: Bool

        init(state: MediaState) {
            isSearchTextEditing = state.isSearchTextEditing
            searchedFeedContents = state.searchedFeedContents
            isSearchResultVisible = state.isSearchResultVisible
            isMoreActive = state.moreActiveType != nil
        }
    }

    public var body: some View {
        NavigationView {
            ZStack {
                AssetColor.Background.primary.color.ignoresSafeArea()
                    .zIndex(0)

                MediaListView(store: store)
                    .zIndex(1)

                Color.black.opacity(0.4)
                    .opacity(viewStore.isSearchTextEditing ? 1 : .zero)
                    .animation(.easeInOut)
                    .zIndex(2)

                SearchResultScreen(
                    feedContents: viewStore.searchedFeedContents,
                    tap: { feedContent in
                        viewStore.send(.tap(feedContent))
                    },
                    tapFavorite: { isFavorited, contentId in
                        viewStore.send(.tapFavorite(isFavorited: isFavorited, id: contentId))
                    }
                )
                .opacity(viewStore.isSearchResultVisible ? 1 : .zero)
                .zIndex(3)
            }
            .animation(.easeInOut)
            .background(
                NavigationLink(
                    destination: IfLetStore(
                        store.scope(
                            state: MediaDetailScreen.ViewState.init(state:),
                            action: MediaAction.init(action:)
                        ),
                        then: MediaDetailScreen.init(store:)
                    ),
                    isActive: viewStore.binding(
                        get: \.isMoreActive,
                        send: { _ in .moreDismissed }
                    )
                ) {
                    EmptyView()
                }
            )
            .navigationTitle(L10n.MediaScreen.title)
            .navigationBarItems(
                trailing: Button(action: {
                    viewStore.send(.showSetting)
                }, label: {
                    AssetImage.iconSetting.image
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                })
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

    private var separator: some View {
        Separator()
            .padding()
    }
}

private extension MediaAction {
    init(action: MediaDetailScreen.ViewAction) {
        switch action {
        case .tap(let content):
            self = .tap(content)
        case .tapFavorite(let isFavorited, let contentId):
            self = .tapFavorite(isFavorited: isFavorited, id: contentId)
        }
    }
}

private extension MediaDetailScreen.ViewState {
    init?(state: MediaState) {
        guard let moreActiveType = state.moreActiveType else { return nil }
        switch moreActiveType {
        case .blog:
            title = L10n.MediaScreen.Section.Blog.title
            contents = state.blogs
        case .video:
            title = L10n.MediaScreen.Section.Video.title
            contents = state.videos
        case .podcast:
            title = L10n.MediaScreen.Section.Podcast.title
            contents = state.podcasts
        }
    }
}

#if DEBUG
public struct MediaScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            Group {
                MediaScreen(
                    store: .init(
                        initialState: .init(
                            feedContents: [
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .videoMock(),
                                .videoMock(),
                                .videoMock(),
                                .podcastMock(),
                                .podcastMock(),
                                .podcastMock()
                            ]
                        ),
                        reducer: .empty,
                        environment: {}
                    )
                )
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)

                MediaScreen(
                    store: .init(
                        initialState: .init(
                            feedContents: [
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .videoMock(),
                                .videoMock(),
                                .videoMock(),
                                .podcastMock(),
                                .podcastMock(),
                                .podcastMock()
                            ],
                            searchedFeedContents: [
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock()
                            ],
                            isSearchResultVisible: true,
                            isSearchTextEditing: true
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
}
#endif
