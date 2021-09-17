import ComposableArchitecture
import SwiftUI

public struct TimelineScreen: View {
    private let store: Store<TimelineState, TimelineAction>

    public init(store: Store<TimelineState, TimelineAction>) {
        self.store = store
    }

    public var body: some View {
        Text("TimelineScreen")
    }
}

#if DEBUG
public struct TimelineScreen_Previews: PreviewProvider {
    public static var previews: some View {
        TimelineScreen(
            store: .init(
                initialState: TimelineState(),
                reducer: .empty,
                environment: TimelineEnvironment()
            )
        )
    }
}
#endif
