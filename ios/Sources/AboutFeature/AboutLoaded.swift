import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct AboutLoadedState: Equatable {
    public var staffs: [Staff]
    public var contributors: [Contributor]
    public var selectedType: SelectedType
    public var isSheetPresented: SheetType?

    public var isShowingSheet: Bool {
        isSheetPresented != nil
    }

    public enum SheetType: Equatable {
        case url(URL)
        case aboutDroidKaigi
    }

    public init(
        staffs: [Staff] = [],
        contributors: [Contributor] = [],
        selectedType: SelectedType = .staff,
        isSheetPresented: SheetType? = nil
    ) {
        self.staffs = staffs
        self.contributors = contributors
        self.selectedType = selectedType
        self.isSheetPresented = isSheetPresented
    }
}

public enum AboutLoadedAction {
    case selectedPicker(SelectedType)
    case tapStaff(Staff)
    case tapContributor(Contributor)
    case tapBanner
    case hideSheet
}

public struct AboutLoadedEnvironment {
    public init() {}
}

public let aboutLoadedReducer = Reducer<AboutLoadedState, AboutLoadedAction, AboutLoadedEnvironment> { state, action, _ in
    switch action {
    case .selectedPicker(let selectedType):
        state.selectedType = selectedType
        return .none
    case let .tapStaff(staff):
        guard let url = URL(string: staff.profileURLString) else { return .none }
        state.isSheetPresented = .url(url)
        return .none
    case let .tapContributor(contributor):
        guard let url = URL(string: contributor.urlString) else { return .none }
        state.isSheetPresented = .url(url)
        return .none
    case .tapBanner:
        state.isSheetPresented = .aboutDroidKaigi
        return .none
    case .hideSheet:
        state.isSheetPresented = nil
        return .none
    }
}

struct AboutLoaded: View {
    private let store: Store<AboutLoadedState, AboutLoadedAction>

    public init(store: Store<AboutLoadedState, AboutLoadedAction>) {
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
                    ZStack {
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
                            .background(AssetColor.Background.primary.color.ignoresSafeArea())
                            .navigationBarTitleDisplayMode(.inline)
                        }
                        banner {
                            viewStore.send(.tapBanner)
                        }
                    }
                    .sheet(
                        isPresented: viewStore.binding(
                            get: \.isShowingSheet,
                            send: .hideSheet
                        ), content: {
                            IfLetStore(store.scope(state: \.isSheetPresented)) { store in
                                WithViewStore(store) { viewStore in
                                    switch viewStore.state {
                                    case .url(let url):
                                        WebView(url: url)
                                    case .aboutDroidKaigi:
                                        AboutDroidKaigiScreen()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }

}

private extension AboutLoaded {
    func banner(tap: @escaping () -> Void) -> some View {
        VStack {
            Spacer()

            HStack(spacing: 8) {
                Text(L10n.AboutDroidKaigiScreen.whatIs)
                    .foregroundColor(.white)
                    .font(.headline)

                AssetImage.logoTitle.image
                    .colorScheme(.dark)
                    .frame(height: 22)

                Spacer()

                AssetImage.iconChevron.image
                    .renderingMode(.template)
                    .foregroundColor(.white)
            }
            .padding(16)
            .background(AssetColor.dark.color)
            .cornerRadius(6)
            .onTapGesture(perform: tap)
            .padding(16)
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
public struct AboutLoaded_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            AboutLoaded(
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
            AboutLoaded(
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
