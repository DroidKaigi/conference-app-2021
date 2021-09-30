import Component
import ComposableArchitecture
import Model
import SwiftUI
import Styleguide

public struct MediaSectionView: View {
    let type: MediaType
    let store: Store<ViewState, ViewAction>

    struct ViewState: Equatable {
        let contents: [FeedContent]
        let language: Lang
    }

    enum ViewAction {
        case showMore
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
        case tapPlay(FeedContent)
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
                        ForEach(viewStore.contents) { content in
                            MediumCard(
                                content: content,
                                language: viewStore.language,
                                tapAction: {
                                    viewStore.send(.tap(content))
                                },
                                tapFavoriteAction: {
                                    viewStore.send(.tapFavorite(isFavorited: content.isFavorited, id: content.id))
                                },
                                tapPlayAction: {
                                    viewStore.send(.tapPlay(content))
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
        language: Lang,
        tapAction: @escaping () -> Void,
        tapFavoriteAction: @escaping () -> Void,
        tapPlayAction: @escaping () -> Void
    ) {
        self.init(
            title: content.item.title.get(by: language),
            imageURL: URL(string: content.item.image.largeURLString),
            media: content.item.media,
            date: content.item.publishedAt,
            isFavorited: content.isFavorited,
            tapAction: tapAction,
            tapFavoriteAction: tapFavoriteAction,
            tapPlayAction: tapPlayAction
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
                    store: .init(initialState: MediaSectionView.ViewState(contents: [.blogMock(), .blogMock(), .blogMock(), .blogMock(), .blogMock(), .blogMock()], language: .en),
                                 reducer: .empty,
                                 environment: {})
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
