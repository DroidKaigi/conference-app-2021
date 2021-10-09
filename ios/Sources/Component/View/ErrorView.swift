import Styleguide
import SwiftUI
import ComposableArchitecture

public enum ErrorViewAction {
    case reload
}

public let errorViewReducer = Reducer<Void, ErrorViewAction, Void> { _, action, _ in
    switch action {
    case .reload:
        return .none
    }
}

public struct ErrorView: View {
    private let store: Store<Void, ErrorViewAction>

    public init(store: Store<Void, ErrorViewAction>) {
        self.store = store
    }

    public var body: some View {
        WithViewStore(store) { viewStore in
            VStack(spacing: 32) {
                Text(L10n.Component.ErrorView.title)
                    .font(.title2)
                    .bold()
                    .foregroundColor(AssetColor.Base.primary.color)

                Button(action: {
                    viewStore.send(.reload)
                }, label: {
                    Text(L10n.Component.ErrorView.reload)
                        .font(.headline)
                        .bold()
                        .foregroundColor(Color.white)
                })
                .padding(.vertical, 10)
                .padding(.horizontal, 24)
                .background(AssetColor.primary.color)
                .cornerRadius(20)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(AssetColor.Background.primary.color.ignoresSafeArea())
        }
    }
}

#if DEBUG
public struct ErrorView_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            ErrorView(
                store: .init(
                    initialState: (),
                    reducer: errorViewReducer,
                    environment: ()
                )
            )
            .background(AssetColor.Background.primary.color)
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)
        }
    }
}
#endif
