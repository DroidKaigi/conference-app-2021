import Component
import Styleguide
import SwiftUI

public struct StaffCell: View {
    enum Const {
        static let imageSize: CGFloat = 60
    }

    private let staff: Staff

    public init(staff: Staff) {
        self.staff = staff
    }

    public var body: some View {
        HStack {
            ImageView(
                imageURL: staff.iconUrl,
                placeholderSize: .medium,
                width: Const.imageSize,
                height: Const.imageSize
            )
            .clipShape(Circle())
            .overlay(
                RoundedRectangle(cornerRadius: Const.imageSize / 2)
                    .stroke(AssetColor.Separate.image.color, lineWidth: 2)
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

public struct StaffCell_Previews: PreviewProvider {
    public static var previews: some View {
        StaffCell(
            staff: Staff(
                name: "dummy name",
                detail: "dummy detail",
                iconUrl: URL(string: "https://example.com")!
            )
        )
        .frame(width: 375, height: 84)
        .environment(\.colorScheme, .light)
        .previewLayout(.sizeThatFits)
        StaffCell(
            staff: Staff(
                name: "dummy name",
                detail: "dummy detail",
                iconUrl: URL(string: "https://example.com")!
            )
        )
        .frame(width: 375, height: 84)
        .environment(\.colorScheme, .dark)
        .previewLayout(.sizeThatFits)
    }
}
