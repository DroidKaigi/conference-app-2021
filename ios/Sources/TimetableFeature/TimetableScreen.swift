import ComposableArchitecture
import Styleguide
import SwiftUI

public struct TimetableScreen: View {
    private let store: Store<TimetableState, TimetableAction>

    public init(store: Store<TimetableState, TimetableAction>) {
        self.store = store

        UISegmentedControl.appearance().selectedSegmentTintColor = AssetColor.secondary.uiColor
        UISegmentedControl.appearance().backgroundColor = AssetColor.Background.secondary.uiColor

        UISegmentedControl.appearance().setTitleTextAttributes([
            .foregroundColor: AssetColor.Base.primary.uiColor
        ], for: .normal)

        UISegmentedControl.appearance().setTitleTextAttributes([
            .foregroundColor: AssetColor.Base.white.uiColor,
        ], for: .selected)
    }

    public var body: some View {
        NavigationView {
            WithViewStore(store) { viewStore in
                ZStack(alignment: .top) {
                    AssetColor.Background.primary.color.ignoresSafeArea()
                    VStack {
                        Picker(
                            "",
                            selection:
                                viewStore.binding(
                                    get: { $0.selectedType },
                                    send: { .selectedPicker($0) }
                                )
                        ) {
                            ForEach(SelectedType.allCases, id: \.self) { (type) in
                                Text(type.title).tag(type)
                            }
                        }
                        .pickerStyle(SegmentedPickerStyle())
                        .padding(.horizontal, 16)
                        TimetableContent(
                            store: store.scope(
                                state: { state in
                                    return .init(items: state.selectedTypeItems)
                                },
                                action: TimetableAction.content
                            )
                        )
                    }
                }
                .background(AssetColor.Background.primary.color.ignoresSafeArea())
                .navigationTitle(L10n.TimetableScreen.title)
                .background(
                    NavigationLink(
                        destination: TimetableDetailScreen(),
                        isActive: viewStore.binding(
                            get: \.isShowingDetail,
                            send: TimetableAction.hideDetail
                        )
                    ) {
                        EmptyView()
                    }
                )
            }
        }
    }
}

private extension SelectedType {
    var title: String {
        switch self {
        case .day1:
            return L10n.TimetableScreen.SelectedType.day1
        case .day2:
            return L10n.TimetableScreen.SelectedType.day2
        case .day3:
            return L10n.TimetableScreen.SelectedType.day3
        }
    }
}

#if DEBUG
import Model

public struct TimetableScreen_Previews: PreviewProvider {
    public static var previews: some View {
        let calendar = Calendar(identifier: .japanese)
        let items: [TimetableItem] = [
            .mock(
                startsAt: calendar.date(
                    from: DateComponents(
                        year: 2021,
                        month: 10,
                        day: 19,
                        hour: 11,
                        minute: 00
                    )
                )!
            ),
            .mock(
                type: .special,
                startsAt: calendar.date(
                    from: DateComponents(
                        year: 2021,
                        month: 10,
                        day: 19,
                        hour: 12,
                        minute: 30
                    )
                )!
            ),
            .mock(
                startsAt: calendar.date(
                    from: DateComponents(
                        year: 2021,
                        month: 10,
                        day: 19,
                        hour: 14,
                        minute: 00
                    )
                )!
            ),
            .mock(
                startsAt: calendar.date(
                    from: DateComponents(
                        year: 2021,
                        month: 10,
                        day: 19,
                        hour: 16,
                        minute: 00
                    )
                )!
            ),
            .mock(
                startsAt: calendar.date(
                    from: DateComponents(
                        year: 2021,
                        month: 10,
                        day: 19,
                        hour: 18,
                        minute: 00
                    )
                )!
            ),
            .mock(
                startsAt: calendar.date(
                    from: DateComponents(
                        year: 2021,
                        month: 10,
                        day: 20,
                        hour: 16,
                        minute: 00
                    )
                )!
            ),
            .mock(
                startsAt: calendar.date(
                    from: DateComponents(
                        year: 2021,
                        month: 10,
                        day: 21,
                        hour: 18,
                        minute: 00
                    )
                )!
            ),
        ]
        return ForEach(ColorScheme.allCases, id: \.hashValue) { colorScheme in
            Group {
                TimetableScreen(
                    store: Store<TimetableState, TimetableAction>(
                        initialState: TimetableState(
                            timetableItems: items,
                            selectedType: .day1
                        ),
                        reducer: timetableReducer,
                        environment: TimetableEnvironment()
                    )
                )
                .environment(\.colorScheme, colorScheme)
            }
        }
    }
}
#endif
