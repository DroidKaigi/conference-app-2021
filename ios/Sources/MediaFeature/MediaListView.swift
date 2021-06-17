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
        var hasBlogs: Bool
        var hasVideos: Bool
        var hasPodcasts: Bool
        var isSearchResultVisible: Bool
        var isMoreActive: Bool

        init(state: MediaListState) {
            hasBlogs = !state.list.blogs.isEmpty
            hasVideos = !state.list.videos.isEmpty
            hasPodcasts = !state.list.podcasts.isEmpty
            if case let .searchText(text) = state.next,
               !text.isEmpty {
                isSearchResultVisible = true
            } else {
                isSearchResultVisible = false
            }
            if case .more = state.next {
                isMoreActive = true
            } else {
                isMoreActive = false
            }
        }
    }

    enum ViewAction {
        case moreDismissed
    }

    var body: some View {
        ZStack {
            ScrollView {
                if viewStore.hasBlogs {
                    MediaSection(
                        icon: AssetImage.iconBlog.image.renderingMode(.template),
                        title: L10n.MediaScreen.Section.Blog.title,
                        store: store.scope(
                            state: {  $0.list.blogs.map(\.feedItem) },
                            action: { .init(action: $0, for: .blog) }
                        )
                    )
                    divider
                }
                if viewStore.hasVideos {
                    MediaSection(
                        icon: AssetImage.iconVideo.image.renderingMode(.template),
                        title: L10n.MediaScreen.Section.Video.title,
                        store: store.scope(
                            state: {  $0.list.videos.map(\.feedItem) },
                            action: { .init(action: $0, for: .video) }
                        )
                    )
                    divider
                }
                if viewStore.hasPodcasts {
                    MediaSection(
                        icon: AssetImage.iconPodcast.image.renderingMode(.template),
                        title: L10n.MediaScreen.Section.Podcast.title,
                        store: store.scope(
                            state: {  $0.list.podcasts.map(\.feedItem) },
                            action: { .init(action: $0, for: .podcast) }
                        )
                    )
                }
            }
            .zIndex(0)

            if viewStore.isSearchResultVisible {
                SearchResultView()
                    .zIndex(1)
            }
        }
        .background(
            NavigationLink(
                destination: IfLetStore(
                    self.store.actionless.scope(state: MediaDetail.ViewState.init(state:)),
                    then: MediaDetail.init(store:)
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

    private var divider: some View {
        Divider()
            .padding()
    }
}

private extension MediaListAction {
    init(action: MediaListView.ViewAction) {
        switch action {
        case .moreDismissed:
            self = .moreDismissed
        }
    }
}

private extension MediaDetail.ViewState {
    init?(state: MediaListState) {
        guard case let .more(mediaType) = state.next else {
            return nil
        }
        switch mediaType {
        case .blog:
            title = L10n.MediaScreen.Section.Blog.title
            feedItems = state.list.blogs.map(FeedItemType.blog)
        case .video:
            title = L10n.MediaScreen.Section.Video.title
            feedItems = state.list.videos.map(FeedItemType.video)
        case .podcast:
            title = L10n.MediaScreen.Section.Podcast.title
            feedItems = state.list.podcasts.map(FeedItemType.podcast)
        }
    }
}

private extension MediaListAction {
    init(action: MediaSection.ViewAction, for mediaType: MediaType) {
        switch action {
        case .showMore:
            self = .showMore(for: mediaType)
        }
    }
}

public struct MediaListView_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            MediaListView(
                store: .init(
                    initialState: .init(list: .mock, next: nil),
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
