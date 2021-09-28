import Component
import ComposableArchitecture
import Styleguide
import SwiftUI

public struct TimetableScreen: View {
    private let store: Store<TimetableState, TimetableAction>

    public init(store: Store<TimetableState, TimetableAction>) {
        self.store = store
    }

    internal enum ViewAction {
        case progressViewAppeared
        case reload
    }

    public var body: some View {
        SwitchStore(store) {
            CaseLet(
                state: /TimetableState.needToInitialize,
                action: TimetableAction.init(action:)
            ) { store in
                WithViewStore(store) { viewStore in
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                        .background(AssetColor.Background.primary.color.ignoresSafeArea())
                        .onAppear { viewStore.send(.progressViewAppeared) }
                }
            }
            CaseLet(
                state: /TimetableState.initialized,
                action: TimetableAction.loaded,
                then: { store in
                    TimetableLoaded(store: store)
                }
            )
            CaseLet(
                state: /TimetableState.errorOccurred,
                action: { (action: TimetableScreen.ViewAction) in
                    TimetableAction(action: action)
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

private extension TimetableAction {
    init(action: TimetableScreen.ViewAction) {
        switch action {
        case .progressViewAppeared, .reload:
            self = .refresh
        }
    }
}

#if DEBUG
import Repository

public struct TimetableScreen_Previews: PreviewProvider {
    public static var previews: some View {
        TimetableScreen(
            store: Store<TimetableState, TimetableAction>(
                initialState: .initialized(.init()),
                reducer: timetableReducer,
                environment: TimetableEnvironment(
                    timetableRepository: TimetableRepositoryMock()
                )
            )
        )
    }
}
#endif
