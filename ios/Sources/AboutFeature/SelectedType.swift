import Styleguide

public enum SelectedType: Int, CaseIterable {
    case staff = 0
    case contributor = 1

    var title: String {
        switch self {
        case .staff:
            return L10n.AboutScreen.Picker.staff
        case .contributor:
            return L10n.AboutScreen.Picker.contributor
        }
    }
}
