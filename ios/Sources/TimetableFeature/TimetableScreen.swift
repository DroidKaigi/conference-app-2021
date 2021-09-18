import ComposableArchitecture
import SwiftUI

public struct TimetableScreen: View {
    private let store: Store<TimetableState, TimetableAction>

    public init(store: Store<TimetableState, TimetableAction>) {
        self.store = store
    }

    public var body: some View {
        Text("TimetableScreen")
    }
}

#if DEBUG
public struct TimetableScreen_Previews: PreviewProvider {
    public static var previews: some View {
        TimetableScreen(
            store: .init(
                initialState: TimetableState(),
                reducer: .empty,
                environment: TimetableEnvironment()
            )
        )
    }
}
#endif
