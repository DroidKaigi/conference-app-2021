import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct MediaDetailState: Equatable {
    public var title: String
    public var contents: [FeedContent]

    public init(
        title: String,
        contents: [FeedContent] = []
    ) {
        self.title = title
        self.contents = contents
    }
}

enum MediaDetailAction {
    case tap(FeedContent)
    case favorite(String)
}

public struct MediaDetailScreen: View {
    let store: Store<MediaDetailState, MediaDetailAction>

    public var body: some View {
        WithViewStore(store) { viewStore in
            ScrollView {
                FeedContentListView(
                    feedContents: viewStore.contents,
                    tapContent: { content in
                        viewStore.send(.tap(content))
                    },
                    tapFavorite: { contentId in
                        viewStore.send(.favorite(contentId))
                    }
                )
                .padding(.horizontal, 8)
            }
            .navigationBarTitle(viewStore.title, displayMode: .inline)
        }
        .introspectViewController { viewController in
            viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
        }
    }
}

public struct MediaDetailScreen_Previews: PreviewProvider {
    public static var previews: some View {
        MediaDetailScreen(
            store: .init(
                initialState: .init(title: "BLOG", contents: []),
                reducer: .empty,
                environment: {}
            )
        )
    }
}
