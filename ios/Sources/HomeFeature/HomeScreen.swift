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
                        VStack(alignment: .trailing, spacing: 0) {
                            Text("DroidKaigi 2021 (7/31) D-7")
                                .foregroundColor(Color(AssetColor.Base.white.color))
                                .padding(.vertical, 12)
                                .padding(.horizontal, 8)
                                .background(Color(AssetColor.primaryDark.color))
                                .padding(.vertical)
                            Rectangle()
                                .frame(width: nil, height: 300)
                            Divider()
                                .foregroundColor(Color(AssetColor.Separate.contents.color))
                            VStack(alignment: .trailing, spacing: 12) {
                                HStack {
                                    Image(uiImage: AssetImage.logo.image)
                                    Text("アンケートにご協力をお願いします")
                                        .foregroundColor(Color(AssetColor.Base.primary.color))
                                        .font(.headline)
                                    Spacer()
                                }
                                Button(
                                    action: {},
                                    label: {
                                        Text("回答")
                                            .foregroundColor(Color(AssetColor.primary.color))
                                            .padding(.vertical, 8)
                                            .padding(.horizontal, 32)
                                            .overlay(
                                                Rectangle()
                                                    .stroke(Color(AssetColor.primary.color))
                                            )
                                    }
                                )
                            }
                            .padding(.vertical, 16)
                            Divider()
                                .foregroundColor(Color(AssetColor.Separate.contents.color))
                        }
                        .padding(.horizontal)
                    }
                }
                .navigationBarTitle("", displayMode: .inline)
                .navigationBarItems(
                    trailing: Image(uiImage: AssetImage.iconSetting.image)
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
                initialState: .init(),
                reducer: homeReducer,
                environment: .init()
            )
        ).previewDevice(.init(rawValue: "iPhone 12"))
    }
}
