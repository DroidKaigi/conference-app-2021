import UIKit

@propertyWrapper
class SearchController: NSObject {

    let wrappedValue = UISearchController()

    private let willBecomeActive: () -> Void
    private let willResignActive: () -> Void
    private let searchTextDidChange: (String) -> Void

    init(
        searchBarPlaceHolder: String? = nil,
        willBecomeActive: @escaping () -> Void,
        willResignActive: @escaping () -> Void,
        searchTextDidChange: @escaping (String) -> Void
    ) {
        self.willBecomeActive = willBecomeActive
        self.willResignActive = willResignActive
        self.searchTextDidChange = searchTextDidChange
        super.init()
        wrappedValue.delegate = self
        wrappedValue.searchBar.placeholder = searchBarPlaceHolder
        wrappedValue.searchBar.delegate = self
    }
}

extension SearchController: UISearchControllerDelegate {
    func willPresentSearchController(_ searchController: UISearchController) {
        willBecomeActive()
    }

    func willDismissSearchController(_ searchController: UISearchController) {
        willResignActive()
    }
}

extension SearchController: UISearchBarDelegate {
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchTextDidChange(searchText)
    }
}
