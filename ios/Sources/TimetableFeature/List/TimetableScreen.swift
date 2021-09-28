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
        WithViewStore(store) { viewStore in
            VStack(alignment: .center, spacing: 0) {
                switch viewStore.type {
                case .needToInitialize:
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                        .background(AssetColor.Background.primary.color.ignoresSafeArea())
                        .onAppear { viewStore.send(.refresh) }
                case .initialized:
                    TimetableLoaded(store: store.scope(state: \.loadedState, action: { (action: TimetableLoadedAction) in
                        TimetableAction.loaded(action)
                    }))
                case .errorOccurred:
                    ErrorView(tapReload: {
                        viewStore.send(.refresh)
                    })
                }
            }
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
                initialState: .init(language: .en),
                reducer: timetableReducer,
                environment: TimetableEnvironment(
                    timetableRepository: TimetableRepositoryMock()
                )
            )
        )
    }
}
#endif
