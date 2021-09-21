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
                ZStack {
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
                                    // TODO: Use specified day contents
                                    return .init(items: state.timetableItems)
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

#if DEBUG
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
