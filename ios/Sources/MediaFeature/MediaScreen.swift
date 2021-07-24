import Component
import ComposableArchitecture
import Introspect
import Model
import SwiftUI
import Styleguide

public struct MediaScreen: View {
    private let store: Store<MediaState, MediaAction>
    private let subStore: Store<MediaState, MediaScreen.ViewAction>
    @ObservedObject private var subViewStore: ViewStore<MediaState, MediaScreen.ViewAction>
    @SearchController private var searchController: UISearchController

    public init(store: Store<MediaState, MediaAction>, subStore: Store<MediaState, MediaScreen.ViewAction>) {
        self.store = store
        self.subStore = subStore
        let subViewStore = ViewStore(subStore)
        self.subViewStore = subViewStore
        self._searchController = .init(
            searchBarPlaceHolder: L10n.MediaScreen.SearchBar.placeholder,
            searchTextDidChangeTo: { text in
                subViewStore.send(.searchTextDidChange(to: text))
            },
            isEditingDidChangeTo: { isEditing in
                subViewStore.send(.isEditingDidChange(to: isEditing))
            }
        )
    }

    public enum ViewAction {
        case searchTextDidChange(to: String?)
        case isEditingDidChange(to: Bool)
        case showMore(for: MediaType)
        case moreDismissed
    }

    public var body: some View {
        NavigationView {
            WithViewStore(store) { viewStore in
                ZStack {
                    ScrollView {
                        if viewStore.hasBlogs {
                            MediaSectionView(
                                type: .blog,
                                feedContent: viewStore.blogs,
                                moreAction: {
                                    ViewStore(subStore).send(.showMore(for: .blog))
                                },
                                tapAction: { feedContent in
                                    viewStore.send(.tap(feedContent))
                                },
                                tapFavoriteAction: { isFavorited, id in
                                    viewStore.send(.tapFavorite(isFavorited: isFavorited, id: id))
                                }
                            )
                            separator
                        }
                        if viewStore.hasVideos {
                            MediaSectionView(
                                type: .video,
                                feedContent: viewStore.videos,
                                moreAction: {
                                    ViewStore(subStore).send(.showMore(for: .video))
                                },
                                tapAction: { feedContent in
                                    viewStore.send(.tap(feedContent))
                                },
                                tapFavoriteAction: { isFavorited, id in
                                    viewStore.send(.tapFavorite(isFavorited: isFavorited, id: id))
                                }
                            )
                            separator
                        }
                        if viewStore.hasPodcasts {
                            MediaSectionView(
                                type: .podcast,
                                feedContent: viewStore.podcasts,
                                moreAction: {
                                    ViewStore(subStore).send(.showMore(for: .podcast))
                                },
                                tapAction: { feedContent in
                                    viewStore.send(.tap(feedContent))
                                },
                                tapFavoriteAction: { isFavorited, id in
                                    viewStore.send(.tapFavorite(isFavorited: isFavorited, id: id))
                                }
                            )
                        }
                    }
                    .separatorStyle(ThickSeparatorStyle())
                    .zIndex(0)

                    Color.black.opacity(0.4)
                        .opacity(viewStore.isSearchTextEditing ? 1 : .zero)
                        .animation(.easeInOut)
                        .zIndex(1)

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
                    .zIndex(2)
                }
                .animation(.easeInOut)
            }
            .background(
                NavigationLink(
                    destination: IfLetStore(
                        store.scope(
                            state: MediaDetailScreen.ViewState.init(state:),
                            action: MediaAction.init(action:)
                        ),
                        then: MediaDetailScreen.init(store:)
                    ),
                    isActive: ViewStore(subStore).binding(
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
                    ViewStore(store).send(.showSetting)
                }, label: {
                    AssetImage.iconSetting.image
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                })
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
                    ),
                    subStore: .init(
                        initialState: .init(
                            feedContents: [
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock()
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
                    ),
                    subStore: .init(
                        initialState: .init(
                            feedContents: [
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock(),
                                .blogMock()
                            ]
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
