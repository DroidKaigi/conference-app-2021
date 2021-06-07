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
        NavigationView {
            VStack {
                WithViewStore(store) { viewStore in
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
                    .padding(.top, 20)
                    .padding(.horizontal)
                    .padding(.bottom, 10)
                    Divider()
                        .foregroundColor(AssetColor.Separate.contents.color)
                    switch viewStore.selectedType {
                    case .staff:
                        ScrollView(.vertical) {
                            LazyVStack(alignment: .leading, spacing: 24) {
                                ForEach(viewStore.staffs) { staff in
                                    StaffCell(staff: staff)
                                }
                            }
                        }
                    case .contributor:
                        ScrollView(.vertical) {
                            LazyVGrid(
                                columns: Array(repeating: .init(), count: 3),
                                spacing: 56
                            ) {
                                ForEach(viewStore.contributors) { contributor in
                                    ContributorCell(contributor: contributor)
                                }
                            }
                            .listStyle(PlainListStyle())
                        }
                    }
                }
                Spacer()
            }
            .navigationBarTitle("")
            .navigationBarHidden(true)
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
