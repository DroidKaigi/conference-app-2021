import UIKit

@propertyWrapper
public class SearchController: NSObject {

    public let wrappedValue = UISearchController()
    private let searchTextDidChangeTo: (String) -> Void
    private let willDismissSearchController: () -> Void

    public init(
        searchBarPlaceHolder: String? = nil,
        searchTextDidChangeTo: @escaping (String) -> Void,
        willDismissSearchController: @escaping () -> Void
    ) {
        self.searchTextDidChangeTo = searchTextDidChangeTo
        self.willDismissSearchController = willDismissSearchController
        super.init()
        wrappedValue.searchBar.delegate = self
        wrappedValue.delegate = self
    }
}

extension SearchController: UISearchBarDelegate {
    public func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchTextDidChangeTo(searchText)
    }
}

extension SearchController: UISearchControllerDelegate {
    public func willDismissSearchController(_ searchController: UISearchController) {
        willDismissSearchController()
    }
}
