import SwiftUI
import Styleguide

struct FavoritesEmptyView: View {
    var body: some View {
        VStack {
            VStack(spacing: 31) {
                AssetImage.iconFavorite.image
                    .renderingMode(.template)
                    .resizable()
                    .frame(width: 66, height: 66)
                    .foregroundColor(AssetColor.Base.tertiary.color)

                VStack(spacing: 16) {
                    Text(L10n.FavoriteScreen.Empty.title)
                        .font(.headline)
                        .foregroundColor(AssetColor.Base.tertiary.color)

                    Text(L10n.FavoriteScreen.Empty.description)
                        .font(.body)
                        .foregroundColor(AssetColor.Base.tertiary.color)
                }
            }
            .padding(.top, 83)

            Spacer()
        }
    }
}

#if DEBUG
public struct FavoritesEmptyView_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            FavoritesEmptyView()
                .previewDevice(.init(rawValue: "iPhone 12"))
                .environment(\.colorScheme, colorScheme)
        }
    }
}
#endif
