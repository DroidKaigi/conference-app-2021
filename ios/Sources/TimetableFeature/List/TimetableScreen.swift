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
                    TimetableLoadedView(store: store)
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
import Model
import Repository

public struct TimetableScreen_Previews: PreviewProvider {
    public static var previews: some View {
        let calendar = Calendar.init(identifier: .japanese)
        let sessionMocks = (0...5).map { num -> AnyTimetableItem in
            let dateComponents = DateComponents(
                year: 2021, month: 10,day: 19, hour: 11 + num, minute: 00
            )
            return AnyTimetableItem.sessionMock(
                startsAt: calendar.date(from: dateComponents)!
            )
        }
        

        let timetableItemMocks: [AnyTimetableItem] = [
            .sessionMock(), .sessionMock(), .specialMock(), .specialMock()
        ]
        
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            // needToInitialize
            timeTableScreen(state: .needToInitialize)
                .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)

            // errorOccurred
            timeTableScreen(state: .errorOccurred)
                .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)

            // initialized
            timeTableScreen(state: .initialized(timeTableLoadedState(items: [])))
                .previewDevice(PreviewDevice(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)
            
            timeTableScreen(state: .initialized(timeTableLoadedState(items: sessionMocks)))
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
    
    private static func timeTableLoadedState(items: [AnyTimetableItem]) -> TimetableLoadedState {
        TimetableLoadedState(
            timetableItems: items
        )
    }
}
#endif
