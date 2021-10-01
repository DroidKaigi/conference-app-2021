import Component
import ComposableArchitecture
import Model
import SwiftUI
import Styleguide
import UIApplicationClient

public struct AboutScreen: View {

    private let store: Store<AboutState, AboutAction>

    public init(store: Store<AboutState, AboutAction>) {
        self.store = store
    }

    internal enum ViewAction {
        case progressViewAppeared
        case reload
    }

    public var body: some View {
        SwitchStore(store) {
            CaseLet(
                state: /AboutState.needToInitialize,
                action: AboutAction.init(action:)
            ) { store in
                WithViewStore(store) { viewStore in
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                        .background(AssetColor.Background.primary.color.ignoresSafeArea())
                        .onAppear { viewStore.send(.progressViewAppeared) }
                }
            }
            CaseLet(
                state: /AboutState.initialized,
                action: AboutAction.loaded,
                then: { store in
                    AboutLoadedView(store: store)
                }
            )
            CaseLet(
                state: /AboutState.errorOccurred,
                action: { (action: AboutScreen.ViewAction) in
                    AboutAction(action: action)
                },
                then: { store in
                    WithViewStore(store) { viewStore in
                        ErrorView(tapReload: {
                            viewStore.send(.reload)
                        })
                    }
                }
            )
        }
    }
}

private extension AboutAction {
    init(action: AboutScreen.ViewAction) {
        switch action {
        case .progressViewAppeared, .reload:
            self = .refresh
        }
    }
}

#if DEBUG
import Repository

public struct AboutScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            Group {
                // needToInitialize
                aboutScreen(state: .needToInitialize)
                    .previewDevice(.init(rawValue: "iPhone 12"))
                    .environment(\.colorScheme, colorScheme)

                // errorOccured
                aboutScreen(state: .errorOccurred)
                    .previewDevice(.init(rawValue: "iPhone 12"))
                    .environment(\.colorScheme, colorScheme)

                // initialized with empty staffs
                aboutScreen(state: .initialized(aboutLoadedState(staffs: [])))
                    .previewDevice(.init(rawValue: "iPhone 12"))
                    .environment(\.colorScheme, colorScheme)

                // initalized with some staffs
                aboutScreen(state: .initialized(
                                aboutLoadedState(staffs: [.mock(), .mock(), .mock()])))
                    .previewDevice(.init(rawValue: "iPhone 12"))
                    .environment(\.colorScheme, colorScheme)
            }
        }
    }

    private static func aboutScreen(state: AboutState) -> some View {
        AboutScreen(
            store: Store<AboutState, AboutAction>(
                initialState: state,
                reducer: .empty,
                environment: AboutEnvironment(
                    applicationClient: UIApplicationClientMock(),
                    contributorRepository: ContributorRepositoryMock(),
                    staffRepository: StaffRepositoryMock()
                )
            )
        )
    }

    private static func aboutLoadedState(staffs: [Staff]) -> AboutLoadedState {
        return AboutLoadedState(
            staffs: staffs,
            contributors: []
        )
    }
}
#endif
