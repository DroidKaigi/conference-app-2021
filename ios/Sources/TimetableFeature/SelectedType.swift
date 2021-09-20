import Foundation
import Styleguide

public enum SelectedType: Int, CaseIterable {
    case day1
    case day2
    case day3

    var title: String {
        switch self {
        case .day1:
            return L10n.TimetableScreen.SelectedType.day1
        case .day2:
            return L10n.TimetableScreen.SelectedType.day2
        case .day3:
            return L10n.TimetableScreen.SelectedType.day3
        }
    }

    var dateComponents: DateComponents {
        switch self {
        case .day1:
            return DateComponents(year: 2021, month: 10, day: 19)
        case .day2:
            return DateComponents(year: 2021, month: 10, day: 20)
        case .day3:
            return DateComponents(year: 2021, month: 10, day: 21)
        }
    }
}
