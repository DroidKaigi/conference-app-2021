import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct SearchResultScreen: View {

    private let store: Store<[FeedContent], ViewAction>

    init(store: Store<[FeedContent], ViewAction>) {
        self.store = store
    }

    enum ViewAction: Equatable {
        case tap(FeedContent)
        case tapFavorite(isFavorited: Bool, id: String)
        case tapPlay(FeedContent)
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            ZStack {
                AssetColor.Background.primary.color
                if viewStore.state.isEmpty {
                    empty
                } else {
                    ScrollView {
                        FeedContentListView(
                            feedContents: viewStore.state,
                            tapContent: { viewStore.send(.tap($0)) },
                            tapFavorite: { viewStore.send(.tapFavorite(isFavorited: $0, id: $1)) },
                            tapPlay: { viewStore.send(.tapPlay($0)) }
                        )
                    }
                }
            }
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

private extension MediaAction {
    init(action: MediaListView.ViewAction) {
        switch action {
        case .moreDismissed:
            self = .moreDismissed
        case .tap(let feedContent):
            self = .tap(feedContent)
        case .tapFavorite(let isFavorited, let id):
            self = .tapFavorite(isFavorited: isFavorited, id: id)
        }
    }
}

#if DEBUG
public struct SearchResultScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            SearchResultScreen(
                store: .init(
                    initialState: [],
                    reducer: .empty,
                    environment: ()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            SearchResultScreen(
                store: .init(
                    initialState: [
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock(),
                        .blogMock()
                    ],
                    reducer: .empty,
                    environment: ()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)
        }
    }
}
#endif
