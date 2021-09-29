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

    let store: Store<TimetableDetailState, TimetableDetailAction>
    let viewStore: ViewStore<TimetableDetailState, TimetableDetailAction>

    init(store: Store<TimetableDetailState, TimetableDetailAction>) {
        self.store = store
        self.viewStore = ViewStore(store)
    }

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
                timetable: viewStore.timetable,
                tapLink: { url in
                    viewStore.send(.tapLink(url))
                }
            )
        }
        .background(AssetColor.Background.primary.color.ignoresSafeArea())
        .sheet(
            isPresented: viewStore.binding(
                get: \.isSheetPresented,
                send: .hideSheet
            ), content: {
                IfLetStore(store.scope(state: \.showingURL)) { _ in
                    WebView(url: viewStore.showingURL.unsafelyUnwrapped)
                }
            }
        )
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

    func descriptions(timetable: AnyTimetableItem) -> some View {
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

    func body(timetable: AnyTimetableItem) -> some View {
        VStack(alignment: .leading, spacing: 32) {
            VStack(alignment: .leading, spacing: 8) {
                Text(L10n.TimetableScreen.Detail.intendedAudience)
                    .font(.body)
                    .bold()
                    .foregroundColor(AssetColor.Base.primary.color)
                Text(timetable.targetAudience)
                    .font(.body)
                    .foregroundColor(AssetColor.Base.secondary.color)
                    .lineLimit(nil)
            }

            if let session = timetable.wrappedValue as? Session {
                VStack(alignment: .leading, spacing: 8) {
                    Text(L10n.TimetableScreen.Detail.description)
                        .font(.body)
                        .bold()
                        .foregroundColor(AssetColor.Base.primary.color)
                    Text(session.description)
                        .font(.body)
                        .foregroundColor(AssetColor.Base.secondary.color)
                        .lineLimit(nil)
                }
            }
        }
        .padding(.bottom, 84)
    }

    func fixedFooter(timetable: AnyTimetableItem, tapLink: @escaping (URL) -> Void) -> some View {
        VStack {
            Spacer()

            HStack {
                if let slideUrlString = timetable.asset.slideURLString, let url = URL(string: slideUrlString) {
                    AssetImage.iconClip.image
                        .foregroundColor(AssetColor.primary.color)
                        .frame(width: Const.footerIconSize, height: Const.footerIconSize)
                        .padding()
                        .background(AssetColor.primaryPale.color)
                        .cornerRadius(6)
                        .onTapGesture {
                            tapLink(url)
                        }
                }

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
                .onTapGesture {
                    if let videoURL = timetable.asset.videoURLString, let url = URL(string: videoURL) {
                        tapLink(url)
                    }
                }
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
        }
    }

    func value(timetable: AnyTimetableItem) -> String {
        switch self {
        case .schedule:
            return "\(timetable.startsAt.formatToDate) \(timetable.startsAt.formatToTime) ~ \(timetable.endsAt.formatToTime)"
        case .length:
            return "\(getMinDiffFrom(startAt: timetable.startsAt, endAt: timetable.endsAt))min"
        case .category:
            return timetable.category.jaTitle
        case .language:
            return timetable.lang
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
                        timetable: .sessionMock()
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
