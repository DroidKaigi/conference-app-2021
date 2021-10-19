import ComposableArchitecture
import SafariServices
import SwiftUI

public struct WebViewState: Equatable {
    public var url: URL

    public init(url: URL) {
        self.url = url
    }
}

public struct WebView: UIViewControllerRepresentable {
    public let store: Store<WebViewState, Never>
    private let viewStore: ViewStore<WebViewState, Never>

    public init(store: Store<WebViewState, Never>) {
        self.store = store
        self.viewStore = ViewStore(store)
    }

    public func makeUIViewController(context: Context) -> some UIViewController {
        let safariViewController = SFSafariViewController(
            url: viewStore.url,
            configuration: .init()
        )
        return safariViewController
    }

    public func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    }
}

public struct WebView_Previews: PreviewProvider {
    public static var previews: some View {
        WebView(store: .init(
            initialState: .init(url: URL(string: "https://example.com")!),
            reducer: .empty,
            environment: {}
        ))
    }
}
