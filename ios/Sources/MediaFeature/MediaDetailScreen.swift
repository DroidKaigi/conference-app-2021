import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct MediaDetailScreen: View {
    let store: Store<ViewState, ViewAction>

    struct ViewState: Equatable {
        var title: String
        var contents: [FeedContent]
        var language: Lang
    }

    enum ViewAction {
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
        case tapPlay(FeedContent)
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            ScrollView {
                FeedContentListView(
                    feedContents: viewStore.contents,
                    language: viewStore.language,
                    tapContent: { content in
                        viewStore.send(.tap(content))
                    },
                    tapFavorite: { isFavorited, contentId in
                        viewStore.send(.tapFavorite(isFavorited: isFavorited, id: contentId))
                    },
                    tapPlay: { content in
                        viewStore.send(.tapPlay(content))
                    }
                )
            }
            .background(AssetColor.Background.primary.color.ignoresSafeArea())
            .navigationBarTitle(viewStore.title, displayMode: .inline)
        }
    }
}

#if DEBUG
public struct MediaDetailScreen_Previews: PreviewProvider {
    public static var previews: some View {
        MediaDetailScreen(
            store: .init(
                initialState: .init(
                    title: "BLOG",
                    contents: [
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock()
                    ],
                    language: .ja
                ),
                reducer: .empty,
                environment: {}
            )
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .light)

        MediaDetailScreen(
            store: .init(
                initialState: .init(
                    title: "BLOG",
                    contents: [
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock()
                    ],
                    language: .ja
                ),
                reducer: .empty,
                environment: {}
            )
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .dark)
    }
}
#endif
