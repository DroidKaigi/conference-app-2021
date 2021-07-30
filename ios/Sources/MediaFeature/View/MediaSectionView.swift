import Component
import ComposableArchitecture
import Model
import SwiftUI
import Styleguide

public struct MediaSectionView: View {
    let type: MediaType
    let store: Store<[FeedContent], ViewAction>

    enum ViewAction {
        case showMore
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
    }

    public var body: some View {
        VStack(spacing: .zero) {
            WithViewStore(store) { viewStore in
                MediaSectionHeader(
                    type: type,
                    moreAction: { viewStore.send(.showMore) }
                )
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack(spacing: .zero) {
                        ForEach(viewStore.state) { content in
                            MediumCard(
                                content: content,
                                tapAction: {
                                    viewStore.send(.tap(content))
                                },
                                tapFavoriteAction: {
                                    viewStore.send(.tapFavorite(isFavorited: content.isFavorited, id: content.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private extension MediumCard {
    init(
        content: FeedContent,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void
    ) {
        self.init(
            title: content.item.title.jaTitle,
            imageURL: URL(string: content.item.image.largeURLString),
            media: content.item.media,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoriteAction
        )
    }
}

#if DEBUG
public struct MediaSectionView_Previews: PreviewProvider {
    public static var previews: some View {
        let sizeCategories: [ContentSizeCategory] = [
            .large, // Default
            .extraExtraExtraLarge
        ]
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            ForEach(sizeCategories, id: \.self) { sizeCategory in
                MediaSectionView(
                    type: .blog,
                    store: .init(initialState: [.blogMock(), .blogMock(), .blogMock(), .blogMock(), .blogMock(), .blogMock()], reducer: .empty, environment: {})
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
