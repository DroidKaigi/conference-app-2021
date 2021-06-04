import SwiftUI
import Styleguide

struct ContributorCell: View {
    private let name: String
    private let iconUrl: URL

    public init(name: String, iconUrl: URL) {
        self.name = name
        self.iconUrl = iconUrl
    }
    
    public var body: some View {
        VStack {
            Image(uiImage: AssetImage.logo.image)
                .resizable()
                .frame(width: 60.0, height: 60.0, alignment: .center)
                .clipShape(Circle())
                .padding(.bottom, 3)
            
            Text(name)
                .foregroundColor(AssetColor.Base.primary.color)
                .font(.custom("SF Pro Display", size: 12))
                .fontWeight(.medium)
        }
        .padding(.bottom, 30)
    }
}

struct ContributorCell_Preview: PreviewProvider {
    static var previews: some View {
        ContributorCell(
            name: "dummy name",
            iconUrl: URL(string: "https://example.com")!
        )
        ContributorCell(
            name: "dummy name",
            iconUrl: URL(string: "https://example.com")!
        )
        .environment(\.colorScheme, .dark)
    }
}
