import UIKit

@propertyWrapper
class SearchController: NSObject {

    let wrappedValue = UISearchController()
    private let searchTextDidChangeTo: (String) -> Void

    init(searchBarPlaceHolder: String? = nil, searchTextDidChangeTo: @escaping (String) -> Void) {
        self.searchTextDidChangeTo = searchTextDidChangeTo
        super.init()
        wrappedValue.searchBar.delegate = self
    }
}

extension SearchController: UISearchBarDelegate {
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchTextDidChangeTo(searchText)
    }
}
