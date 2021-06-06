import SwiftUI
import Styleguide

struct MediaSectionHeader: View {

    let icon: Image
    let title: String

    var body: some View {
        HStack(spacing: 0) {
            Label { Text(title) } icon: { icon }
                .font(.headline)
                .layoutPriority(1)
            Spacer()
            Button(action: {}, label: {
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

struct MediaSectionHeader_Previews: PreviewProvider {
    static var previews: some View {
        let sizeCategories: [ContentSizeCategory] = [
            .large, // Default
            .accessibilityExtraExtraExtraLarge // Biggest: to verify the height
        ]
        ForEach(sizeCategories, id: \.self) { (sizeCategory: ContentSizeCategory) in
            MediaSectionHeader(
                icon: AssetImage.iconBlog.image,
                title: L10n.MediaScreen.Session.Podcast.title
            )
            .previewLayout(.sizeThatFits)
            .environment(\.sizeCategory, sizeCategory)
        }
        .accentColor(AssetColor.primary.color)
    }
}
