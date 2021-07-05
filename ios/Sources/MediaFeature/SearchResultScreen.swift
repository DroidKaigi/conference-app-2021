import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct SearchResultScreen: View {
    let store: Store<ViewState, ViewAction>

    struct ViewState: Equatable {
        var contents: [FeedContent]

        init(contents: [FeedContent] = []) {
            self.contents = contents
        }
    }

    enum ViewAction {
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            Group {
                if viewStore.contents.isEmpty {
                    empty
                } else {
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
                        .padding(.horizontal, 8)
                    }
                }
            }
            .background(AssetColor.Background.primary.color)
        }
    }
}

extension SearchResultScreen {
    private var empty: some View {
        VStack {
            Text(L10n.SearchResultScreen.Empty.title)
                .font(.subheadline)
                .foregroundColor(AssetColor.Base.primary.color)
                .padding(.top, 40)

            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

public struct SearchResultScreen_Previews: PreviewProvider {
    public static var previews: some View {
        SearchResultScreen(
            store: .init(
                initialState: .init(
                    contents: [
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                    ]
                ),
                reducer: .empty,
                environment: {}
            )
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .light)

        SearchResultScreen(
            store: .init(
                initialState: .init(
                    contents: [
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
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
