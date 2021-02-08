package io.github.droidkaigi.confnews2021.data

import io.github.droidkaigi.confnews2021.AppError
import io.github.droidkaigi.confnews2021.News
import io.github.droidkaigi.confnews2021.data.response.FeedsResponse
import io.github.droidkaigi.confnews2021.data.response.InstantSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

interface NewsApi {
    suspend fun fetch(): List<News>
}

fun fakeNewsApi(error: AppError? = null): NewsApi = object : NewsApi {
    override suspend fun fetch(): List<News> {
        if (error != null) {
            throw error
        }
        return list
    }

    // cache for fixing test issue
    @OptIn(ExperimentalStdlibApi::class)
    val list: List<News> = run {
        val responseText = """{
  "status": "OK",
  "articles": [
    {
      "id": "f6691d69-ffbb-499e-812b-12878179fe25",
      "externalId": {
        "value": "cb93eea3083e",
        "serviceName": "medium"
      },
      "title": "DroidKaigi 2020 Activity Report",
      "summary": "This activity report consolidates the effects of the cancellation of DroidKaigi 2020 that was to be held in February 2020, due to COVID-19.",
      "authorName": "mhidaka",
      "authorUrl": "https://medium.com/@mhidaka",
      "thumbnail": {
        "smallUrl": "https://miro.medium.com/max/320/1*c-gCfGSsYkCJiCMssCFL_Q.png",
        "standardUrl": "https://miro.medium.com/max/480/1*c-gCfGSsYkCJiCMssCFL_Q.png",
        "largeUrl": "https://miro.medium.com/max/640/1*c-gCfGSsYkCJiCMssCFL_Q.png"
      },
      "link": "https://medium.com/droidkaigi/droidkaigi-2020-activity-report-en-cb93eea3083e",
      "publishedAt": "2020-07-30T09:34:26.223Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "106ab422-b8e9-415c-8814-8fa9d29938d7",
      "externalId": {
        "value": "940391367b4e",
        "serviceName": "medium"
      },
      "title": "DroidKaigi 2020 活動報告",
      "summary": "DroidKaigi 代表理事 mhidaka です。",
      "authorName": "mhidaka",
      "authorUrl": "https://medium.com/@mhidaka",
      "thumbnail": {
        "smallUrl": "https://miro.medium.com/max/320/1*c-gCfGSsYkCJiCMssCFL_Q.png",
        "standardUrl": "https://miro.medium.com/max/480/1*c-gCfGSsYkCJiCMssCFL_Q.png",
        "largeUrl": "https://miro.medium.com/max/640/1*c-gCfGSsYkCJiCMssCFL_Q.png"
      },
      "link": "https://medium.com/droidkaigi/droidkaigi-2020-report-940391367b4e",
      "publishedAt": "2020-07-30T07:03:35.466Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "48b631b1-b565-44c6-9f94-256f025c8e29",
      "externalId": {
        "value": "f4b59715b96c",
        "serviceName": "medium"
      },
      "title": "DroidKaigiでデザイナーを募集します",
      "summary": "こんにちは、DroidKaigi デザイナーの@mutoatuです。今回はDroidKaigiのデザイナーを募集しますので、その詳細をお知らせします。",
      "authorName": "Atsushi Muto",
      "authorUrl": "https://medium.com/@mutoatu",
      "thumbnail": {
        "smallUrl": "https://miro.medium.com/max/320/0*wT9LBn4T-JSLXhFY",
        "standardUrl": "https://miro.medium.com/max/480/0*wT9LBn4T-JSLXhFY",
        "largeUrl": "https://miro.medium.com/max/640/0*wT9LBn4T-JSLXhFY"
      },
      "link": "https://medium.com/droidkaigi/droidkaigi%E3%81%A7%E3%83%87%E3%82%B6%E3%82%A4%E3%83%8A%E3%83%BC%E3%82%92%E5%8B%9F%E9%9B%86%E3%81%97%E3%81%BE%E3%81%99-f4b59715b96c",
      "publishedAt": "2020-07-07T02:02:51.822Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "7c0b46a9-3752-4050-8291-4f09b5aadb71",
      "externalId": {
        "value": "d190fa57ed10",
        "serviceName": "medium"
      },
      "title": "DroidKaigi 2020 Codelabs",
      "summary": "This is tomoya0x00 from DroidKaigi Committee. DroidKaigi 2020 is approaching!This post is about our original codelab that you can try on…",
      "authorName": "Tomoya Miwa",
      "authorUrl": "https://medium.com/@tomoya0x00",
      "thumbnail": {
        "smallUrl": "https://miro.medium.com/max/320/1*-VG74mo_-SS3GLSteVuQEA.jpeg",
        "standardUrl": "https://miro.medium.com/max/480/1*-VG74mo_-SS3GLSteVuQEA.jpeg",
        "largeUrl": "https://miro.medium.com/max/640/1*-VG74mo_-SS3GLSteVuQEA.jpeg"
      },
      "link": "https://medium.com/droidkaigi/droidkaigi-2020-codelabs-d190fa57ed10",
      "publishedAt": "2020-03-08T14:05:08.319Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "ee03fa43-a17e-4fa3-812e-a6fed30e3822",
      "externalId": {
        "value": "4a677d7456c6",
        "serviceName": "medium"
      },
      "title": "DroidKaigi 2020でのCodelabsについて",
      "summary": "DroidKaigiスタッフの@tomoya0x00です。DroidKaigi 2020の開催日が近づいてきましたね。この記事では、DroidKaigi 2020 Day.1に実施する DroidKaigiオリジナルCodelabsについてご紹介します。",
      "authorName": "Tomoya Miwa",
      "authorUrl": "https://medium.com/@tomoya0x00",
      "thumbnail": {
        "smallUrl": "https://miro.medium.com/max/320/1*-VG74mo_-SS3GLSteVuQEA.jpeg",
        "standardUrl": "https://miro.medium.com/max/480/1*-VG74mo_-SS3GLSteVuQEA.jpeg",
        "largeUrl": "https://miro.medium.com/max/640/1*-VG74mo_-SS3GLSteVuQEA.jpeg"
      },
      "link": "https://medium.com/droidkaigi/droidkaigi-2020%E3%81%A7%E3%81%AEcodelabs%E3%81%AB%E3%81%A4%E3%81%84%E3%81%A6-4a677d7456c6",
      "publishedAt": "2020-03-07T01:26:19.858Z",
      "status": "submitted",
      "language": "japanese"
    }
  ],
  "episodes": [
    {
      "id": "0b328510-182f-47f7-a2ae-a1afc6200baf",
      "externalId": {
        "value": "5",
        "serviceName": "droidkaigi_fm"
      },
      "title": "5. Notificiationよもやま話",
      "summary": "hidey、matsuyama、KeithYokoma、e10dokupの4人でAndroidのNotification周りの変遷や最近踏んだツラい話をしました。",
      "duration": 1456.0,
      "durationLabel": "24:16",
      "speakers": [
        {
          "name": "hidey",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/hidey.jpg"
        },
        {
          "name": "matsuyama",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/matsuyama.jpg"
        },
        {
          "name": "KeithYokoma",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/keithyokoma.jpg"
        },
        {
          "name": "e10dokup",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/e10dokup.png"
        }
      ],
      "thumbnail": {
        "smallUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "standardUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "largeUrl": "https://droidkaigi.jp/fm/images/artwork.jpg"
      },
      "link": "https://droidkaigi.jp/fm/episode/5",
      "publishedAt": "2020-12-21T03:00:00Z",
      "status": "submitted"
    },
    {
      "id": "b7718b84-1191-440b-bf7f-b8e079ba93e0",
      "externalId": {
        "value": "4",
        "serviceName": "droidkaigi_fm"
      },
      "title": "4. 大調達！検証端末ブラザーズ for 2020 秋冬",
      "summary": "hidey、matsuyama、KeithYokoma、e10dokupの4人でAndroidアプリ開発時の検証端末の選定や開発PCについて話しました。なおmatsuyamaさんはPS5の購入に成功したようです。",
      "duration": 2462.0,
      "durationLabel": "41:2",
      "speakers": [],
      "thumbnail": {
        "smallUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "standardUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "largeUrl": "https://droidkaigi.jp/fm/images/artwork.jpg"
      },
      "link": "https://droidkaigi.jp/fm/episode/4",
      "publishedAt": "2020-11-20T03:00:00Z",
      "status": "submitted"
    },
    {
      "id": "02ff1334-19cf-4617-8834-12a59f1cf36b",
      "externalId": {
        "value": "3",
        "serviceName": "droidkaigi_fm"
      },
      "title": "3. DroidKaigi 2020 LiteとDroidKaigi 2020",
      "summary": "DroidKaigi 2020 Liteに向けたやっていきのため、スタッフたち（s_shimotori, satsukies, tnj, yurihondo, ymnd, e10dokup）でDroidKaigi 2020 Liteの内容やDroidKaigi 2020を支えるはずだった技術について話しました",
      "duration": 61.0,
      "durationLabel": "1:1",
      "speakers": [
        {
          "name": "s_shimotori",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/s_shimotori.jpg"
        },
        {
          "name": "satsukies",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/satsukies.png"
        },
        {
          "name": "tnj",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/tnj.png"
        },
        {
          "name": "yurihondo",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/yurihondo.jpg"
        },
        {
          "name": "ymnd",
          "iconUrl": "https://droidkaigi.jp/fm/images/actors/ymnd.jpg"
        }
      ],
      "thumbnail": {
        "smallUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "standardUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "largeUrl": "https://droidkaigi.jp/fm/images/artwork.jpg"
      },
      "link": "https://droidkaigi.jp/fm/episode/3",
      "publishedAt": "2020-08-20T03:00:00Z",
      "status": "submitted"
    },
    {
      "id": "60f2e2da-8628-4d40-8eda-3ba15394af1c",
      "externalId": {
        "value": "2",
        "serviceName": "droidkaigi_fm"
      },
      "title": "2. Android 11 Talks",
      "summary": "#1の続編です。引き続きKeithYokoma、ymnd、e10dokupの3名で、Android 11 Talksで取り上げられた内容についてお話しました。",
      "duration": 1937.0,
      "durationLabel": "32:17",
      "speakers": [],
      "thumbnail": {
        "smallUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "standardUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "largeUrl": "https://droidkaigi.jp/fm/images/artwork.jpg"
      },
      "link": "https://droidkaigi.jp/fm/episode/2",
      "publishedAt": "2020-07-08T15:00:00Z",
      "status": "submitted"
    },
    {
      "id": "6cc7dacd-643e-4553-b155-78c59ffbf6f7",
      "externalId": {
        "value": "1",
        "serviceName": "droidkaigi_fm"
      },
      "title": "1. Android Studio 4.xとAndroid 11",
      "summary": "KeithYokoma、ymnd、e10dokupの3名で、DroidKaigi On Airでも取り上げたAndroid Studio 4.x、そしてAndroid 11についてお話しました。",
      "duration": 1679.0,
      "durationLabel": "27:59",
      "speakers": [],
      "thumbnail": {
        "smallUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "standardUrl": "https://droidkaigi.jp/fm/images/artwork.jpg",
        "largeUrl": "https://droidkaigi.jp/fm/images/artwork.jpg"
      },
      "link": "https://droidkaigi.jp/fm/episode/1",
      "publishedAt": "2020-07-04T03:00:00Z",
      "status": "submitted"
    }
  ],
  "recordings": [
    {
      "id": "925cd30a-63b6-4536-823b-bec1e46f9ddf",
      "externalId": {
        "value": "GuwGFpQ5om0",
        "serviceName": "youtube"
      },
      "title": {
        "japanese": "DroidKaigi 2020 - Widget and Integration Testing in Flutter / JB Lorenzo [EN]",
        "english": "DroidKaigi 2020 - Widget and Integration Testing in Flutter / JB Lorenzo [EN]"
      },
      "summary": {
        "japanese": "Automated Testing is essential to making your apps production-ready and to prevent introducing bugs when you change something. Flutter is an interesting case for testing because it",
        "english": "Automated Testing is essential to making your apps production-ready and to prevent introducing bugs when you change something. Flutter is an interesting case for testing because it"
      },
      "thumbnail": {
        "smallUrl": "https://i.ytimg.com/vi/GuwGFpQ5om0/mqdefault.jpg",
        "standardUrl": "https://i.ytimg.com/vi/GuwGFpQ5om0/hqdefault.jpg",
        "largeUrl": "https://i.ytimg.com/vi/GuwGFpQ5om0/sddefault.jpg"
      },
      "aspectRatio": {
        "width": 4,
        "height": 3
      },
      "link": "https://youtube.com/watch?v=GuwGFpQ5om0",
      "publishedAt": "2020-11-24T04:00:09Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "8bf7bd0a-2ec8-45da-b6fa-406c1e5a0b36",
      "externalId": {
        "value": "bsWHgePdLBg",
        "serviceName": "youtube"
      },
      "title": {
        "japanese": "DroidKaigi 2020 - Meta-Programming with Kotlin / Jitin Sharma [EN]",
        "english": "DroidKaigi 2020 - Meta-Programming with Kotlin / Jitin Sharma [EN]"
      },
      "summary": {
        "japanese": "Kotlin has become go-to language for Android developers all over the world and the language itself has a large number of feature sets.\nWith greater acceptance of language, we look",
        "english": "Kotlin has become go-to language for Android developers all over the world and the language itself has a large number of feature sets.\nWith greater acceptance of language, we look"
      },
      "thumbnail": {
        "smallUrl": "https://i.ytimg.com/vi/bsWHgePdLBg/mqdefault.jpg",
        "standardUrl": "https://i.ytimg.com/vi/bsWHgePdLBg/hqdefault.jpg",
        "largeUrl": "https://i.ytimg.com/vi/bsWHgePdLBg/sddefault.jpg"
      },
      "aspectRatio": {
        "width": 4,
        "height": 3
      },
      "link": "https://youtube.com/watch?v=bsWHgePdLBg",
      "publishedAt": "2020-11-17T04:00:09Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "9e223801-a73e-4710-bcce-35cc48d1d75b",
      "externalId": {
        "value": "UCuf1vXPH3A",
        "serviceName": "youtube"
      },
      "title": {
        "japanese": "DroidKaigi 2020 - FlutterをRenderObjectまで理解する / kiri [JA]",
        "english": "DroidKaigi 2020 - FlutterをRenderObjectまで理解する / kiri [JA]"
      },
      "summary": {
        "japanese": "FlutterはWidgetをツリー構造に組み合わせて、UIレイアウトを構築します。\nそれでは、構築されたWidgetツリーはどのようにスクリーンにレンダリングされるのでしょうか。\n重要な概念はElementとRenderObjectです。\nRenderObjectはレンダリングの責務を担い、ElementはWidgetとRenderObjectの仲介役の責",
        "english": "FlutterはWidgetをツリー構造に組み合わせて、UIレイアウトを構築します。\nそれでは、構築されたWidgetツリーはどのようにスクリーンにレンダリングされるのでしょうか。\n重要な概念はElementとRenderObjectです。\nRenderObjectはレンダリングの責務を担い、ElementはWidgetとRenderObjectの仲介役の責"
      },
      "thumbnail": {
        "smallUrl": "https://i.ytimg.com/vi/UCuf1vXPH3A/mqdefault.jpg",
        "standardUrl": "https://i.ytimg.com/vi/UCuf1vXPH3A/hqdefault.jpg",
        "largeUrl": "https://i.ytimg.com/vi/UCuf1vXPH3A/sddefault.jpg"
      },
      "aspectRatio": {
        "width": 4,
        "height": 3
      },
      "link": "https://youtube.com/watch?v=UCuf1vXPH3A",
      "publishedAt": "2020-11-05T04:00:08Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "21a03088-1ce5-4096-a915-cbf45e58e484",
      "externalId": {
        "value": "uQrzy2d7y7s",
        "serviceName": "youtube"
      },
      "title": {
        "japanese": "DroidKaigi 2020 Lite - Day 2 Night Session",
        "english": "DroidKaigi 2020 Lite - Day 2 Night Session"
      },
      "summary": {
        "japanese": "DroidKaigi 2020 Liteは3日間のオンラインチャリティイベントです。8月27日（木）～8月29日（土）の間に、オンラインセッション、Fireside chat、Ask the Speakerなどさまざまな技術トピックをお届け予定です。\n\nDay 2のプレイリストはこちらです: https://www.youtube.com/playlist?",
        "english": "DroidKaigi 2020 Liteは3日間のオンラインチャリティイベントです。8月27日（木）～8月29日（土）の間に、オンラインセッション、Fireside chat、Ask the Speakerなどさまざまな技術トピックをお届け予定です。\n\nDay 2のプレイリストはこちらです: https://www.youtube.com/playlist?"
      },
      "thumbnail": {
        "smallUrl": "https://i.ytimg.com/vi/uQrzy2d7y7s/mqdefault.jpg",
        "standardUrl": "https://i.ytimg.com/vi/uQrzy2d7y7s/hqdefault.jpg",
        "largeUrl": "https://i.ytimg.com/vi/uQrzy2d7y7s/sddefault.jpg"
      },
      "aspectRatio": {
        "width": 4,
        "height": 3
      },
      "link": "https://youtube.com/watch?v=uQrzy2d7y7s",
      "publishedAt": "2020-08-28T11:21:01Z",
      "status": "submitted",
      "language": "japanese"
    },
    {
      "id": "8f38a6f5-41fa-4faf-8d03-40f7c34fa8f1",
      "externalId": {
        "value": "G5x1WUMj1gE",
        "serviceName": "youtube"
      },
      "title": {
        "japanese": "DroidKaigi 2020 Lite - KotlinのDelegated Propertiesを活用してAndroidアプリ開発をもっと便利にする / chibatching [JA]",
        "english": "DroidKaigi 2020 Lite - KotlinのDelegated Propertiesを活用してAndroidアプリ開発をもっと便利にする / chibatching [JA]"
      },
      "summary": {
        "japanese": "本動画は2020/05/04に公開した動画を DroidKaigi 2020 Lite 用に編集し再公開したものです。\n\n質問は https://forms.gle/nnwMqGWtH66SNkbM7 にお願いします。 19 時から Ask the Speaker ライブ配信を行います。\n\nDay 2の再生リストはこちら: https://www.youtu",
        "english": "本動画は2020/05/04に公開した動画を DroidKaigi 2020 Lite 用に編集し再公開したものです。\n\n質問は https://forms.gle/nnwMqGWtH66SNkbM7 にお願いします。 19 時から Ask the Speaker ライブ配信を行います。\n\nDay 2の再生リストはこちら: https://www.youtu"
      },
      "thumbnail": {
        "smallUrl": "https://i.ytimg.com/vi/G5x1WUMj1gE/mqdefault.jpg",
        "standardUrl": "https://i.ytimg.com/vi/G5x1WUMj1gE/hqdefault.jpg",
        "largeUrl": "https://i.ytimg.com/vi/G5x1WUMj1gE/sddefault.jpg"
      },
      "aspectRatio": {
        "width": 4,
        "height": 3
      },
      "link": "https://youtube.com/watch?v=G5x1WUMj1gE",
      "publishedAt": "2020-08-28T05:00:11Z",
      "status": "submitted",
      "language": "japanese"
    }
  ]
}"""
        val newsContents = Json {
            serializersModule = SerializersModule {
                contextual(InstantSerializer)
            }
        }.decodeFromString<FeedsResponse>(
            responseText
        ).toNewsList()
        newsContents
    }
}
