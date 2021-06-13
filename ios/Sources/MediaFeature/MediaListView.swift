import ComposableArchitecture
import Styleguide
import SwiftUI

struct MediaListView: View {

    private let store: Store<MediaList, MediaAction>
    @ObservedObject var viewStore: ViewStore<ViewState, Never>

    init(store: Store<MediaList, MediaAction>) {
        self.store = store
        self.viewStore = .init(store.scope(state: ViewState.init(state:)).actionless)
    }

    struct ViewState: Equatable {
        var hasBlogs: Bool
        var hasVideos: Bool
        var hasPodcasts: Bool

        init(state: MediaList) {
            hasBlogs = !state.blogs.isEmpty
            hasVideos = !state.videos.isEmpty
            hasPodcasts = !state.podcasts.isEmpty
        }
    }

    var body: some View {
        ScrollView {
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

    private var divider: some View {
        Divider()
            .padding()
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
            .environment(\.colorScheme, colorScheme)
        }
        .accentColor(AssetColor.primary.color)
    }
}
