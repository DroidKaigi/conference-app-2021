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
        case showSetting
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
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .principal) {
                        AssetImage.logoTitle.image
                    }
                }
                .navigationBarItems(
                    trailing: Button(action: {
                        viewStore.send(.showSetting)
                    }, label: {
                        AssetImage.iconSetting.image
                            .renderingMode(.template)
                            .foregroundColor(AssetColor.Base.primary.color)
                    })
                )
                .introspectViewController { viewController in
                    viewController.view.backgroundColor = AssetColor.Background.primary.uiColor
                }
            }
        }
    }
}

private extension HomeAction {
    init(action: HomeScreen.ViewAction) {
        switch action {
        case .progressViewAppeared:
            self = .refresh
        case .showSetting:
            self = .showSetting
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
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            HomeScreen(
                store: .init(
                    initialState: .needToInitialize,
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
            HomeScreen(
                store: .init(
                    initialState: .initialized(.init(feedContents: [.videoMock(), .videoMock()])),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            HomeScreen(
                store: .init(
                    initialState: .initialized(.init(feedContents: [.videoMock(), .videoMock()])),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}
#endif
