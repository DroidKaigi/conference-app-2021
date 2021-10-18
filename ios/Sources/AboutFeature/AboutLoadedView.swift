import Component
import ComposableArchitecture
import Model
import SwiftUI
import Styleguide

public struct AboutLoadedView: View {
    private let store: Store<AboutLoadedState, AboutLoadedAction>

    private enum Const {
        static let bannerHeight: CGFloat = 100
    }

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
                        content(
                            selectedType: viewStore.selectedType,
                            staffs: viewStore.staffs,
                            contributors: viewStore.contributors,
                            tapStaffAction: {
                                viewStore.send(.tapStaff($0))
                            },
                            tapContributorAction: {
                                viewStore.send(.tapContributor($0))
                            }
                        )
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
                        banner {
                            viewStore.send(.tapBanner)
                        }
                    }
                    .sheet(
                        isPresented: viewStore.binding(
                            get: \.isShowingSheet,
                            send: .hideSheet
                        ), content: {
                            IfLetStore(
                                store.scope(
                                    state: \.aboutDroidKaigiState,
                                    action: AboutLoadedAction.aboutDroidKaigi
                                ),
                                then: AboutDroidKaigiScreen.init(store:)
                            )
                            IfLetStore(
                                store.scope(state: \.webViewState).actionless,
                                then: WebView.init(store:)
                            )
                        }
                    )
                }
            }
        }
    }
}

private extension AboutLoadedView {
    func content(
        selectedType: SelectedType,
        staffs: [Staff],
        contributors: [Contributor],
        tapStaffAction: @escaping (Staff) -> Void,
        tapContributorAction: @escaping (Contributor) -> Void
    ) -> some View {
        ScrollView(.vertical) {
            switch selectedType {
            case .staff:
                LazyVStack(alignment: .leading, spacing: 24) {
                    ForEach(staffs) { staff in
                        StaffCell(staff: staff, tapAction: tapStaffAction)
                    }
                }
                .padding(.top, 20)
                .padding(.bottom, Const.bannerHeight)
            case .contributor:
                LazyVGrid(
                    columns: Array(repeating: .init(), count: 3),
                    spacing: 40
                ) {
                    ForEach(contributors) { contributor in
                        ContributorCell(contributor: contributor, tapAction: tapContributorAction)
                    }
                }
                .listStyle(PlainListStyle())
                .padding(.top, 20)
                .padding(.bottom, Const.bannerHeight)
            }
        }
    }
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
public struct AboutLoadedScreen_Previews: PreviewProvider {
    public static var previews: some View {
        Group {
            aboutLoadedView
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            aboutLoadedView
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }

    static var aboutLoadedView: some View {
        AboutLoadedView(
            store: .init(
                initialState: .init(
                    staffs: [.mock(), .mock(), .mock()],
                    contributors: [.mock(), .mock(), .mock()]
                ),
                reducer: .empty,
                environment: {}
            )
        )
    }
}
#endif
