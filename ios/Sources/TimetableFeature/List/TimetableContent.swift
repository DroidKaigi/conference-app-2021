import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct TimetableContentState: Equatable {
    public var items: [AnyTimetableItem]

    public init(
        items: [AnyTimetableItem]
    ) {
        self.items = items
    }
}

public enum TimetableContentAction {
    case tap(AnyTimetableItem)
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
                                .frame(width: 44, alignment: .trailing)
                            Divider()
                                .frame(width: 2)
                                .background(AssetColor.Separate.contents.color)
                            TimetableCard(item: item)
                                .padding(.bottom, 16)
                                .onTapGesture {
                                    viewStore.send(.tap(item))
                                }
                        }
                    }
                }
                .padding(.horizontal, 16)
                .padding(.top, 24)
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
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                    .sessionMock(),
                ]),
                reducer: .empty,
                environment: TimetableContentEnvironment()
            )
        )
    }
}
#endif
