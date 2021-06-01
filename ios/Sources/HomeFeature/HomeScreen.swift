import ComposableArchitecture
import Styleguide
import SwiftUI
import Styleguide

public struct HomeScreen: View {
    let store: Store<HomeState, HomeAction>

    public init(store: Store<HomeState, HomeAction>) {
        self.store = store
    }

    public var body: some View {
        ZStack(alignment: .top) {
            NavigationView {
                ScrollView {
                    ZStack(alignment: .top) {
                        Color(AssetColor.primary.color)
                            .frame(width: nil, height: 200)
                        WithViewStore(store) { viewStore in
                            VStack(alignment: .trailing, spacing: 0) {
                                Text("DroidKaigi 2021 (7/31) D-7")
                                    .foregroundColor(Color(AssetColor.Base.white.color))
                                    .padding(.vertical, 12)
                                    .padding(.horizontal, 8)
                                    .background(Color(AssetColor.primaryDark.color))
                                    .padding(.vertical)
                                // TODO: Replace with card(large)
                                Rectangle()
                                    .frame(width: nil, height: 300)
                                Divider()
                                    .foregroundColor(Color(AssetColor.Separate.contents.color))
                                QuestionareView(onTapAnswer: {
                                    viewStore.send(.answerQuestionare)
                                })
                                Divider()
                                    .foregroundColor(Color(AssetColor.Separate.contents.color))
                                ForEach(viewStore.contents, id: \.self) { content in
                                    Text(content)
                                        .foregroundColor(Color(AssetColor.Base.primary.color))
                                }
                            }
                            .padding(.horizontal)
                        }
                    }
                }
                .navigationBarTitle("", displayMode: .inline)
                .navigationBarItems(
                    trailing: Image(uiImage: AssetImage.iconSetting.image)
                        .renderingMode(.template)
                        .foregroundColor(Color(AssetColor.Base.primary.color))
                )
            }
            Image(uiImage: AssetImage.logoTitle.image)
                .frame(width: nil, height: 44, alignment: .center)
        }
    }
}

public struct HomeScreen_Previews: PreviewProvider {
    public static var previews: some View {
        HomeScreen(
            store: .init(
                initialState: .init(
                    contents: ["aaa", "bbb"]
                ),
                reducer: homeReducer,
                environment: .init()
            )
        ).previewDevice(.init(rawValue: "iPhone 12"))
    }
}
