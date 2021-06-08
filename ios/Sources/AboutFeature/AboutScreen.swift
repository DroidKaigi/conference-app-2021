import ComposableArchitecture
import SwiftUI
import Styleguide

public struct AboutScreen: View {

    private let store: Store<AboutState, AboutAction>

    public init(store: Store<AboutState, AboutAction>) {
        self.store = store
        let fontDescriptor = UIFont.preferredFont(forTextStyle: .subheadline).fontDescriptor.withSymbolicTraits(.traitBold)!
        let font = UIFont(descriptor: fontDescriptor, size: 0)
        UISegmentedControl.appearance().selectedSegmentTintColor = UIColor(AssetColor.secondary.color)
        UISegmentedControl.appearance().backgroundColor = UIColor(AssetColor.Background.secondary.color)

        UISegmentedControl.appearance().setTitleTextAttributes([
            .font: font,
            .foregroundColor: UIColor(AssetColor.Base.primary.color)
        ], for: .normal)

        UISegmentedControl.appearance().setTitleTextAttributes([
            .font: font,
            .foregroundColor: UIColor(AssetColor.Base.white.color),
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
                                        StaffCell(staff: staff)
                                    }
                                }
                                .padding(.top, 20)
                            case .contributor:
                                LazyVGrid(
                                    columns: Array(repeating: .init(), count: 3),
                                    spacing: 40
                                ) {
                                    ForEach(viewStore.contributors) { contributor in
                                        ContributorCell(contributor: contributor)
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
                    }
                }
            }
        }
    }
}

public struct AboutScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            AboutScreen(
                store: .init(
                    initialState: .init(
                        staffs: dummyStaffs,
                        contributors: dummyContributors
                    ),
                    reducer: aboutReducer,
                    environment: .init()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            AboutScreen(
                store: .init(
                    initialState: .init(
                        staffs: dummyStaffs,
                        contributors: dummyContributors
                    ),
                    reducer: aboutReducer,
                    environment: .init()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}
