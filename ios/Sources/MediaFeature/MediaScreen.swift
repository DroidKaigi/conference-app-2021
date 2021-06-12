import Model
import SwiftUI
import Styleguide

public struct MediaScreen: View {

    var blogs: [FeedItem]
    var videos: [FeedItem]
    var podcasts: [FeedItem]

    public init(blogs: [FeedItem], videos: [FeedItem], podcasts: [FeedItem]) {
        self.blogs = blogs
        self.videos = videos
        self.podcasts = podcasts
    }

    public var body: some View {
        NavigationView {
            list
                .navigationTitle(L10n.MediaScreen.title)
                .navigationBarItems(
                    trailing: AssetImage.iconSetting.image
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary.color)
                )
        }
    }

    private var list: some View {
        ScrollView {
            VStack(spacing: 0) {
                if !blogs.isEmpty {
                    MediaSection(
                        icon: AssetImage.iconBlog.image.renderingMode(.template),
                        title: L10n.MediaScreen.Session.Blog.title,
                        items: blogs
                    )
                    divider
                }
                if !videos.isEmpty {
                    MediaSection(
                        icon: AssetImage.iconVideo.image.renderingMode(.template),
                        title: L10n.MediaScreen.Session.Video.title,
                        items: videos
                    )
                    divider
                }
                if !podcasts.isEmpty {
                    MediaSection(
                        icon: AssetImage.iconPodcast.image.renderingMode(.template),
                        title: L10n.MediaScreen.Session.Podcast.title,
                        items: podcasts
                    )
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

public struct MediaScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            MediaScreen(
                blogs: [
                    .init(
                        id: "0",
                        image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                        link: "",
                        media: .medium,
                        publishedAt: .init(),
                        summary: .init(enTitle: "", jaTitle: ""),
                        title: .init(enTitle: "", jaTitle: "DroidKaigi 2020でのCodelabsについて")
                    ),
                    .init(
                        id: "1",
                        image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                        link: "",
                        media: .medium,
                        publishedAt: .init(),
                        summary: .init(enTitle: "", jaTitle: ""),
                        title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Codelabs")
                    ),
                ],
                videos: [
                    .init(
                        id: "0",
                        image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                        link: "",
                        media: .youtube,
                        publishedAt: .init(),
                        summary: .init(enTitle: "", jaTitle: ""),
                        title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Lite - KotlinのDelegated Propertiesを活用してAndroidアプリ開発をもっと便利にする / chibatching [JA]")
                    ),
                    .init(
                        id: "1",
                        image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                        link: "",
                        media: .youtube,
                        publishedAt: .init(),
                        summary: .init(enTitle: "", jaTitle: ""),
                        title: .init(enTitle: "", jaTitle: "DroidKaigi 2020 Lite - Day 2 Night Session")
                    ),
                ],
                podcasts: [
                    .init(
                        id: "0",
                        image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                        link: "",
                        media: .droidkaigifm,
                        publishedAt: .init(),
                        summary: .init(enTitle: "", jaTitle: ""),
                        title: .init(enTitle: "", jaTitle: "2. Android 11 Talks")
                    ),
                    .init(
                        id: "1",
                        image: .init(largeURLString: "", smallURLString: "", standardURLString: ""),
                        link: "",
                        media: .droidkaigifm,
                        publishedAt: .init(),
                        summary: .init(enTitle: "", jaTitle: ""),
                        title: .init(enTitle: "", jaTitle: "5. Notificiationよもやま話")
                    ),
                ]
            )
            .environment(\.colorScheme, colorScheme)
        }
        .accentColor(AssetColor.primary.color)
    }
}
