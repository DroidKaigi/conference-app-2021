import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

struct MediaDetail: View {
    let store: Store<ViewState, Never>

    struct ViewState: Equatable {
        var title: String
        var feedItems: [FeedItemType]
    }

    var body: some View {
        WithViewStore(store) { viewStore in
            AssetColor.Background.primary.color.ignoresSafeArea()
                .navigationTitle(viewStore.title)
        }
    }
}

struct MediaDetail_Previews: PreviewProvider {
    static var previews: some View {
        MediaDetail(store: .init(
            initialState: .init(title: "Blog", feedItems: []),
            reducer: .empty,
            environment: {}
        ))
    }
}
