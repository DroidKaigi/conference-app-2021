public enum SelectedType: Int, CaseIterable {
    case day1
    case day2
    case day3

    var title: String {
        switch self {
        case .day1:
            return "Day1"
        case .day2:
            return "Day2"
        case .day3:
            return "Day3"
        }
    }
}
