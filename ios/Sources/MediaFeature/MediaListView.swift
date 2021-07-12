import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

struct MediaListView: View {

    private let store: Store<MediaListState, MediaListAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, ViewAction>

    init(store: Store<MediaListState, MediaListAction>) {
        self.store = store
        self.viewStore = .init(store.scope(state: ViewState.init(state:), action: MediaListAction.init(action:)))
    }

    struct ViewState: Equatable {
        var searchedFeedContents: [FeedContent]
        var hasBlogs: Bool
        var hasVideos: Bool
        var hasPodcasts: Bool
        var isSearchResultVisible: Bool
        var isSearchTextEditing: Bool
        var moreActiveType: MediaType?

        init(state: MediaListState) {
            searchedFeedContents = state.searchedFeedContents
            hasBlogs = !state.blogs.isEmpty
            hasVideos = !state.videos.isEmpty
            hasPodcasts = !state.podcasts.isEmpty
            isSearchResultVisible = state.isSearchResultVisible
            isSearchTextEditing = state.isSearchTextEditing
            moreActiveType = state.moreActiveType
        }
    }

    enum ViewAction {
        case moreDismissed
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
    }

    var body: some View {
        ZStack {
            ScrollView {
                if viewStore.hasBlogs {
                    MediaSectionView(
                        type: .blog,
                        store: store.scope(
                            state: \.blogs,
                            action: { .init(action: $0, for: .blog) }
                        )
                    )
                    separator
                }
                if viewStore.hasVideos {
                    MediaSectionView(
                        type: .video,
                        store: store.scope(
                            state: \.videos,
                            action: { .init(action: $0, for: .video) }
                        )
                    )
                    separator
                }
                if viewStore.hasPodcasts {
                    MediaSectionView(
                        type: .podcast,
                        store: store.scope(
                            state: \.podcasts,
                            action: { .init(action: $0, for: .podcast) }
                        )
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
        .background(
            NavigationLink(
                destination: IfLetStore(
                    store.scope(
                        state: MediaDetailScreen.ViewState.init(state:),
                        action: MediaListAction.init(action:)
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
    }

    private var separator: some View {
        Separator()
            .padding()
    }
}

private extension MediaListView.ViewState {
    var isMoreActive: Bool {
        moreActiveType != nil
    }
}

private extension MediaListAction {
    init(action: MediaListView.ViewAction) {
        switch action {
        case .moreDismissed:
            self = .moreDismissed
        case .tap(let feedContent):
            self = .tap(id: feedContent.id)
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        }
    }
}

private extension MediaDetailScreen.ViewState {
    init?(state: MediaListState) {
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

private extension MediaListAction {
    init(action: MediaSectionView.ViewAction, for mediaType: MediaType) {
        switch action {
        case .showMore:
            self = .showMore(for: mediaType)
        case .tap(let content):
            self = .tap(id: content.id)
        case .tapFavorite(let isFavorited, let contentId):
            self = .tapFavorite(isFavorited: isFavorited, id: contentId)
        }
    }

    init(action: MediaDetailScreen.ViewAction) {
        switch action {
        case .tap(let content):
            self = .tap(id: content.id)
        case .tapFavorite(let isFavorited, let contentId):
            self = .tapFavorite(isFavorited: isFavorited, id: contentId)
        }
    }
}

#if DEBUG
public struct MediaListView_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            MediaListView(
                store: .init(
                    initialState: .init(
                        feedContents: [],
                        blogs: [.blogMock(), .blogMock()],
                        videos: [.videoMock(), .videoMock()],
                        podcasts: [.podcastMock(), .podcastMock()]
                    ),
                    reducer: .empty,
                    environment: {}
                )
            )
            .background(AssetColor.Background.primary.color.ignoresSafeArea())
            .environment(\.colorScheme, colorScheme)
        }
        .accentColor(AssetColor.primary.color)
    }
}
#endif
