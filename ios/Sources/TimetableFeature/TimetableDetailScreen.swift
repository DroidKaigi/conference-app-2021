import Component
import ComposableArchitecture
import Model
import Styleguide
import SwiftUI

public struct TimetableDetailScreen: View {
    enum Const {
        static let imageSize: CGFloat = 40
        static let iconSize: CGFloat = 20
        static let footerIconSize: CGFloat = 24
    }

    let store: Store<ViewState, ViewAction>

    struct ViewState: Equatable {
        var timetable: TimetableItem
    }

    enum ViewAction {}

    public var body: some View {
        ZStack {
            ScrollView {
                WithViewStore(store) { viewStore in
                    Text(viewStore.timetable.title.jaTitle)
                        .font(.title)
                        .foregroundColor(AssetColor.Base.primary.color)
                        .lineLimit(nil)
                        .padding(.bottom, 12)

                    speakers(viewStore.timetable.speakers)

                    descriptions(timetable: viewStore.timetable)

                    body(timetable: viewStore.timetable)
                }
                .padding(.horizontal, 16)
            }

            fixedFooter(
                tapSlide: {
                    // TODO: FIXME
                },
                tapVideo: {
                    // TODO: FIXME
                }
            )
        }
    }
}

private extension TimetableDetailScreen {
    func speakers(_ speakers: [Speaker]) -> some View {
        VStack(alignment: .leading, spacing: 12) {
            ForEach(speakers, id: \.self) { speaker in
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
    }

    func descriptions(timetable: TimetableItem) -> some View {
        VStack(alignment: .leading, spacing: 4) {
            ForEach(DesciptionType.allCases, id: \.self) { type in
                HStack(spacing: 8) {
                    type.image
                        .foregroundColor(AssetColor.Base.primary.color)
                        .frame(width: Const.iconSize, height: Const.iconSize)

                    Text(type.value(timetable: timetable))
                        .font(.footnote)
                        .foregroundColor(AssetColor.Base.secondary.color)

                    Spacer()
                }
            }
        }
        .padding(8)
        .background(AssetColor.Background.secondary.color)
        .cornerRadius(8)
        .padding(.bottom, 32)
    }

    func body(timetable: TimetableItem) -> some View {
        VStack(alignment: .leading, spacing: 32) {
            VStack(alignment: .leading, spacing: 8) {
                Text(L10n.TimetableScreen.Detail.intendedAudience)
                    .font(.body)
                    .bold()
                    .foregroundColor(AssetColor.Base.primary.color)
                Text("ViewModel、LiveDataぐらいはわかるが、StateFlow辺りから少し混乱している方") // TODO: FIXME
                    .font(.body)
                    .foregroundColor(AssetColor.Base.secondary.color)
                    .lineLimit(nil)
            }

            VStack(alignment: .leading, spacing: 8) {
                Text(L10n.TimetableScreen.Detail.description)
                    .font(.body)
                    .bold()
                    .foregroundColor(AssetColor.Base.primary.color)
                Text("みなさんは、AndroidのUIの関連の状態管理や非同期処理について、CoroutiensのrepeatOnLifecycleまでそのメリット、デメリットまで含めて追えていますか？以下のQiitaではAndroidのライフサイクルの基礎からKotlin Coroutinesでのsuspend functionによる処理までは紹介しました。https://qiita.com/takahirom/items/3f012d46e15a1666fa33しかし、現在はKotlin Coroutines Flow、StateFlow、今はさらにlaunchWhenStarted、repeatOnLifecycleが登場して、少し混乱しているのではないでしょうか？ここまでの流れをまとめて紹介していきたいです。") // TODO: FIXME
                    .font(.body)
                    .foregroundColor(AssetColor.Base.secondary.color)
                    .lineLimit(nil)
            }
        }
        .padding(.bottom, 84)
    }

    func fixedFooter(tapSlide: @escaping () -> Void, tapVideo: @escaping () -> Void) -> some View {
        VStack {
            Spacer()

            HStack {
                VStack {
                    AssetImage.iconClip.image
                        .foregroundColor(AssetColor.primary.color)
                        .frame(width: Const.footerIconSize, height: Const.footerIconSize)
                }
                .padding()
                .background(AssetColor.primaryPale.color)
                .cornerRadius(6)

                HStack(spacing: 10) {
                    AssetImage.iconPlayCircleFilled.image
                        .foregroundColor(.white)
                        .frame(width: 24, height: 24)

                    Text(L10n.TimetableScreen.Detail.watchThisVideo)
                        .font(.headline)
                        .bold()
                        .foregroundColor(.white)

                    Spacer()

                    AssetImage.iconChevron.image
                        .renderingMode(.template)
                        .foregroundColor(.white)
                        .frame(width: Const.footerIconSize, height: Const.footerIconSize)
                }
                .padding()
                .background(AssetColor.primary.color)
                .cornerRadius(6)
            }
            .padding(8)
            .background(AssetColor.Background.primary.color)
        }
    }
}

private enum DesciptionType: CaseIterable {
    case schedule
    case length
    case category
    case language
    case difficulty

    var image: SwiftUI.Image {
        switch self {
        case .schedule:
            return AssetImage.iconSchedule.image
        case .length:
            return AssetImage.iconTime.image
        case .category:
            return AssetImage.iconCategory.image
        case .language:
            return AssetImage.iconLanguage.image
        case .difficulty:
            return AssetImage.iconDifficulty.image
        }
    }

    func value(timetable: TimetableItem) -> String {
        switch self {
        case .schedule:
            return "\(timetable.startsAt.formatToDate) \(timetable.startsAt.formatToTime) ~ \(timetable.endsAt.formatToTime)"
        case .length:
            return "\(getMinDiffFrom(startAt: timetable.startsAt, endAt: timetable.endsAt))min"
        case .category:
            return timetable.category
        case .language:
            return "日本語" // TODO: FIXME
        case .difficulty:
            return "(Difficulty of the session)" // TODO: FIXME
        }
    }

    func getMinDiffFrom(startAt: Date, endAt: Date) -> Int {
        let diff = Int(endAt.timeIntervalSince1970 - startAt.timeIntervalSince1970)
        let hours = diff / 3600
        let minutes = (diff - hours * 3600) / 60
        return minutes
    }
}

private extension Date {
    enum Const {
        static let formatter = DateFormatter()
    }

    var formatToDate: String {
        Const.formatter.dateFormat = "yyyy-MM-dd"
        return Const.formatter.string(from: self)
    }

    var formatToTime: String {
        Const.formatter.dateFormat = "HH:mm"
        return Const.formatter.string(from: self)
    }
}

#if DEBUG
public struct TimetableDetailScreen_Previews: PreviewProvider {
    public static var previews: some View {
        ForEach(ColorScheme.allCases, id: \.self) { colorScheme in
            TimetableDetailScreen(
                store: .init(
                    initialState: .init(
                        timetable: .mock()
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
#endif
