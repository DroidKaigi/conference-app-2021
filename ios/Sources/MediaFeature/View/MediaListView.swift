import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

struct MediaListView: View {

    private let store: Store<MediaState, MediaAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, ViewAction>

    init(store: Store<MediaState, MediaAction>) {
        self.store = store
        self.viewStore = .init(store.scope(state: ViewState.init(state:), action: MediaAction.init(action:)))
    }

    struct ViewState: Equatable {
        var hasBlogs: Bool
        var hasVideos: Bool
        var hasPodcasts: Bool

        init(state: MediaState) {
            hasBlogs = state.feedContents.contains { $0.item.wrappedValue is Blog }
            hasVideos = state.feedContents.contains { $0.item.wrappedValue is Video }
            hasPodcasts = state.feedContents.contains { $0.item.wrappedValue is Podcast }
        }
    }

    enum ViewAction {
        case moreDismissed
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
    }

    var body: some View {
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
    }

    private var separator: some View {
        Separator()
            .padding()
    }
}

private extension MediaAction {
    init(action: MediaListView.ViewAction) {
        switch action {
        case .moreDismissed:
            self = .moreDismissed
        case .tap(let feedContent):
            self = .tap(feedContent)
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        }
    }
}

private extension MediaAction {
    init(action: MediaSectionView.ViewAction, for mediaType: MediaType) {
        switch action {
        case .showMore:
            self = .showMore(for: mediaType)
        case .tap(let content):
            self = .tap(content)
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
                        feedContents: [
                            .blogMock(),
                            .blogMock(),
                            .videoMock(),
                            .videoMock(),
                            .podcastMock(),
                            .podcastMock()
                        ]
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
