import ComposableArchitecture
import Styleguide
import SwiftUI

public struct TimetableScreen: View {
    private let store: Store<TimetableState, TimetableAction>

    public init(store: Store<TimetableState, TimetableAction>) {
        self.store = store
    }

    public var body: some View {
        NavigationView {
            ZStack {
                AssetColor.Background.primary.color.ignoresSafeArea()
            }
        }
    }
}

#if DEBUG
import Model

public struct TimetableScreen_Previews: PreviewProvider {
    public static var previews: some View {
        TimetableScreen(
            store: .init(
                initialState: TimetableState(
                    timetableItems: [
                        .mock(),
                        .mock(),
                        .mock(),
                        .mock(),
                        .mock(),
                        .mock(),
                        .mock(),
                    ]
                ),
                reducer: .empty,
                environment: TimetableEnvironment()
            )
        )
    }
}
#endif
