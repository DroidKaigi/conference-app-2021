import Component
import ComposableArchitecture
import Model
import SwiftUI
import Styleguide

struct MediaSection: View {

    var icon: SwiftUI.Image
    var title: String
    let store: Store<[FeedContent], ViewAction>

    enum ViewAction {
        case showMore
    }

    var body: some View {
        VStack(spacing: 0) {
            WithViewStore(store) { viewStore in
                MediaSectionHeader(
                    icon: icon,
                    title: title,
                    moreAction: { viewStore.send(.showMore) }
                )
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack(spacing: 0) {
                        ForEach(viewStore.state) { item in
                            let feedItem = item.feedItem
                            MediumCard(
                                title: feedItem.title.get(by: .ja),
                                imageURL: URL(string: feedItem.image.standardURLString),
                                tag: feedItem.media,
                                date: feedItem.publishedAt,
                                isFavorited: item.isFavorited,
                                tapAction: {},
                                tapFavoriteAction: {}
                            )
                            .aspectRatio(257.0 / 258, contentMode: .fit)
                        }
                    }
                    .padding(.horizontal, 8)
                }
            }
        }
        .aspectRatio(375.0 / 301, contentMode: .fit)
    }
}

struct MediaSection_Previews: PreviewProvider {

    static var previews: some View {
        let sizeCategories: [ContentSizeCategory] = [
            .large, // Default
            .extraExtraExtraLarge
        ]
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            ForEach(sizeCategories, id: \.self) { sizeCategory in
                MediaSection(
                    icon: AssetImage.iconBlog.image.renderingMode(.template),
                    title: L10n.MediaScreen.Section.Blog.title,
                    store: .init(initialState: .mockBlogs, reducer: .empty, environment: {})
                )
                .background(AssetColor.Background.primary.color)
                .environment(\.sizeCategory, sizeCategory)
                .environment(\.colorScheme, colorScheme)
            }
        }
        .previewLayout(.sizeThatFits)
        .accentColor(AssetColor.primary.color)
    }
}
