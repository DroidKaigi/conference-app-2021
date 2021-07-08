import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct SearchResultScreen: View {
    let feedContents: [FeedContent]
    let tap: (FeedContent) -> Void
    let tapFavorite: (_ isFavorited: Bool, _ id: String) -> Void

    public var body: some View {
        Group {
            if feedContents.isEmpty {
                empty
            } else {
                ScrollView {
                    FeedContentListView(
                        feedContents: feedContents,
                        tapContent: tap,
                        tapFavorite: tapFavorite
                    )
                    .padding(.horizontal, 8)
                    .animation(.easeInOut)
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

#if DEBUG
public struct SearchResultScreen_Previews: PreviewProvider {
    public static var previews: some View {
        // TODO: for each
        SearchResultScreen(
            feedContents: [],
            tap: { _ in },
            tapFavorite: { _, _ in }
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .light)

        SearchResultScreen(
            feedContents: [
                .blogMock(),
                .blogMock(),
                .blogMock(),
                .blogMock(),
                .blogMock(),
                .blogMock()
            ],
            tap: { _ in },
            tapFavorite: { _, _ in }
        )
        .previewDevice(.init(rawValue: "iPhone 12"))
        .environment(\.colorScheme, .dark)
    }
}
#endif
