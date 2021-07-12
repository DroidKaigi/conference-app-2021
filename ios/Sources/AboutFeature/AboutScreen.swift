import ComposableArchitecture
import SwiftUI
import Styleguide

public struct AboutScreen: View {

    private let store: Store<AboutState, AboutAction>

    public init(store: Store<AboutState, AboutAction>) {
        self.store = store
        let fontDescriptor = UIFont.preferredFont(forTextStyle: .subheadline).fontDescriptor.withSymbolicTraits(.traitBold)!
        let font = UIFont(descriptor: fontDescriptor, size: 0)
        UISegmentedControl.appearance().selectedSegmentTintColor = AssetColor.secondary.uiColor
        UISegmentedControl.appearance().backgroundColor = AssetColor.Background.secondary.uiColor

        UISegmentedControl.appearance().setTitleTextAttributes([
            .font: font,
            .foregroundColor: AssetColor.Base.primary.uiColor
        ], for: .normal)

        UISegmentedControl.appearance().setTitleTextAttributes([
            .font: font,
            .foregroundColor: AssetColor.Base.white.uiColor,
        ], for: .selected)
    }

    public var body: some View {
        GeometryReader { geometry in
            NavigationView {
                WithViewStore(store) { viewStore in
                    VStack {
                        ScrollView(.vertical) {
                            switch viewStore.selectedType {
                            case .staff:
                                LazyVStack(alignment: .leading, spacing: 24) {
                                    ForEach(viewStore.staffs) { staff in
                                        StaffCell(staff: staff) { staff in
                                            viewStore.send(.tapStaff(staff))
                                        }
                                    }
                                }
                                .padding(.top, 20)
                            case .contributor:
                                LazyVGrid(
                                    columns: Array(repeating: .init(), count: 3),
                                    spacing: 40
                                ) {
                                    ForEach(viewStore.contributors) { contributor in
                                        ContributorCell(contributor: contributor) { contributor in
                                            viewStore.send(.tapContributor(contributor))
                                        }
                                    }
                                }
                                .listStyle(PlainListStyle())
                                .padding(.top, 20)
                            }
                        }
                        .toolbar {
                            ToolbarItem(placement: .principal) {
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
                                .frame(width: geometry.size.width - 32, height: nil, alignment: .center)
                                .pickerStyle(SegmentedPickerStyle())
                            }
                        }
                        .background(AssetColor.Background.primary.color)
                        .navigationBarTitleDisplayMode(.inline)
                        .onAppear {
                            viewStore.send(.refresh)
                        }
                    }
                }
            }
        }
    }
}

private extension SelectedType {
    var title: String {
        switch self {
        case .staff:
            return L10n.AboutScreen.Picker.staff
        case .contributor:
            return L10n.AboutScreen.Picker.contributor
        }
    }
}

#if DEBUG
public struct AboutScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            AboutScreen(
                store: .init(
                    initialState: .init(
                        staffs: [.mock(), .mock(), .mock()],
                        contributors: [.mock(), .mock(), .mock()]
                    ),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            AboutScreen(
                store: .init(
                    initialState: .init(
                        staffs: [.mock(), .mock(), .mock()],
                        contributors: [.mock(), .mock(), .mock()]
                    ),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}
#endif
