import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct TimetableDetailScreen: View {
    enum Const {
        static let imageSize: CGFloat = 40
    }

    let store: Store<ViewState, ViewAction>

    struct ViewState: Equatable {
        var timeline: TimetableItem
    }

    enum ViewAction {}

    public var body: some View {
        ScrollView {
            WithViewStore(store) { viewStore in
                Text(viewStore.timeline.title.jaTitle)
                    .font(.title)
                    .foregroundColor(AssetColor.Base.primary.color)
                    .lineLimit(nil)
                    .padding(.bottom, 12)

                VStack(alignment: .leading, spacing: 12) {
                    ForEach(viewStore.timeline.speakers, id: \.self) { speaker in
                        HStack {
                            ImageView(
                                imageURL: URL(string: speaker.iconURLString),
                                placeholderSize: .medium,
                                width: Const.imageSize,
                                height: Const.imageSize
                            )
                            .clipShape(Circle())
                            .overlay(
                                RoundedRectangle(cornerRadius: Const.imageSize / 2)
                                    .stroke(AssetColor.Separate.image.color, lineWidth: 1)
                            )
                            Text(speaker.name)
                                .font(.body)
                                .foregroundColor(AssetColor.Base.secondary.color)

                            Spacer()
                        }
                    }
                }
                .padding(.bottom, 24)

                VStack(alignment: .leading, spacing: 6) {
                    ForEach(DesciptionType.allCases, id: \.self) { type in
                        HStack {
                            Text(type.value(timeline: viewStore.timeline))

                            Spacer()
                        }
                    }
                }
                .padding(8)
                .background(AssetColor.Background.secondary.color)
                .cornerRadius(8)
                .padding(.bottom, 32)

                VStack(alignment: .leading, spacing: 8) {
                    Text("受講対象者 / Intended Audience")
                        .font(.body)
                        .bold()
                        .foregroundColor(AssetColor.Base.primary.color)
                    Text("ViewModel、LiveDataぐらいはわかるが、StateFlow辺りから少し混乱している方")
                        .font(.body)
                        .foregroundColor(AssetColor.Base.secondary.color)
                        .lineLimit(nil)
                }
                .padding(.bottom, 32)

                VStack(alignment: .leading, spacing: 8) {
                    Text("説明 / Description")
                        .font(.body)
                        .bold()
                        .foregroundColor(AssetColor.Base.primary.color)
                    Text("みなさんは、AndroidのUIの関連の状態管理や非同期処理について、CoroutiensのrepeatOnLifecycleまでそのメリット、デメリットまで含めて追えていますか？以下のQiitaではAndroidのライフサイクルの基礎からKotlin Coroutinesでのsuspend functionによる処理までは紹介しました。https://qiita.com/takahirom/items/3f012d46e15a1666fa33しかし、現在はKotlin Coroutines Flow、StateFlow、今はさらにlaunchWhenStarted、repeatOnLifecycleが登場して、少し混乱しているのではないでしょうか？ここまでの流れをまとめて紹介していきたいです。")
                        .font(.body)
                        .foregroundColor(AssetColor.Base.secondary.color)
                        .lineLimit(nil)
                }
                .padding(.bottom, 32)
            }
            .padding(.horizontal, 16)
        }
    }
}

private enum DesciptionType: CaseIterable {
    case schedule
    case length
    case category
    case language
    case difficulty

    func value(timeline: TimetableItem) -> String {
        switch self {
        case .schedule:
            return "YYYY-MM-DD 11:00 ~"
        case .length:
            return "25min"
        case .category:
            return timeline.category
        case .language:
            return "日本語"
        case .difficulty:
            return "(Difficulty of the session)"
        }
    }
}

public struct TimetableDetailScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            TimetableDetailScreen(
                store: .init(
                    initialState: .init(
                        timeline: .mock()
                    ),
                    reducer: .empty,
                    environment: {}
                )
            )
            .previewDevice(.init(rawValue: "iPhone 12"))
            .environment(\.colorScheme, colorScheme)
        }
    }
}
