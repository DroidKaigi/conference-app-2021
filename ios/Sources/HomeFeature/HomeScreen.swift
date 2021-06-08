import Component
import ComposableArchitecture
import Styleguide
import SwiftUI

public struct HomeScreen: View {
    private let store: Store<HomeState, HomeAction>

    public init(store: Store<HomeState, HomeAction>) {
        self.store = store
    }

    public var body: some View {
        NavigationView {
            InlineTitleNavigationBarScrollView {
                ZStack(alignment: .top) {
                    AssetColor.primary.color
                        .frame(width: nil, height: 200)
                        .clipShape(CutCornerRectangle(targetCorners: [.topLeft], radius: 42))
                    WithViewStore(store) { viewStore in
                        VStack(alignment: .trailing, spacing: 16) {
                            MessageBar(title: "DroidKaigi 2021 (7/31) D-7")
                                .padding(.top, 16)
                            LargeCard(
                                title: "DroidKaigi 2021とその他活動予定についてのお知らせ",
                                imageURL: nil,
                                tag: .droidKaigiFm,
                                date: Date(),
                                isFavorited: false,
                                tapAction: {},
                                tapFavoriteAction: {}
                            )
                            Divider()
                                .foregroundColor(AssetColor.Separate.contents.color)
                            QuestionnaireView(tapAnswerAction: {
                                viewStore.send(.answerQuestionnaire)
                            })
                            Divider()
                                .foregroundColor(AssetColor.Separate.contents.color)
                            ForEach(viewStore.contents, id: \.self) { content in
                                // TODO: Replace with List Item
                                Text(content)
                                    .foregroundColor(AssetColor.Base.primary.color)
                            }
                        }
                        .padding(.horizontal)
                    }
                }
            }
            .background(AssetColor.Background.primary.color.ignoresSafeArea())
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    AssetImage.logoTitle.image
                }
            }
            .navigationBarItems(
                trailing: AssetImage.iconSetting.image
                    .renderingMode(.template)
                    .foregroundColor(AssetColor.Base.primary.color)
            )
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            HomeScreen(
                store: .init(
                    initialState: .init(
                        contents: ["aaa", "bbb"]
                    ),
                    reducer: homeReducer,
                    environment: .init()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .dark)
            HomeScreen(
                store: .init(
                    initialState: .init(
                        contents: ["aaa", "bbb"]
                    ),
                    reducer: homeReducer,
                    environment: .init()
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, .light)
        }
    }
}
