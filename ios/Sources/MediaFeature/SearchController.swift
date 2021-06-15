import UIKit

@propertyWrapper
class SearchController: NSObject {

    let wrappedValue = UISearchController()
    private let searchTextDidChangeTo: (String) -> Void
    private let willDismissSearchController: () -> Void

    init(
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
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchTextDidChangeTo(searchText)
    }
}

extension SearchController: UISearchControllerDelegate {
    func willDismissSearchController(_ searchController: UISearchController) {
        willDismissSearchController()
    }
}
