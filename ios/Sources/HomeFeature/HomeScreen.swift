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
                    Color(AssetColor.primary.color)
                        .frame(width: nil, height: 200)
                }.navigationBarItems(
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
        )
    }
}
