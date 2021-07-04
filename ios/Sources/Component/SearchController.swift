import Styleguide
import UIKit

@propertyWrapper
public class SearchController: NSObject {

    public let wrappedValue = UISearchController()
    private let searchTextDidChangeTo: (String) -> Void
    private let isEditingDidChangeTo: (Bool) -> Void

    public init(
        searchBarPlaceHolder: String,
        searchTextDidChangeTo: @escaping (String) -> Void,
        isEditingDidChangeTo: @escaping (Bool) -> Void
    ) {
        self.searchTextDidChangeTo = searchTextDidChangeTo
        self.isEditingDidChangeTo = isEditingDidChangeTo
        super.init()
        wrappedValue.searchBar.placeholder = searchBarPlaceHolder
        wrappedValue.searchBar.delegate = self
        // W/A first time showing search result, black clear view does not work correctly
        wrappedValue.obscuresBackgroundDuringPresentation = false
        let cancelButtonAttributes: [NSAttributedString.Key: Any] = [.foregroundColor: AssetColor.primary.uiColor]
        UIBarButtonItem.appearance().setTitleTextAttributes(cancelButtonAttributes, for: .normal)
    }
}

extension SearchController: UISearchBarDelegate {
    public func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        searchTextDidChangeTo(searchText)
    }

    public func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        isEditingDidChangeTo(true)
    }

    public func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        isEditingDidChangeTo(false)
    }
}
