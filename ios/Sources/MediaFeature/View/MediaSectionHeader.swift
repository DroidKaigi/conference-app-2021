import SwiftUI
import Styleguide

public struct MediaSectionHeader: View {
    let type: MediaType
    let moreAction: () -> Void

    public var body: some View {
        HStack(spacing: 0) {
            Label { Text(type.title) } icon: { type.icon }
                .font(.headline)
                .layoutPriority(1)
            Spacer()
            Button(action: moreAction, label: {
                HStack {
                    Text("More")
                        .lineLimit(1)
                        .minimumScaleFactor(0.1)
                    AssetImage.iconChevron.image.renderingMode(.template)
                }
            })
        }
        .padding(.horizontal)
        .frame(height: 43, alignment: .center)
    }
}

private extension MediaType {
    var icon: SwiftUI.Image {
        switch self {
        case .blog:
            return AssetImage.iconBlog.image.renderingMode(.template)
        case .video:
            return AssetImage.iconVideo.image.renderingMode(.template)
        case .podcast:
            return AssetImage.iconPodcast.image.renderingMode(.template)
        }
    }

    var title: String {
        switch self {
        case .blog:
            return L10n.MediaScreen.Section.Blog.title
        case .video:
            return L10n.MediaScreen.Section.Video.title
        case .podcast:
            return L10n.MediaScreen.Section.Podcast.title
        }
    }
}

public struct MediaSectionHeader_Previews: PreviewProvider {
    public static var previews: some View {
        let sizeCategories: [ContentSizeCategory] = [
            .large, // Default
            .accessibilityExtraExtraExtraLarge // Biggest: to verify the height
        ]
        ForEach(sizeCategories, id: \.self) { (sizeCategory: ContentSizeCategory) in
            MediaSectionHeader(
                type: .blog,
                moreAction: {}
            )
            .previewLayout(.sizeThatFits)
            .environment(\.sizeCategory, sizeCategory)
        }
        .frame(width: 375, height: 43)
        .accentColor(AssetColor.primary.color)
    }
}
