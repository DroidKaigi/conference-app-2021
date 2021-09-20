import ComposableArchitecture
import Model
import Styleguide
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

    private let dateFormatter: DateFormatter = {
       let formatter = DateFormatter()
        formatter.setLocalizedDateFormatFromTemplate("HH:mm")
        return formatter
    }()

    public var body: some View {
        WithViewStore(store) { viewStore in
            ScrollView {
                LazyVStack(spacing: .zero) {
                    ForEach(viewStore.items) { item in
                        HStack(alignment: .top, spacing: 16) {
                            Text(dateFormatter.string(from: item.startsAt))
                                .font(.caption)
                                .foregroundColor(AssetColor.Base.secondary.color)
                                .frame(width: 44)
                            Divider()
                                .frame(width: 2)
                                .foregroundColor(AssetColor.Separate.contents.color)
                            TimetableCard(item: item)
                                .padding(.bottom, 16)
                        }
                    }
                    .onTapGesture {
                        #if DEBUG
                        viewStore.send(.tap(.mock()))
                        #endif
                    }
                }
                .padding(.horizontal, 16)
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
                    .mock(),
                    .mock(),
                    .mock(),
                    .mock(),
                    .mock(),
                    .mock(),
                    .mock(),
                    .mock(),
                    .mock(),
                    .mock(),
                ]),
                reducer: .empty,
                environment: TimetableContentEnvironment()
            )
        )
    }
}
#endif
