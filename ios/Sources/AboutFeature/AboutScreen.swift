import SwiftUI
import Styleguide

public struct AboutScreen: View {
    
    public init() {
        let font = UIFont.boldSystemFont(ofSize: 15)
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

    enum SelectedType: Int {
        case staff = 0
        case contributor = 1
    }

    @State var selectedType = SelectedType.staff
    private var columns: [GridItem] = Array(repeating: .init(.fixed(UIScreen.main.bounds.width / 3 - 20)), count: 3)
    
    public var body: some View {
        
            NavigationView {
                VStack {
                    Picker("", selection: $selectedType) {
                        Text(L10n.AboutScreen.Picker.staff).tag(SelectedType.staff)
                        Text(L10n.AboutScreen.Picker.contributor).tag(SelectedType.contributor)
                    }
                    .pickerStyle(SegmentedPickerStyle())
                    .padding(.top, 20)
                    .padding(.horizontal)
                    .padding(.bottom, 10)
                    Divider()
                        .foregroundColor(AssetColor.Separate.contents.color)
                    switch selectedType {
                    case .staff:
                        List {
                            ForEach(0..<10) { _ in
                                // TODO: Replace with List Item
                                StaffCell(
                                    name: "dummy name",
                                    detail: "dummy detail",
                                    iconUrl: URL(string: "https://example.com")!
                                )
                            }
                        }
                        .listStyle(PlainListStyle())
                    case .contributor:
                        LazyVGrid(columns: columns) {
                            ForEach(0..<10) { _ in
                                // TODO: Replace with List Item
                                ContributorCell(
                                    name: "dummy name",
                                    iconUrl: URL(string: "https://example.com")!
                                )
                            }
                        }
                        .listStyle(PlainListStyle())
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
            AboutScreen()
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            AboutScreen()
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}
