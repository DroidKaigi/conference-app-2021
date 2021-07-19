import SafariServices
import SwiftUI

public struct WebView: UIViewControllerRepresentable {
    public let url: URL

    public init(url: URL) {
        self.url = url
    }

    public func makeUIViewController(context: Context) -> some UIViewController {
        let safariViewController = SFSafariViewController(url: url, configuration: .init())
        return safariViewController
    }

    public func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    }
}

public struct WebView_Previews: PreviewProvider {
    public static var previews: some View {
        WebView(url: URL(string: "https://example.com")!)
    }
}
