import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct TimetableLoadedView: View {
    private let store: Store<TimetableLoadedState, TimetableLoadedAction>

    public init(store: Store<TimetableLoadedState, TimetableLoadedAction>) {
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
                                    return .init(items: state.selectedTypeItems, language: state.language)
                                },
                                action: TimetableLoadedAction.content
                            )
                        )
                    }
                }
                .background(AssetColor.Background.primary.color.ignoresSafeArea())
                .navigationTitle(L10n.TimetableScreen.title)
                .background(
                    NavigationLink(
                        destination: IfLetStore(
                            store.scope(
                                state: \.detailState,
                                action: TimetableLoadedAction.detail
                            ),
                            then: TimetableDetailScreen.init(store:)
                        ),
                        isActive: viewStore.binding(
                            get: \.isShowingDetail,
                            send: { _ in .hideDetail }
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
public struct TimetableLoaded_Previews: PreviewProvider {
    public static var previews: some View {
        let calendar = Calendar.init(identifier: .japanese)
        let items: [AnyTimetableItem] = [
            .sessionMock(
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
            .specialMock(
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
            .sessionMock(
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
            .sessionMock(
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
            .sessionMock(
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
            .sessionMock(
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
            .sessionMock(
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
                TimetableLoadedView(
                    store: .init(
                        initialState: .init(
                            timetableItems: items,
                            language: .en
                        ),
                        reducer: timetableLoadedReducer,
                        environment: TimetableLoadedEnvironment()
                    )
                )
                .environment(\.colorScheme, colorScheme)
            }
        }
    }
}
#endif
