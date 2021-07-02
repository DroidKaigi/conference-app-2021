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
        var hasBlogs: Bool
        var hasVideos: Bool
        var hasPodcasts: Bool
        var isSearchResultVisible: Bool
        var isMoreActive: Bool

        init(state: MediaListState) {
            hasBlogs = !state.blogs.isEmpty
            hasVideos = !state.videos.isEmpty
            hasPodcasts = !state.podcasts.isEmpty
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
                            state: \.blogs,
                            action: { .init(action: $0, for: .blog) }
                        )
                    )
                    separator
                }
                if viewStore.hasVideos {
                    MediaSection(
                        icon: AssetImage.iconVideo.image.renderingMode(.template),
                        title: L10n.MediaScreen.Section.Video.title,
                        store: store.scope(
                            state: \.videos,
                            action: { .init(action: $0, for: .video) }
                        )
                    )
                    separator
                }
                if viewStore.hasPodcasts {
                    MediaSection(
                        icon: AssetImage.iconPodcast.image.renderingMode(.template),
                        title: L10n.MediaScreen.Section.Podcast.title,
                        store: store.scope(
                            state: \.podcasts,
                            action: { .init(action: $0, for: .podcast) }
                        )
                    )
                }
            }
            .separatorStyle(ThickSeparatorStyle())
            .zIndex(0)

            if viewStore.isSearchResultVisible {
                SearchResultView()
                    .zIndex(1)
            }
        }
        .background(
            NavigationLink(
                destination: IfLetStore(
                    store.scope(
                        state: MediaDetailState.init(state:),
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

private extension MediaListAction {
    init(action: MediaListView.ViewAction) {
        switch action {
        case .moreDismissed:
            self = .moreDismissed
        }
    }
}

private extension MediaDetailState {
    init?(state: MediaListState) {
        guard case let .more(mediaType) = state.next else {
            return nil
        }
        switch mediaType {
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
    init(action: MediaSection.ViewAction, for mediaType: MediaType) {
        switch action {
        case .showMore:
            self = .showMore(for: mediaType)
        case .tap(let content):
            self = .tap(content)
        case .favorite(let contentId):
            self = .favorite(contentId)
        }
    }

    init(action: MediaDetailAction) {
        switch action {
        case .tap(let content):
            self = .tap(content)
        case .favorite(let contentId):
            self = .favorite(contentId)
        }
    }
}

public struct MediaListView_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            MediaListView(
                store: .init(
                    initialState: .mock,
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
