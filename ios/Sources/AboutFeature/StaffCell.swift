import Styleguide
import SwiftUI

struct StaffCell: View {
    private let name: String
    private let detail: String
    private let iconUrl: URL

    public init(name: String, detail: String, iconUrl: URL) {
        self.name = name
        self.detail = detail
        self.iconUrl = iconUrl
    }

    public var body: some View {
        HStack {
            Image(uiImage: AssetImage.logo.image)
                .resizable()
                .frame(width: 60.0, height: 60.0, alignment: .center)
                .clipShape(Circle())
                .padding(.vertical, 10)
            VStack(alignment: .leading, spacing: 3) {
                Text(name)
                    .font(.custom("SF Pro Display", size: 17))
                    .foregroundColor(AssetColor.Base.primary.color)
                Text(detail)
                    .font(.custom("SF Pro Display", size: 13))
                    .foregroundColor(AssetColor.Base.primary.color)
            }
        }
    }
}

struct StaffCell_Previews: PreviewProvider {
    static var previews: some View {
        StaffCell(
            name: "dummy name",
            detail: "dummy detail",
            iconUrl: URL(string: "https://example.com")!
        )
        StaffCell(
            name: "dummy name",
            detail: "dummy detail",
            iconUrl: URL(string: "https://example.com")!
        )
        .environment(\.colorScheme, .dark)
    }
}
