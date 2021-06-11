import Styleguide

public enum SelectedType: Int, CaseIterable {
    case staff
    case contributor

    var title: String {
        switch self {
        case .staff:
            return L10n.AboutScreen.Picker.staff
        case .contributor:
            return L10n.AboutScreen.Picker.contributor
        }
    }
}
