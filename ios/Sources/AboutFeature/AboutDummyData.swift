import Foundation

// Todo: Remove this file when calls the API through KMM module is implemented
var dummyStaffs: [Staff] {
    var staffs = [Staff]()
    for _ in 0...10 {
        let staff = Staff(
            name: "dummy name",
            detail: "dummy detail",
            iconUrl: URL(string: "https://example.com")!
        )
        staffs.append(staff)
    }
    return staffs
}

var dummyContributors: [Contributor] {
    var contributors = [Contributor]()
    for _ in 0...10 {
        let contributor = Contributor(
            name: "dummy name",
            iconUrl: URL(string: "https://example.com")!
        )
        contributors.append(contributor)
    }
    return contributors
}
