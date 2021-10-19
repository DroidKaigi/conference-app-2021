import Component
import ComposableArchitecture
import Styleguide
import SwiftUI

public struct TimetableScreen: View {
    private let store: Store<TimetableState, TimetableAction>

    public init(store: Store<TimetableState, TimetableAction>) {
        self.store = store
    }

    public var body: some View {
        SwitchStore(store) {
            CaseLet(
                state: /TimetableState.needToInitialize,
                action: TimetableAction.loading,
                then: LoadingView.init(store:)
            )
            CaseLet(
                state: /TimetableState.initialized,
                action: TimetableAction.loaded,
                then: TimetableLoadedView.init(store:)
            )
            CaseLet(
                state: /TimetableState.errorOccurred,
                action: TimetableAction.error,
                then: ErrorView.init(store:)
            )
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
            timeTableScreen(
                state: .needToInitialize
            )
            .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            // errorOccurred
            timeTableScreen(
                state: .errorOccurred
            )
            .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            // initialized
            timeTableScreen(
                state: .initialized(
                    .init()
                )
            )
            .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)

            timeTableScreen(
                state: .initialized(
                    .init(timetableItems: sessionMocks)
                )
            )
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
