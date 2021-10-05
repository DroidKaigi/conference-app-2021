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
                    TimetableLoadedView(
                        store: store.scope(
                            state: \.loadedState,
                            action: { (action: TimetableLoadedAction) in
                                TimetableAction.loaded(action)
                            }
                        )
                    )
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
import Model
import Repository

public struct TimetableScreen_Previews: PreviewProvider {
    public static var previews: some View {
        let calendar = Calendar.init(identifier: .japanese)
        let sessionMocks = (0...5).map { num -> AnyTimetableItem in
            let dateComponents = DateComponents(
                year: 2021, month: 10, day: 19, hour: 11 + num, minute: 00
            )
            return AnyTimetableItem.sessionMock(
                startsAt: calendar.date(from: dateComponents)!
            )
        }

        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            // needToInitialize
            timeTableScreen(state: .init(type: .needToInitialize))
                .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)

            // errorOccurred
            timeTableScreen(state: .init(type: .errorOccurred))
                .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)

            // initialized
            timeTableScreen(state: .init(type: .initialized))
                .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)

            timeTableScreen(state: .init(type: .initialized, timetableItems: sessionMocks))
                .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)
        }
    }

    private static func timeTableScreen(state: TimetableState) -> some View {
        TimetableScreen(
            store: Store<TimetableState, TimetableAction>(
                initialState: state,
                reducer: .empty,
                environment: TimetableEnvironment(
                    timetableRepository: TimetableRepositoryMock()
                )
            )
        )
    }
}
#endif
