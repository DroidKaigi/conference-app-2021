import ComposableArchitecture
import Styleguide
import SwiftUI

public enum LoadingViewAciton {
    case onAppeared
}

public let loadingReducer = Reducer<Void, LoadingViewAciton, Void> { _, action, _ in
    switch action {
    case .onAppeared:
        return .none
    }
}

public struct LoadingView: View {
    private let store: Store<Void, LoadingViewAciton>

    public init(store: Store<Void, LoadingViewAciton>) {
        self.store = store
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            ProgressView()
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(AssetColor.Background.primary.color.ignoresSafeArea())
                .onAppear { viewStore.send(.onAppeared) }
        }
    }
}

#if DEBUG
public struct LoadingView_Previews: PreviewProvider {
    public static var previews: some View {
        LoadingView(
            store: .init(
                initialState: (),
                reducer: loadingReducer,
                environment: ()
            )
        )
    }
}
#endif
