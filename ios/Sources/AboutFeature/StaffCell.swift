import Component
import Model
import Styleguide
import SwiftUI

public struct StaffCell: View {
    enum Const {
        static let imageSize: CGFloat = 60
    }

    private let staff: Staff
    private let tapAction: (Staff) -> Void

    public init(staff: Staff, tapAction: @escaping (Staff) -> Void) {
        self.staff = staff
        self.tapAction = tapAction
    }

    public var body: some View {
        HStack {
            ImageView(
                imageURL: URL(string: staff.imageURLString),
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
                // TODO: specify value
                Text("")
                    .font(.footnote)
                    .foregroundColor(AssetColor.Base.secondary.color)
            }
        }
        .padding(.leading, 20)
        .onTapGesture {
            tapAction(staff)
        }
    }
}

#if DEBUG
public struct StaffCell_Previews: PreviewProvider {
    public static var previews: some View {
        StaffCell(
            staff: .mock(),
            tapAction: { _ in }
        )
        .frame(width: 375, height: 84)
        .environment(\.colorScheme, .light)
        .previewLayout(.sizeThatFits)
        StaffCell(
            staff: .mock(),
            tapAction: { _ in }
        )
        .frame(width: 375, height: 84)
        .environment(\.colorScheme, .dark)
        .previewLayout(.sizeThatFits)
    }
}
#endif
