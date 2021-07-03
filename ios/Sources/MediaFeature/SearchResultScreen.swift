import Component
import Model
import Styleguide
import SwiftUI

public struct SearchResultScreen: View {
    private let contents: [FeedContent]

    public init(
        contents: [FeedContent]
    ) {
        self.contents = contents
    }

    public var body: some View {
        Group {
            if contents.isEmpty {
                empty
            } else {
                ScrollView {
                    FeedContentListView(
                        feedContents: contents,
                        tapContent: { _ in
        //                    viewStore.send(.tap(content))
                        },
                        tapFavorite: { _ in
        //                    viewStore.send(.favorite(contentId))
                        }
                    )
                    .padding(.horizontal, 8)
                }
            }
        }
        .background(AssetColor.Background.primary.color)
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
        SearchResultScreen(contents: [])
    }
}
