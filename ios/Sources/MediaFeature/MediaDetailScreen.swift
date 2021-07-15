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

        init(
            title: String,
            contents: [FeedContent] = []
        ) {
            self.title = title
            self.contents = contents
        }
    }

    enum ViewAction {
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            ScrollView {
                FeedContentListView(
                    feedContents: viewStore.contents,
                    tapContent: { content in
                        viewStore.send(.tap(content))
                    },
                    tapFavorite: { isFavorited, contentId in
                        viewStore.send(.tapFavorite(isFavorited: isFavorited, id: contentId))
                    }
                )
            }
            .navigationBarTitle(viewStore.title, displayMode: .inline)
        }
        .introspectViewController { viewController in
            viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
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
                    ]
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
                    ]
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
