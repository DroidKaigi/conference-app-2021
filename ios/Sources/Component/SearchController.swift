import UIKit

@propertyWrapper
public class SearchController: NSObject {

    public let wrappedValue = UISearchController()
    private let searchTextDidChangeTo: (String) -> Void
    private let isSearchTextEditing: (Bool) -> Void

    public init(
        searchBarPlaceHolder: String? = nil,
        searchTextDidChangeTo: @escaping (String) -> Void,
        isSearchTextEditing: @escaping (Bool) -> Void
    ) {
        self.searchTextDidChangeTo = searchTextDidChangeTo
        self.isSearchTextEditing = isSearchTextEditing
        super.init()
        wrappedValue.searchBar.delegate = self
        // W/A first time showing search result, black blur view does not work correctly
        wrappedValue.obscuresBackgroundDuringPresentation = false
    }
}

extension SearchController: UISearchBarDelegate {
    public func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchTextDidChangeTo(searchText)
    }

    public func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        isSearchTextEditing(true)
    }

    public func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        isSearchTextEditing(false)
    }
}
