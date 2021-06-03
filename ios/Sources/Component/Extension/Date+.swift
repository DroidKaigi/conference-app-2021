import Foundation

extension Date {
    var formatted: String {
        let formatter = DateFormatter.shared
        formatter.dateStyle = .medium
        formatter.timeStyle = .none
        formatter.locale = Locale(identifier: "ja_JP")
        return formatter.string(from: self)
    }
}

extension DateFormatter {
    static let shared = DateFormatter()
}
