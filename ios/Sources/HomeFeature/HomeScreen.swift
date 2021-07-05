import Component
import ComposableArchitecture
import Model
import Repository
import Styleguide
import SwiftUI

public struct HomeScreen: View {
    private let store: Store<HomeState, HomeAction>
    @ObservedObject private var viewStore: ViewStore<ViewState, ViewAction>

    public init(store: Store<HomeState, HomeAction>) {
        self.store = store
        self.viewStore = ViewStore<ViewState, ViewAction>(store.scope(state: ViewState.init(state:), action: HomeAction.init(action:)))
    }

    internal struct ViewState: Equatable {
        init(state: HomeState) {}
    }

    internal enum ViewAction {
        case progressViewAppeared
    }

    public var body: some View {
        NavigationView {
            InlineTitleNavigationBarScrollView {
                ZStack(alignment: .top) {
                    AssetColor.primary.color
                        .frame(width: nil, height: 200)
                        .clipShape(CutCornerRectangle(targetCorners: [.topLeft], radius: 42))
                    SwitchStore(store) {
                        CaseLet(
                            state: /HomeState.needToInitialize,
                            action: HomeAction.init(action:)) { _ in
                            ProgressView()
                                .onAppear { viewStore.send(.progressViewAppeared) }
                        }
                        CaseLet(
                            state: /HomeState.initialized,
                            action: HomeAction.init(action:),
                            then: HomeListView.init(store:))
                    }
                }
            }
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    AssetImage.logoTitle.image
                }
            }
            .navigationBarItems(
                trailing: AssetImage.iconSetting.image
                    .renderingMode(.template)
                    .foregroundColor(AssetColor.Base.primary.color)
            )
            .introspectViewController { viewController in
                viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
            }
        }
    }
}

private extension HomeAction {
    init(action: HomeScreen.ViewAction) {
        switch action {
        case .progressViewAppeared:
            self = .refresh
        }
    }

    init(action: HomeListAction) {
        self = .homeList(action)
    }
}

#if DEBUG
public struct HomeScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            HomeScreen(
                store: .init(
                    initialState: .needToInitialize,
                    reducer: .empty,
                    environment: HomeEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            HomeScreen(
                store: .init(
                    initialState: .needToInitialize,
                    reducer: .empty,
                    environment: HomeEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
            HomeScreen(
                store: .init(
                    initialState: .initialized(.init(feedContents: [.mock(), .mock()])),
                    reducer: .empty,
                    environment: HomeEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            HomeScreen(
                store: .init(
                    initialState: .initialized(.init(feedContents: [.mock(), .mock()])),
                    reducer: .empty,
                    environment: HomeEnvironment(feedRepository: FeedRepositoryMock())
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}

extension FeedContent {
    static func mock(
        id: String = UUID().uuidString,
        imageURLString: String = "",
        link: String = "",
        media: Media = .medium,
        publishedAt: Date = Date(timeIntervalSince1970: 0),
        summary: String = "",
        title: String = "DroidKaigi 2021とその他活動予定についてのお知らせ"
    ) -> FeedContent {
        .init(
            item: .init(
                Video(
                    id: id,
                    image: .init(largeURLString: imageURLString, smallURLString: "", standardURLString: ""),
                    link: link,
                    media: media,
                    publishedAt: publishedAt,
                    summary: .init(enTitle: summary, jaTitle: summary),
                    title: .init(enTitle: title, jaTitle: title)
                )
            ),
            isFavorited: false
        )
    }
}
#endif
