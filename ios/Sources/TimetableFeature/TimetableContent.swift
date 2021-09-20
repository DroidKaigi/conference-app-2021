import ComposableArchitecture
import Model
import SwiftUI

public struct TimetableContentState: Equatable {
    public var items: [TimetableItem]

    public init(
        items: [TimetableItem]
    ) {
        self.items = items
    }
}

public enum TimetableContentAction {
    case tap(TimetableItem)
}

public struct TimetableContentEnvironment {
    public init() {}
}

public let timetableContentReducer = Reducer<TimetableContentState, TimetableContentAction, TimetableContentEnvironment> { _, _, _ in
    .none
}

public struct TimetableContent: View {
    private let store: Store<TimetableContentState, TimetableContentAction>

    public init(store: Store<TimetableContentState, TimetableContentAction>) {
        self.store = store
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            Text("Timetable Content")
                .onTapGesture {
                    viewStore.send(.tap(.mock()))
                }
        }
    }
}

#if DEBUG
public struct TimetableContent_Previews: PreviewProvider {
    public static var previews: some View {
        TimetableContent(
            store: .init(
                initialState: TimetableContentState(items: [
                    .mock()
                ]),
                reducer: .empty,
                environment: TimetableContentEnvironment()
            )
        )
    }
}
#endif
