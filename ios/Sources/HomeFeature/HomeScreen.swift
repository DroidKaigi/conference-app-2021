import ComposableArchitecture
import Styleguide
import SwiftUI

public struct HomeScreen: View {
    private let store: Store<HomeState, HomeAction>

    public init(store: Store<HomeState, HomeAction>) {
        self.store = store
    }

    public var body: some View {
        ZStack(alignment: .top) {
            NavigationView {
                ScrollView {
                    ZStack(alignment: .top) {
                        AssetColor.primary()
                            .frame(width: nil, height: 200)
                        WithViewStore(store) { viewStore in
                            VStack(alignment: .trailing, spacing: 0) {
                                Text("DroidKaigi 2021 (7/31) D-7")
                                    .foregroundColor(AssetColor.Base.white())
                                    .padding(.vertical, 12)
                                    .padding(.horizontal, 8)
                                    .background(AssetColor.primaryDark())
                                    .padding(.vertical)
                                // TODO: Replace with card(large)
                                Rectangle()
                                    .frame(width: nil, height: 300)
                                Divider()
                                    .foregroundColor(AssetColor.Separate.contents())
                                QuestionnaireView(tapAnswerAction: {
                                    viewStore.send(.answerQuestionnaire)
                                })
                                Divider()
                                    .foregroundColor(AssetColor.Separate.contents())
                                ForEach(viewStore.contents, id: \.self) { content in
                                    // TODO: Replace with List Item
                                    Text(content)
                                        .foregroundColor(AssetColor.Base.primary())
                                }
                            }
                            .padding(.horizontal)
                        }
                    }
                }
                .background(AssetColor.Background.primary())
                .navigationBarTitle("", displayMode: .inline)
                .navigationBarItems(
                    trailing: Image(uiImage: AssetImage.iconSetting.image)
                        .renderingMode(.template)
                        .foregroundColor(AssetColor.Base.primary())
                )
            }
            Image(uiImage: AssetImage.logoTitle.image)
                .frame(width: nil, height: 44, alignment: .center)
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
