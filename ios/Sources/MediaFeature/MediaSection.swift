import Component
import ComposableArchitecture
import Model
import SwiftUI
import Styleguide

public struct MediaSection: View {

    var icon: SwiftUI.Image
    var title: String
    let store: Store<[FeedContent], ViewAction>

    enum ViewAction {
        case showMore
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
    }

    public var body: some View {
        VStack(spacing: 0) {
            WithViewStore(store) { viewStore in
                MediaSectionHeader(
                    icon: icon,
                    title: title,
                    moreAction: { viewStore.send(.showMore) }
                )
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack(spacing: 0) {
                        ForEach(viewStore.state) { content in
                            let item = content.item
                            MediumCard(
                                title: item.title.get(by: .ja),
                                imageURL: URL(string: item.image.standardURLString),
                                media: item.media,
                                date: item.publishedAt,
                                isFavorited: content.isFavorited,
                                tapAction: {
                                    viewStore.send(.tap(content))
                                },
                                tapFavoriteAction: {
                                    viewStore.send(.tapFavorite(isFavorited: content.isFavorited, id: content.id))
                                }
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

#if DEBUG
public struct MediaSection_Previews: PreviewProvider {
    public static var previews: some View {
        let sizeCategories: [ContentSizeCategory] = [
            .large, // Default
            .extraExtraExtraLarge
        ]
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            ForEach(sizeCategories, id: \.self) { sizeCategory in
                MediaSection(
                    icon: AssetImage.iconBlog.image.renderingMode(.template),
                    title: L10n.MediaScreen.Section.Blog.title,
                    store: .init(
                        initialState: [
                            .blogMock(),
                            .blogMock(),
                            .blogMock(),
                            .blogMock(),
                            .blogMock(),
                            .blogMock(),
                        ],
                        reducer: .empty,
                        environment: {}
                    )
                )
                .background(AssetColor.Background.primary.color)
                .environment(\.sizeCategory, sizeCategory)
                .environment(\.colorScheme, colorScheme)
            }
        }
        .frame(width: 375, height: 301)
        .previewLayout(.sizeThatFits)
        .accentColor(AssetColor.primary.color)
    }
}
#endif
