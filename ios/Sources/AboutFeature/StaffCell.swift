import Component
import Styleguide
import SwiftUI

public struct StaffCell: View {
    private let staff: Staff

    public init(staff: Staff) {
        self.staff = staff
    }

    public var body: some View {
        HStack {
            ImageView(imageURL: staff.iconUrl)
                .scaledToFill()
                .frame(width: 60, height: 60)
                .clipShape(Circle())
                .overlay(
                    RoundedRectangle(cornerRadius: 30)
                        .stroke(AssetColor.Background.secondary.color, lineWidth: 2)
                )
            VStack(alignment: .leading, spacing: 3) {
                Text(staff.name)
                    .font(.body)
                    .foregroundColor(AssetColor.Base.secondary.color)
                Text(staff.detail)
                    .font(.footnote)
                    .foregroundColor(AssetColor.Base.secondary.color)
            }
        }
        .padding(.leading, 20)
    }
}

struct StaffCell_Previews: PreviewProvider {
    static var previews: some View {
        StaffCell(
            staff: Staff(
                name: "dummy name",
                detail: "dummy detail",
                iconUrl: URL(string: "https://example.com")!
            )
        )
        StaffCell(
            staff: Staff(
                name: "dummy name",
                detail: "dummy detail",
                iconUrl: URL(string: "https://example.com")!
            )
        )
        .environment(\.colorScheme, .dark)
    }
}
