import ComposableArchitecture
import Styleguide
import SwiftUI

struct MediaListView: View {

    private let store: Store<MediaListState, MediaListAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, Never>

    init(store: Store<MediaListState, MediaListAction>) {
        self.store = store
        self.viewStore = .init(store.scope(state: ViewState.init(state:)).actionless)
    }

    struct ViewState: Equatable {
        var hasBlogs: Bool
        var hasVideos: Bool
        var hasPodcasts: Bool
        var isSearchResultVisible: Bool

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
        }
    }

    var body: some View {
        ZStack {
            ScrollView {
                VStack(spacing: 0) {
                    if viewStore.hasBlogs {
                        MediaSection(
                            icon: AssetImage.iconBlog.image.renderingMode(.template),
                            title: L10n.MediaScreen.Session.Blog.title,
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
                            title: L10n.MediaScreen.Session.Video.title,
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
                            title: L10n.MediaScreen.Session.Podcast.title,
                            store: store.scope(
                                state: {  $0.list.podcasts.map(\.feedItem) },
                                action: { .init(action: $0, for: .podcast) }
                            )
                        )
                    }
                }
            }
            .zIndex(0)

            if viewStore.isSearchResultVisible {
                SearchResultView()
                    .zIndex(1)
            }
        }
    }

    private var divider: some View {
        Divider()
            .padding()
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
