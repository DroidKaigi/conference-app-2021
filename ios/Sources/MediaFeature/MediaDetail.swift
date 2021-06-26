import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct MediaDetail: View {
    let store: Store<ViewState, Never>

    struct ViewState: Equatable {
        var title: String
        var feedContents: [FeedContent]
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            AssetColor.Background.primary.color.ignoresSafeArea()
                .navigationTitle(viewStore.title)
        }
    }
}

public struct MediaDetail_Previews: PreviewProvider {
    public static var previews: some View {
        MediaDetail(store: .init(
            initialState: .init(title: "Blog", feedContents: []),
            reducer: .empty,
            environment: {}
        ))
    }
}
