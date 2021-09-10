package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.SessionContents
import io.github.droidkaigi.feeder.data.response.InstantSerializer
import io.github.droidkaigi.feeder.data.session.response.SessionAllResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

interface SessionApi {
    suspend fun fetch(): SessionContents
}

fun fakeSessionApi(error: AppError? = null): SessionApi = object : SessionApi {
    override suspend fun fetch(): SessionContents {
        if (error != null) {
            throw error
        }
        return list
    }

    // cache for fixing test issue
    @OptIn(ExperimentalStdlibApi::class)
    val list: SessionContents = run {
        val responseText = """{
  "sessions": [
    {
      "id": "274c1d32-b975-4b9c-8423-13d9175f6d2a",
      "title": {
        "ja": "Welcome Talk",
        "en": "Welcome Talk"
      },
      "description": null,
      "startsAt": "2020-02-20T10:00:00+09:00",
      "endsAt": "2020-02-20T10:20:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 11511,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 20,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "155510",
      "title": {
        "ja": "Jetpack時代のFragment再入門",
        "en": "A guide to learning Fragment on Jetpack era"
      },
      "description": "Jetpackの登場でFragmentがより書きやすく、より安全になりました。このセッションでは、Jetpackを活用したFragment実装方法を解説します。\r\n\r\nFragmentはUIの実装を再利用するためにAndroid 3.0で追加されました。ですが、かつてのFragmentは扱いの難しいコンポーネントでもありました。Activityとの連携によって生まれる複雑なライフサイクル、非同期のFragment Transaction、密結合しがちなFragment間のやり取り...。Fragmentがもたらす複雑さを避けるため、敢えてFragmentを利用しない開発者もいました。\r\n\r\nしかしJetpackライブラリの登場で状況は変わりつつあります。ライフサイクル管理はLifecycleとLiveDataによって、Fragment TransactionはNavigationによって、Fragment間のやり取りはViewModelによって、それぞれコードを書くのがずっと安全に、簡単になりました。\r\n\r\nJetpackの時代にFragmentに入門し直すとしたら、どの処理をどんなライブラリでどのように書くべきか。このセッションでは、シンプルなTODOアプリを題材にして、Fragmentの各処理をJetpackの文脈で再解説します。\r\n具体的には以下のトピックについて扱う予定です。\r\n\r\n* Fragmentの生成とトランザクション -> Navigation\r\n* ライフサイクル管理 -> Lifecycle, LiveData\r\n* Fragment間のやり取り -> ViewModel\r\n* テスト -> fragment-testing\r\n\r\nこのセッションを通じて、Fragmentにあまり触って来なかった方には「Fragment、これから使っていけそうだな」と、Fragmentを避けてきた方には「最近のFragment、結構いいやつじゃん」と感じていただけたら嬉しいです。",
      "startsAt": "2020-02-20T10:20:00+09:00",
      "endsAt": "2020-02-20T11:00:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "bae43e43-1596-40a5-b20f-5da1a0f0545f"
      ],
      "roomId": 11511,
      "targetAudience": "* Support LibraryのFragmentをJetpackにしていきたい方\r\n* Fragmentを避けてきた方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156392",
      "title": {
        "ja": "チームで気持ちよく、Androidアプリを開発するための基盤作り",
        "en": "Establish environments for comfortable team development"
      },
      "description": "個人開発する上では何も意識しなくてよくても、チームで工夫やルールなどないまま開発をすると、本質的じゃないところに時間を使ってしまうことが多々あります。\r\n例えば、「Lintをかけてないことから起こるスタイル崩れへの指摘およびその修正」や「テスターに対して都度ローカルでapkを作ってから配布」などがそれに当たります。\r\nまたこれらは時間を奪っていくだけでなく、気持ちよく開発する上で弊害になりチーム全体のモチベーションを下げることにも繋がります。\r\n\r\n本セッションでは、チームメンバーが気持ちよく開発をするためにどういう環境を用意するべきなのかにフォーカスを当てます。\r\n１つ１つは当たり前のようにやっていることでも、まとまった情報というのは滅多に見ることがありません。\r\nどのチームでも使えるようなチェックシートを作り、実際にどう用意するのかについて説明します。\r\n\r\n【アジェンダ（予定）】\r\n* 無秩序なチーム開発で起こること\r\n* チェックリスト\r\n* チーム開発を始める時にまずやるべきこと\r\n  * 方針を決める\r\n      * テスト方針を決める\r\n      * 設計方針を決める\r\n      * コーディング規約を決める\r\n  * 事前にツールを入れておく\r\n      * lintを入れる\r\n      * Dangerを入れる\r\n      * CIの設定をする\r\n      * DeployGateの設定をする\r\n  * テンプレートを作っておく\r\n      * プルリクテンプレート\r\n      * イシューテンプレート\r\n      * コミットメッセージテンプレート\r\n  * 毎回作るものは、ライブラリやサンプルを作っておく\r\n      * 強制アップデート\r\n      * トラッキング\r\n      * 認証\r\n  * APIとのつなぎ方も用意しておく\r\n      * swaggerを使った方法\r\n  * etc",
      "startsAt": "2020-02-20T11:20:00+09:00",
      "endsAt": "2020-02-20T12:00:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "e57f43fb-f258-4743-b6bf-cb843bc9185a"
      ],
      "roomId": 11511,
      "targetAudience": "* チーム開発をよくして行きたい方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28653,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156539",
      "title": {
        "ja": "Customize build logic with the latest Android Gradle plugin",
        "en": "Customize build logic with the latest Android Gradle plugin"
      },
      "description": "The Android Gradle plugin API is evolving to become more efficient and useful for app and plugin developers. In this talk, members of the Android Build team will show you how to best utilize this API surface to customize the build process for your project. Whether you're an app developer that wants to dynamically change variant properties between builds, or a plugin creator who wants to publish their custom build logic for others, this talk will provide actionable steps you can implement today.",
      "startsAt": "2020-02-20T11:20:00+09:00",
      "endsAt": "2020-02-20T12:00:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "532dc16d-b69d-4f68-aa49-fe9878b0b52f",
        "be1c9688-683b-4d8a-a3f5-c74b451d079d"
      ],
      "roomId": 11512,
      "targetAudience": "Intermediate Android developers who are already familiar with the basics of the Gradle-based Android build system.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28655,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "セッションの部屋と時間が変更になりました。",
        "en": "This session room and time has been changed."
      },
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156924",
      "title": {
        "ja": "総ざらいMaterialComponents",
        "en": "A general review of MaterialComponents"
      },
      "description": "マテリアルデザインを実現するためにAndroidにはmaterial-componentsというLibraryが用意されており、利用することでマテリアルデザインに準じたUIをより簡単に実装することができます。\r\n\r\nさてみなさん、material-componentsは20数種のComponentを提供していますが、どんなものがあるか認識してるでしょうか？\r\nComponentのガイドラインに目を通したことは？\r\n事前に用意されたスタイルにどんなものがあるか知っていますか？\r\nBottomNavigationBarが簡単に実装できることは？Floatingボタンが伸び縮みできるようになったことは？9月にDatePickerが追加されたことは？？カタログアップをビルドしてみたことは？？...\r\n\r\nmaterial-componentsには思ったよりも多くの機能があります。存在を知っていてもどんな機能が提供されているかを知らなければ使い所も分かりませんよね。\r\n\r\n本発表では、マテリアルデザインの基本を抑えつつ、material-componentsに用意されている機能、コンポーネントの概要・使い所・気をつけるべきことをガイドラインと照らし合わせながら湯水のごとく浴びていきます。\r\n\r\n本発表を聞くことでmaterial-componentsを使いこなし、素敵なマテリアルデザインをより簡単に実装することが出来るようになることでしょう。\r\n\r\n#### 予定している内容\r\n- material-componentsの導入\r\n- Theming\r\n    ColorTheming\r\n　ShapeTheming\r\n　Typography Theming\r\n- Components\r\n    実装方法\r\n　GoodUseCase/BadUseCase\r\n- ダークテーマ\r\n",
      "startsAt": "2020-02-20T11:20:00+09:00",
      "endsAt": "2020-02-20T12:00:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "bc64bad6-642c-4b19-89df-78236e4d8a2b"
      ],
      "roomId": 11513,
      "targetAudience": "* material-componentsで何ができるか知りたい方\r\n* なるべく楽にマテリアルデザインに対応したい方\r\n* マテリアルデザインに興味のある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": true
    },
    {
      "id": "f8bf6649-3a13-49d3-81c7-5a881828a565",
      "title": {
        "ja": "Lunch",
        "en": "Lunch"
      },
      "description": null,
      "startsAt": "2020-02-20T12:00:00+09:00",
      "endsAt": "2020-02-20T13:00:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 12203,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 60,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "155123",
      "title": {
        "ja": "Text in Android Q and Compose",
        "en": "Text in Android Q and Compose"
      },
      "description": "日本語:\r\n我々Android TextチームはAndroid Qで開発者向けにいくつかの機能追加を行いました。日本語向けの改行アルゴリズムの設定、新しい絵文字の追加、フォントをもっとフレキシブルに使うためのAPIなどが新たに入りました。さらに、Androidチームは今年、Jetpack Composeと言う、シンプルで効率的なUI開発を可能とする、新しいAndroid向けUIライブラリを発表しました。このUIライブラリはKotlinを利用するリアクティブプログラミングモデルであり、より簡潔に分かりやすいUI構築を可能にします。\r\n\r\nこの発表では、Android Textの新機能と、Textという観点からJetpack Composeを解説していきたいと思います。\r\n\r\nEnglish:\r\nAndroid Q brings a set of updates to text aimed at improving different use cases for developers: from better Japanese line breaking, more emojis, to more flexibility when working with fonts and others. Moreover, Android team have announced Jetpack Compose which is a new toolkit designed to simplify and accelerate UI development on Android. It combines reactive programming with the conciseness and ease of use of Kotlin.\r\n\r\nIn this talk, we’ll cover what’s new for Android Text and also describe Jetpack Compose from the perspective of Text. \r\n\r\nRelated links: \r\ngoo.gle/Android-Q-text\r\ndeveloper.android.com/jetpack/compose\r\n",
      "startsAt": "2020-02-20T13:00:00+09:00",
      "endsAt": "2020-02-20T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "66296294-ca8f-4d03-85f3-d26aa5035c5d",
        "ad44e7e5-5ba9-427c-a30f-7f447969f213"
      ],
      "roomId": 11511,
      "targetAudience": "日本語:\r\nAndroid UIに興味がある、またはAndroidアプリケーションのユーザーインターフェースを書いたことがある方向け\r\n\r\nEnglish:\r\nAny audience that are interested in Android UI or wrote Android Applications with a user interface.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156213",
      "title": {
        "ja": "パスワードのない未来のためのFirebaseで実装するFIDO2",
        "en": "FIDO2 actualized by Firebase for the password-less future"
      },
      "description": "FIDO（ファイド）はユーザーの認証はローカルで行うため、ユーザーの識別情報がネットワーク上でやりとりされないセキュアなプロトコルです。\r\n\r\nユーザーからのメリットとしてはパスワードを覚える必要がなくワンタップで簡単にログインできますが、実装に関する情報はまだまだ少ないのが現状です。\r\n\r\n本トークではサードパーティ認証を使わず、メールとFIDOを使ったファーストパーティ認証としてどのようにAndroidアプリにFIDO2を実装し運用していくかについて紹介します。\r\n\r\nデモにはFirebase AuthenticationとCloud Functionsを利用します。\r\nサーバーサイドはNode.jsで実装を行います。\r\n\r\nこのトークで話すこと\r\n- なぜID/Passwordじゃダメなのか\r\n- FIDOとは\r\n- FIDO2 for Android\r\n- Firebase Authentication とCloud Functionsを使ってFIDO2の実装\r\n- 実際にサービスに導入する上で検討すべきこと",
      "startsAt": "2020-02-20T13:00:00+09:00",
      "endsAt": "2020-02-20T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "159e1276-8177-4bda-b630-6201df9d897b"
      ],
      "roomId": 11512,
      "targetAudience": "認証があるサービスを作る全ての人\r\n\r\n実装を理解する場合は\r\n- Androidの基礎知識\r\n- Web APIの基礎知識\r\nを前提とします。",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28647,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156912",
      "title": {
        "ja": "Widget and Integration Testing in Flutter",
        "en": "Widget and Integration Testing in Flutter"
      },
      "description": "Automated Testing is essential to making your apps production-ready and to prevent introducing bugs when you change something. Flutter is an interesting case for testing because it is cross-platform. You should be writing also just one test for all your platforms making it easier to maintain.\r\n\r\nFlutter offers three types of tests, unit, Widget, and integration tests. We will focus on widget and integration testing. Widget testing is new for Flutter, coming from Android and iOS. Integration testing is also something you want to make sure your app does not have issues running on iOS or Android. \r\n\r\nWhen you do tests, you mock your dependencies and the network responses and requests. We will discuss how to mock your dependencies and network requests to have a stable test suite. Also, sometimes you also use plugins for Flutter that are using platform-specific code. Examples for these are Firebase Authentication and Firebase Database. We'll discuss how to mock these plugins so you can write widget and integration tests. \r\n\r\nOf course, when doing automated tests, you also think about CI/CD. We will also discuss which services you can use to run your tests and how to set up your tests for CI/CD.",
      "startsAt": "2020-02-20T13:00:00+09:00",
      "endsAt": "2020-02-20T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "ad4257bf-d24f-4955-9372-b1642660bf2b"
      ],
      "roomId": 11513,
      "targetAudience": "Flutter Beginners or those interested in starting Flutter, and want to know how to automate testing.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28656,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156764",
      "title": {
        "ja": "自動生成でさくさく実装するユニットテスト",
        "en": "Implement unit testing quickly by automatic generation"
      },
      "description": "ユニットテストは、日々のAndroidアプリ開発を支える重要な手法です。\r\n\r\nしかし、いざユニットテストのコードを書き始めようとすると、思うように書き進められないことがあります。\r\n\r\n例えば、テストコードの書き方を調べるのに時間を取られてしまって実装タスクがなかなか完了しない、というようなことがあるかもしれません。\r\n\r\nそんなときにテンプレートを用いてテストコードを生成できれば、ユニットテストを書き始める手助けになります。\r\n\r\n本セッションでは、Android開発におけるユニットテストを、自動生成を用いてさくさくと実装する方法を紹介したいと思います。\r\n\r\nセッションの中では下記の内容に触れる予定です。\r\n\r\n・スニペットを用いたテストコードの生成\r\n・KotlinPoetを用いたテストコードの生成\r\n・上記自動生成を用いたAndroid開発でのユニットテスト実装\r\n・テストコードテンプレートのカスタマイズ",
      "startsAt": "2020-02-20T13:00:00+09:00",
      "endsAt": "2020-02-20T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "854edee5-c9cb-49e8-80c4-a0ee63b82f7b"
      ],
      "roomId": 11514,
      "targetAudience": "・ユニットテストをもっと効率的に書きたいと思っている方\r\n・ユニットテストを書くのに時間がかかり、大変だと感じている方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28652,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156840",
      "title": {
        "ja": "1から学ぶAndroidアプリデバッグ - アプリの動作を追いかけよう",
        "en": "Learn Android application debugging from the scratch - track apps' behaviors"
      },
      "description": "アプリ開発には欠かせないデバッグ。バグ報告を受けて原因調査に使うこともあれば、アプリがどのような動作をするのか、どのような状態になっているのかをコードを見ながら追いかけるのに使うこともあります。\r\nログやブレークポイントを使った基本的なデバッグを使う機会はあれど、デバッガーを駆使した手法について少し知ることができるだけで、アプリの挙動、状態といったアプリの「なぜ？」を突き止めることぐっと近づくことができるはずです。\r\n本セッションでは、初心者の方も対象として、実例を交えてAndroid Studioを使ったデバッグ手法を中心に、+1としてTimber、Stetho、Hyperion等のデバッグライブラリを使ったデバッグについても解説する予定です。\r\n\r\n＜アジェンダ＞\r\n- Android Studioのデバッガー\r\n    - Logcat\r\n    - ブレークポイント\r\n    - Android Profiler\r\n    - etc...\r\n- +1：デバッグライブラリを用いたデバッグ\r\n    - Timber\r\n    - Stetho\r\n    - Hyperion",
      "startsAt": "2020-02-20T13:00:00+09:00",
      "endsAt": "2020-02-20T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "704adb77-0ec3-450c-8970-868b7c109df0"
      ],
      "roomId": 11515,
      "targetAudience": "- Android開発初心者の方\r\n- 途中からプロジェクトにジョインしてコードを見ることになった方\r\n- デバッグ中、バグの原因を追いかけることに悩んでいる方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28655,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156383",
      "title": {
        "ja": "アプリの検索UXを考える",
        "en": "Let's think about search UX of apps"
      },
      "description": "検索はユーザーが必要としているコンテンツへアプローチする重要な機能です。\r\n膨大なコンテンツから効率よく情報を探せる機能はサービスのコアな体験にもなり、コンテンツを扱うサービスでは必須といっても過言ではありません。\r\n優れた検索体験はユーザーのエンゲージメントを高めることができますが、よくない検索体験はユーザーがコンテンツにふれる機会を減らす可能性もあります。\r\n\r\nしかし、スマートフォンはPCに比べ画面が小さく表示できる要素に制限があるため、PCと同じ体験を目指してはコミュニケーションを失敗することがあります。また、扱うデータやサービスの特性によっても異なります。つまりアプリで最適な検索体験を提供するには、デバイスの特性だけでなく扱うデータやサービスの特性も考慮する必要があります。\r\n\r\n本セッションでは、テキストコンテンツのサービスを念頭に、アプリでの検索体験を向上させるためのUI/UXデザインについてお話致します。\r\n\r\nまず、検索に必要な体験を考え、ユーザーが検索機能を使う目的について整理します。\r\n次に、Material Design guidelinesを踏まえつつ、検索のUXについて説明します。\r\nそして、様々なアプリで実装されている検索機能の事例を抽象化した上で分類していきます。あわせて、リッチな検索体験を実現するためのフィルターやクエリの機能について検討を行います。\r\n最後に、テキストアプリを念頭に、どのような検索体験を実現できるか実装例を紹介致します。具体的な実装例を扱いつつお話することで、実践的な取り組みへとつながる発表ができればと考えております。\r\n\r\n現在考えている発表内容の構成は次のとおりです。\r\n\r\n・検索の体験を考える\r\n　・そもそも何故検索するのか？\r\n　・検索はサービス側とユーザーの橋渡し\r\n　・検索は内容でチューニングが必要\r\n　・ユーザーの目的を理解する\r\n　・...\r\n・Material Design guidelinesの簡単な基本概念\r\n・Search UXについて\r\n　・サジェスト\r\n　・提案\r\n　・結果\r\n    ・音声\r\n　・...\r\n・Search UI/UXのパターン整理\r\n・モデルケースの紹介\r\n・まとめ\r\n",
      "startsAt": "2020-02-20T13:00:00+09:00",
      "endsAt": "2020-02-20T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "00f8c0a3-55cc-4c3a-af45-730fe03ea6b8",
        "6f216970-9b9c-4ecc-86e3-b28cd46d572e",
        "975fd8a9-088c-4e6c-881c-b3e7504f21cc"
      ],
      "roomId": 11516,
      "targetAudience": "・UI/UXについて興味のある方\r\n・アプリで検索画面を作られたことのある方\r\n・検索機能をこれから開発しようと考えられている方\r\n・検索機能をよりリッチにしたいと考えられている方\r\n・デザインを実装するまでの開発の流れに興味のある方\r\n",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "cb063fe3-de9d-4087-9e63-adf1b10bd19c",
      "title": {
        "ja": "既存のアプリをマルチモジュール化する方法 ~ DroidKaigiオリジナルCodelabs ~",
        "en": "Multi-Modularizing Apps: A DroidKaigi Original Codelab"
      },
      "description": "A DroidKaigi staff original codelab.\nIn this codelab, you will be tasked to split an existing app into multiple modules.\n\nFor a more practical experience, we will use the GithubBrowserSample from the Android Architecture Components samples, which had its first commit three years ago.\n\nWe have also used claat to prepare a more cleaner, Google Codelabs-style course material.\n\nNote that codelabs participation is on a first-come, first-served basis, but you can join/leave at any time!\n\n\nDroidKaigiスタッフオリジナルのCodelabsです。\n今回は既存アプリのマルチモジュール化に取り組んで頂きます。\n\n実践的なCodelabsを実現するため、最初のコミットからほぼ3年が経過しているAndroid Architecture Components samplesの一つ、GithubBrowserSampleをマルチモジュール化するお題としました。\nチューターと一緒に、GithubBrowserSampleのモジュール分割をおこなっていきましょう。\n\n今回はclaatを用い、Google Codelabs形式の資料をご用意しています。\n\nなお、受付は先着順となります。入退場は自由ですので、ふるってご参加下さい！",
      "startsAt": "2020-02-20T13:00:00+09:00",
      "endsAt": "2020-02-20T16:00:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 12203,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 180,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "154763",
      "title": {
        "ja": "継続的に機能開発を進めながら行うマルチモジュール化",
        "en": "A continuous process of modularization with feature development"
      },
      "description": "2017年頃からアプリケーションのマルチモジュール化による利点が多く得られるようになった．特に「差分ビルドのビルド時間短縮化」「InstantAppやDynamicFeatureModuleの導入可能」「モジュール依存関係グラフのDAG化」といった利点が語られる．これらの利点は肥大化したアプリケーション上で高速に開発を行う上での助けになったり，ユーザ体験を向上させる手助けとなったりする．\r\n\r\n一方で，これまで大部分をアプリケーションモジュール(:app)で開発されてきたアプリケーションを，容易にマルチモジュール化することは難しい．特にMVVMやFluxといったルールを明確化したアーキテクチャに乗っていない場合は問題が顕著だ．また，並行してマルチモジュール化とは本質的に無関係な機能改善を進めていく必要がある場合，問題はさらにややこしくなる．\r\n\r\n本セッションでは上に挙げた実体験を基にどのようにしてマルチモジュール化を進めていったのかを時系列順に，そのハマりどころと解決方法を中心に紹介する．\r\n\r\n<アジェンダ(仮)>\r\n- マルチモジュールとは何で，導入するとどう嬉しいのか？\r\n- はじめの一歩は:appから:legacyへ\r\n- ApplicationモジュールとLibraryモジュールの違いによって生じる問題\r\n- Flavor，buildType，BuildConfigのマルチモジュールでの取り扱い\r\n- 共通リソースのモジュール化とそのハマりどころ\r\n- マルチモジュールとJetPack 〜どのようなライブラリの導入効果が高いのか〜\r\n- 意味境界としてのモジュール分割 〜モジュール分割戦略〜\r\n- 継続的な機能開発との共存法\r\n- TBD",
      "startsAt": "2020-02-20T14:00:00+09:00",
      "endsAt": "2020-02-20T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "11cc830a-2767-4da6-9499-c7fa006a9441"
      ],
      "roomId": 11511,
      "targetAudience": "- アプリのマルチモジュール化に興味がある人\r\n- 継続的にアプリ改善を続けながらアプリの改修を行いたい人\r\n- どうやってマルチモジュール化を行うか？その「手順」に興味がある人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "153892",
      "title": {
        "ja": "A Journey of Application Distribution",
        "en": "A Journey of Application Distribution"
      },
      "description": "Application Distribution (a.k.a application delivery, application deployment) is absolutely required for modern mobile development and collaborative development. \r\n\r\nA lot of people -- your co- mobile app engineers, server/api engineers, QA engineers, designers, directors, testers and etc. -- would like to easily try changes you've made in some way.\r\n\r\nWe have currently several choices of distribution services and there are a lot of workflows that include such services. However, we cannot get much information about the distribution services and/or the workflows curiously. Because Application Distribution stuff is kinda tacit knowledge of each company and/or team. So we do not so often rethink and change currently using delivery flows, unfortunately. \r\n\r\nA delivery flow that you are currently using might be *stable* but it's not always *effective* in any other company. It doesn't mean less-useful, of course.\r\nLet's rethink and rebuild your mobile application delivery environment through this presentation to accelerate development.\r\n\r\nPoints:\r\n\r\n- The overview of Application Distribution\r\n- The history of Application Distribution services\r\n- Pros/Cons of the currently available services\r\n- Examples of practical delivery flow",
      "startsAt": "2020-02-20T14:00:00+09:00",
      "endsAt": "2020-02-20T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "65df1273-b4f7-47c9-a225-ab63a521f5b4"
      ],
      "roomId": 11512,
      "targetAudience": "For those who:\r\n\r\n- have interests in application delivery stuff\r\n- would like to compare application distribution services\r\n- don't know why your team has chosen currently using distribution service\r\n\r\nRequires no specific level and knowledge (except Android development)!",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28655,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156349",
      "title": {
        "ja": "運用中アプリにダークテーマを適用してみる",
        "en": "Apply Dark Theme to our live app"
      },
      "description": "Android10でダークテーマが発表されました。夜間利用時に目に優しいこと、またバッテリーの持ちがよりよくなることから、今後より各アプリにダークテーマ対応が求められていくと思います。今回は、現在リリースされているアプリへダークテーマを導入していくために、どのような準備が必要で、デザイナーとどういったやりとりがあったか。また、実装をした際に起こった問題、注意していくポイント等を具体的にお話します。\r\n",
      "startsAt": "2020-02-20T14:00:00+09:00",
      "endsAt": "2020-02-20T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "b81c21b8-8656-4351-ba07-f1a0cfc83638"
      ],
      "roomId": 11513,
      "targetAudience": "ダークテーマに興味がある人\r\nすでにストアに出ているアプリにダークテーマを入れてみたいエンジニア、デザイナー",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156202",
      "title": {
        "ja": "高くなったハードルをくぐる。2020年のAndroidアプリ開発入門",
        "en": "The beginner guide 2020-edition to developing Android apps to avoid the big hurdle"
      },
      "description": "本講演では、ぼくの考える「2020年のAndroidアプリ開発入門」について発表します。\r\n\r\n「移り変わる流行りのアーキテクチャ」\r\n「鉄板と言われるライブラリ」\r\n「今後来る（かもしれない）トレンド」\r\nAndroidアプリ開発のハードルは、日々上がっていると言われています。\r\n\r\nこの状況は特に、Androidアプリ開発の初心者にとって深刻です。\r\n上がったハードルを（熟練の開発者がそうしているように）乗り越えようと過大な設計をしてしまったり、複雑なライブラリ依存の森に迷い込んでしまったり。\r\n初心者が「Androidアプリ開発は難しい」と諦めてしまう一因になっています。\r\n\r\nぼくは、すべてのライブラリやアーキテクチャは、開発者が「楽」をするために存在する。開発が「楽」にならないのであれば、そのライブラリやアーキテクチャは採用するべきではないと考えています。\r\n\r\n本講演では、高いハードルに怯まず、ライブラリやアーキテクチャを取捨選択する指針。ぼく自身が考えているアプリ開発に必要な知識のボトムライン。それらを身につけることで何が「楽」になるのかを解説します。\r\n\r\n・Overview\r\n・それ、導入して「楽」になる？\r\n・メンテナンスコストが上がる（内的・外的）要因\r\n・高くなったハードルを 越える|くぐる 方法\r\n・初心開発者のためのAndroidアプリ開発入門\r\n",
      "startsAt": "2020-02-20T14:00:00+09:00",
      "endsAt": "2020-02-20T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "49b04902-1ce9-4276-99ba-4420556ac63d"
      ],
      "roomId": 11514,
      "targetAudience": "今後、アプリを新規開発することのあるAndroidアプリ開発者（初心者に限らない）",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156842",
      "title": {
        "ja": "Kotlin/MPPでAndroid, iOSをMPF開発した話",
        "en": "A talk about MPF development using Kotlin MPP for Android and iOS apps"
      },
      "description": "2018年にKotlin 1.3でFeatureとしてMPPが入り、また同年末にはFlutterがリリースされ、Android界隈で見ただけでもMPF(Multi-Platform)の実現方法が多様化しています。\r\nそんな2018年下旬、私達のプロダクトはKotlin/MPPでの開発を採用し、現在オンプロダクトで稼働させることができました。\r\n\r\n人的リソースは有限であり人材不足が叫ばれる昨今、省コストで素早く価値をデリバリーできるMPFは、プロダクト立ち上げ時に気になる領域です。\r\n反して、MPFは概ね継続的な開発に問題を抱えることが多く、事業が継続した場合のメンテナビリティが課題になりがちで採用に二の足を踏む方も多いのではないでしょうか？\r\n\r\n今回私達は長期運用を目的とした既存アプリのリプレースという観点で開発手法の選定を行いましたが、Kotlin/MPPはその目的に十分足るものでした。\r\nでは、Kotlin/MPPは他のMPFとはどのような違った特徴を持っているのでしょうか？\r\n\r\n## 予定している内容\r\n以上の経験を元に、この発表では以下のことについて話したいと思います。\r\n* Kotlin/MPP採用に至った経緯\r\n* Kotlin/MPPを採用することによるメリット・デメリット\r\n  * デメリットに立ち向かうアプリケーション設計\r\n    * AtomicDesign、Flux、(Clean Architectureベースの)オニオンアーキテクチャ\r\n  * MPPで使えるライブラリ使えないライブラリ\r\n  * CoroutineとRxの共生\r\n* 開発中に発生した問題とその対処\r\n\r\n## この発表で得られる予定の知見\r\n* Kotlin/MPPが向いてること、向いてないこと\r\n* 実際にKotlin/MPPを使ってみた経験談\r\n* Fluxによる大規模、ロングライフ向け設計\r\n\r\n",
      "startsAt": "2020-02-20T14:00:00+09:00",
      "endsAt": "2020-02-20T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "17bd0ded-66d8-437a-8a62-525844850b34"
      ],
      "roomId": 11515,
      "targetAudience": "* Multi-Platformの候補としてKotlin/MPPを考えている方\r\n* Kotlin/MPPを使用した設計でお悩みの方\r\n* 情報収集で、Kotlin/MPPに興味がある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28656,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156919",
      "title": {
        "ja": "アプリの継続開発で見据えるべき過去と未来のコード",
        "en": "Remarkable code of in the past and future for continuous application development"
      },
      "description": "AndroidアプリやiOSアプリはアプリストアからユーザーの端末にインストールして使われます。\r\nこうした配信の仕組み上、過去のコードによる生成物が常に世界のどこかに残ってしまいます。\r\nそのためアプリ開発では通常よりもタイムスケールの大きなリリースやコードの寿命にも注意を払う必要があります。\r\nこのセッションではビルドしたアプリがユーザーに届くまでの話や、APIの破壊的変更やローカルデータベースのスキーマ変更など、アプリ開発につきまとう寿命や、未来にためにそうした寿命とどう向き合うかについてまとめます。\r\nまた、こうした状況に出くわしたときアプリエンジニアはチーム内でどう対応するべきか、チームに何を知っておいてもらわないといけないのか、といったことにもフォーカスします。\r\nAndroidアプリに限らずiOSアプリとの比較についても紹介します。\r\n\r\n内容\r\n- リリースとは、寿命とは\r\n- AndroidアプリやiOSアプリがユーザーに届くまで\r\n- Google Play StoreとAppStoreでの段階的リリースという概念の違い\r\n- APIのサポート\r\n- マイグレーションのサポート\r\n- OS/端末のサポート\r\n- 開発言語のアップデートや変更\r\n- ユーザーへのアップデート訴求 (In-app Updates APIなど)",
      "startsAt": "2020-02-20T14:00:00+09:00",
      "endsAt": "2020-02-20T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "63a3dc07-8891-4cf2-b74c-15c6f626f7f3"
      ],
      "roomId": 11516,
      "targetAudience": "- ひとつのアプリを継続開発している方・継続開発していく予定の方\r\n- 継続開発中のアプリの開発をディレクションしている方\r\n- AndroidアプリとiOSアプリでのアプリリリースの違いが知りたい方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28653,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156779",
      "title": {
        "ja": "How to handle the credit card data on Android / Androidでクレジットカード情報を扱う",
        "en": "How to handle the credit card data on Android"
      },
      "description": "Credit card data should be handled sensitively and securely.\r\nKyash which is the fintech app provided by my company supports the registration, display, activation in the app.\r\nhttps://play.google.com/store/apps/details?id=co.kyash\r\n\r\nI'll talk about the tips to handle the creadit card data from my experience in Kyash.\r\nIt would be helpful when you create the app which handles the credit card data like below.\r\n\r\n- Input (Form validations、EditText cursor、Card brand check)\r\n- Scan（Scan by camera、Contactless scan by NFC）\r\n- API (Certificate pinning、Encryption and decryption)\r\n- User's action (Copy&Paste、Screenshots alert)\r\n\r\n--------\r\n\r\nクレジットカード情報はとても慎重に扱われるべき情報です。\r\n私が業務で携わっているKyashでは、Androidアプリ上でクレジット/デビットカードの登録や表示、有効化を行います。\r\nhttps://play.google.com/store/apps/details?id=co.kyash\r\n\r\nこのセッションでは、私が業務で培った経験から、クレジットカード情報を扱うアプリを作成する際に知っておくべきことを説明します。以下の内容に触れる予定です。\r\n\r\n- カード入力 (バリデーション、EditTextのカーソル制御、カードブランド判定)\r\n- カード読取（カメラでの読取、NFCによる非接触での読取）\r\n- API通信 (証明書ピン留め、暗号化と復号)\r\n- ユーザー操作 (コピー&ペーストの制御、スクリーンショット警告)",
      "startsAt": "2020-02-20T15:00:00+09:00",
      "endsAt": "2020-02-20T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "903eaf98-bf70-42a1-80da-6af3c5ec6668"
      ],
      "roomId": 11511,
      "targetAudience": "People who are interested in the implementation to handle the credit card data securely\r\n\r\n--------\r\n\r\nクレジットカード情報をセキュアに扱う実装に興味がある人",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156210",
      "title": {
        "ja": "MDCの内部実装から学ぶ 表現力の高いViewの作り方",
        "en": "How to build expressive Views learning through internal implementations of MDC"
      },
      "description": "Material Components(MDC)は2018年のGoogle I/Oで発表されました。\r\nDesign Support Libraryの役割を引き継ぎ、FloatingActionButtonやBottomNavigationといったMaterial Designの実装に不可欠なコンポーネントを提供しています。\r\nそれだけではなく、Material Themingでの自由な表現を実現するために、各コンポーネントが高いカスタマイズ性を持っていることも特徴です。\r\n\r\nアプリをMaterial Themingに対応させていくうえで、CustomViewを実装する必要性が高くなってきています。\r\nところが、いざ実装しようとした際には「機能を実現するために必要なAPIがわからない、どのAPIを使うのが最適か分からない」といった状況に陥りがちです。\r\n\r\nこのセッションでは、MDCの各コンポーネントの特徴的な機能がAndroid FrameworkのどのようなAPIを使って実装されているのかを解説します。\r\n特に、同じ表現にも関わらず異なるAPIが使われているコンポーネントを対比し、何故そのような実装になっているかを解説します。\r\n\r\n用途に合ったAPIを知り、効率的でメンテナンス性の高いViewを作れるようになりましょう。\r\n\r\n\r\n※ このセッションではAndroid向けのMDCだけを取り上げます\r\n\r\nセッション概要\r\n- Drawableでの表現\r\n　- カスタムDrawable実装の基礎\r\n　- MaterialShapeDrawableで何ができるか、内部では何をしているか\r\n- Shadowの表現方法\r\n　- FAB・Extended FAB\r\n　- BottomAppBar\r\n- 文字サイズを変えるアニメーション\r\n　- BottomNavigationのlabel\r\n　- TextInputLayoutのhint\r\n- Viewのサイズを変える\r\n　- Chip\r\n　- Extended FAB\r\n",
      "startsAt": "2020-02-20T15:00:00+09:00",
      "endsAt": "2020-02-20T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "697e3809-2e7e-4314-8db1-0fb5fd0c9f8b"
      ],
      "roomId": 11512,
      "targetAudience": "中級者の方のViewに対する知識を向上させることを目的としたセッションですが、上級者でも知見を得られるセッションを目指します\r\n\r\n- Viewの表現力を向上させたい方\r\n- MDCに不足しているViewコンポーネントを実装したい方\r\n- MDCの内部実装に興味がある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "154003",
      "title": {
        "ja": "Meta-Programming with Kotlin",
        "en": "Meta-Programming with Kotlin"
      },
      "description": "Kotlin has become go-to language for Android developers all over the world and the language itself has a large number of feature sets.\r\nWith greater acceptance of language, we look at how we can leverage power of Kotlin for meta-programming.\r\nThrough the talk, we'll introduce what Meta-Programming is and how we do it on day to day basis. We'll see how using Kotlin's internal compiler API, we can parse kotlin files, pull data out of it and go through various applications of this data such as custom documentation for API code and much more.\r\nWe'll also look at various approaches of source code generation for Kotlin files and how we can leverage them in projects.",
      "startsAt": "2020-02-20T15:00:00+09:00",
      "endsAt": "2020-02-20T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "a7da8c70-c13b-4ad5-aa0a-ddb502e5eed6"
      ],
      "roomId": 11513,
      "targetAudience": "People who are programming with Kotlin",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": true
    },
    {
      "id": "156686",
      "title": {
        "ja": "How to develop debugging libraries / デバッグライブラリを使うから作るへ",
        "en": "How to develop debugging libraries / Become developers from users of debugging libraries"
      },
      "description": "Debugging tools are essential to analyze application status during the development phase. By using them, developers can efficiently get enormous amounts of information about both the internal and external operations, for example, display information, output logs, database monitoring and internet connection status. However, how can developers more deeply understand debugging libraries? \r\nThis session would address debugging libraries to dive deep into their implementation. \r\n\r\nPoints:\r\n・The overview of debugging libraries\r\n・The explanation of internal implementation\r\n・Development methodology and practical applications of debugging tools\r\n\r\n----\r\n\r\nアプリ開発で、デバッグライブラリはアプリの状態を分析するために必要不可欠です。デバッグライブラリにより、ログを出力する、DBや通信状態を見る、表示要素を分析するなど、アプリの現在の内部的・外部的状態を効率良く取得できます。\r\n\r\nしかし、デバッグライブラリはどのようにアプリの状態を取得し、見やすい形で表示しているのでしょうか。\r\n\r\n本セッションでは、デバッグライブラリの内部実装を探求し、アプリの状態を分析するための仕組みに迫ります。\r\n\r\n現在考えているセッションの構成は次のとおりです。\r\n・デバッグライブラリについて\r\n　・なぜデバッグをするのか\r\n　・どんな情報を取得したいか\r\n　・デバッグライブラリの紹介\r\n　　・Stetho\r\n　　・Flipper\r\n　　・Hyperion-Android\r\n　　・Chuck\r\n　　・Timber / Log\r\n　　・DebugDrawer 他\r\n・デバッグライブラリの内部実装\r\n　・ログの取得\r\n　・DBの値の取得\r\n　・通信ログの取得\r\n　・表示要素の分析 他\r\n・デバッグツールを実装する\r\n　・デバッグ画面のデザイン\r\n　・画面の呼び出し方法 他\r\n\r\nデバッグライブラリについて概観した後、その内部実装をデバッグ情報ごとに丁寧に解説していきます。そして、得られたノウハウを自作デバッグツールの実装にどう活用できるかを紹介します。\r\n\r\nセッションを通じ、デバッグツールに関する知見を詳しく紹介するとともに、内部実装を掘り下げるノウハウについても解説できればと思います。",
      "startsAt": "2020-02-20T15:00:00+09:00",
      "endsAt": "2020-02-20T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "00f8c0a3-55cc-4c3a-af45-730fe03ea6b8"
      ],
      "roomId": 11514,
      "targetAudience": "・Android developers / アプリ開発に携わる方\r\n・People who are interested in internal implementation / ライブラリの内部実装に興味のある方\r\n・People who want to develop debugging libraries / デバッグライブラリを開発されたい方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28655,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156777",
      "title": {
        "ja": "アプリの閉じ方",
        "en": "How to shutdown apps"
      },
      "description": "何ヶ月もかけて開発したアプリを、いよいよGoogle Playに公開！リリース当初は話題になるものの、次々に新しいサービスが生まれる中徐々に埋もれていってしまいます。少しの間足掻いてみますが、その努力も虚しくとうとうサービスを終了する判断が下されます。Androidアプリのエンジニアとして所属するあなたは、アプリをクローズするタスクを任されることになりました。\r\n\r\n公開したアプリをクローズすることはなかなか経験することのないタスクでありながら、一度きりで絶対に失敗できない重要なものでもあります。特に課金などお金に関わる部分で失敗すると、目も当てられない状況に陥ってしまいます。しかもアプリを公開することに関しては世の中に知見が溢れている一方で、閉じる事に関する知見はあまりありません。\r\n\r\n本セッションではサービスを終了することが決まったアプリをクローズするにあたり、\r\n- シナリオで考えるアプリの終了方法\r\n- それぞれで取れる対策と行動\r\n- アプリ終了に関する落とし穴とその回避方法\r\nについて取り上げる予定です。",
      "startsAt": "2020-02-20T15:00:00+09:00",
      "endsAt": "2020-02-20T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "24aa5e63-d4a5-4e9d-8f54-0c95e7c339e8"
      ],
      "roomId": 11515,
      "targetAudience": "- 個人でGoogle Playにサービスを公開している方\r\n- 業務でGoogle Playに公開しているサービスの運用に関わっている方\r\n- 好調なサービスの運用に関わっているけれど、一寸先は闇だと思っている方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28652,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156774",
      "title": {
        "ja": "Android開発と比べてわかるFlutter",
        "en": "Let's understand Flutter based on comparison with Android development"
      },
      "description": "Flutter 1.0が2018年末にリリースされて以来、Flutter開発に関する話題を目にする事が日に日に増えているように感じます。\r\n普段Androidアプリを開発しているエンジニアの中にも、そろそろFlutterを触ってみたい！と考えている方は多いのではないでしょうか。\r\nしかしFlutterによるアプリ開発には、Androidアプリ開発と比べて勝手が違う部分が多々あります。\r\nAndroidでは出来てたコレ、Flutterではどうやるんだろう？\r\nAndroidでは面倒だったコレ、Flutterだったら簡単に出来たりしないかな？\r\nこのセッションでは皆さんのそんな疑問にお答えします。\r\n\r\n予定している内容\r\n* 開発環境・ツールの違いについて\r\n* 開発言語の違いについて\r\n* フレームワークの構造の違いについて\r\n* ライフサイクルの違いについて\r\n* Jetpack Architecture Componentsを使用して作成したAndroidアプリと、\r\n  Flutterの代表的なアーキテクチャで作成したアプリの違いについて\r\n* Androidでよく使われるUIコンポーネントはFlutterでどうなっているか\r\n* Android特有の機能を実装する際の違いについて\r\n* Androidアプリ開発とFlutterによるアプリ開発のコストの違いについての考察\r\n",
      "startsAt": "2020-02-20T15:00:00+09:00",
      "endsAt": "2020-02-20T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "8cf3b542-a0b4-451a-9326-3bd37bf69532"
      ],
      "roomId": 11516,
      "targetAudience": "Androidアプリ開発の基礎的な知識を持っている方\r\nFlutterに興味のある方\r\n",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28656,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "153542",
      "title": {
        "ja": "Data Bindingのイロハ",
        "en": "The ABC of Data Binding"
      },
      "description": "Android開発で最も基本となるデザインパターンはMVCパターンです。\r\nMVCパターンではViewの操作をActivityが行うため、Activityのコードが煩雑になり保守性が下がる要因となっていました。\r\nData Bindingを使うとViewの振る舞いはView自身で決めるためActivityのコード量が減り保守性が上がります。\r\n複雑になっていくUIの状態をより管理しやすくできるData Bindingを積極的に取り入れていきましょう。\r\n\r\n本セッションではData Bindingの基礎から最新の機能まで、幅広く紹介します。\r\n初心者歓迎です。\r\n\r\nアジェンダ(仮)\r\n- Data Bindingの基礎\r\n- Data Bindingのメリット\r\n- BaseObservable\r\n- LiveDataとData Binding\r\n- ViewModelとData Binding\r\n- Custom ViewとData Binding\r\n- 実践小技集\r\n- Android Studio 3.6に追加されるView Bindingについて\r\n- (仮) Jetpack ComposeとData Binding",
      "startsAt": "2020-02-20T16:00:00+09:00",
      "endsAt": "2020-02-20T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "551effb7-8f0f-41e8-ab7c-99b4a299324f"
      ],
      "roomId": 11511,
      "targetAudience": "- Data Bindingを使ってみたい方\r\n- Data Bindingの理解を深めたい方\r\n- サンプルではなく実際のプロダクションでどのようにData Bindingを使っているのか興味がある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156785",
      "title": {
        "ja": "Android Studio Design Tools",
        "en": "Android Studio Design Tools"
      },
      "description": "In the last few releases of Android Studio, we added many new design tools, like navigation editor, resource manager and more. In this session, we will take a look at those new tools, covering how to use them to build your applications more efficiently. Specifically, we will cover the new resource manager, the layout editor & nav editor as well as the upcoming motion editor in Android Studio 3.6+ and how those can be used to develop Android applications faster.",
      "startsAt": "2020-02-20T16:00:00+09:00",
      "endsAt": "2020-02-20T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "a7d0fcec-49dd-4e65-bcb7-ad3d1901928c",
        "e3267cf5-b1a7-4ce7-a4b3-223925f21316",
        "ed1e4545-4547-4697-9515-28f4174f09dd"
      ],
      "roomId": 11512,
      "targetAudience": "Android Developers interested in UI design / programming",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28655,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "155329",
      "title": {
        "ja": "アプリのアップデート浸透率を上げろ！ ~in-app updatesを実戦投入して見えてきたもの~",
        "en": "Let's improve the update rate! - see findings through in-app updates on production"
      },
      "description": "in-app updates（以下IAU）は、ストアにアプリの新バージョンが存在する際にアップデートを促し、Play Storeアプリを開かずにその場でアップデートができる機能です。\r\n2018年11月ののAndroid Dev Summitでβ版が発表され、2019年5月のGoogle I/Oにて正式版がリリースになりました。\r\n\r\nIAUが発表される以前にも各開発者が自前でアップデート訴求機能を実装することはありましたが、IAUによりそれがより手軽に実現出来るようになりました。\r\n\r\n本セッションでは実プロダクトにIAUを実戦投入した結果に基づき、その導入方法、落とし穴だらけの検証方法や、運用してみて分かったTipsなどを赤裸々にお話します。\r\n聴講後に参加者が一人でも多くIAUを試したくなっていることを本セッションのゴールとします。\r\n\r\n\r\nお話すること（予定）\r\n・in-app updatesについて\r\n・導入方法\r\n・検証の方法と落とし穴\r\n・実装時の落とし穴\r\n・実践投入した成果と課題\r\n・現時点でのベストプラクティス",
      "startsAt": "2020-02-20T16:00:00+09:00",
      "endsAt": "2020-02-20T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "b5fe5fdd-6fe4-41cc-b2e7-0ea294d9157f"
      ],
      "roomId": 11513,
      "targetAudience": "・in-app updates を知りたい方\r\n・in-app updates を実戦投入したい方\r\n・in-app updates を実践投入した結果を知りたい方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156056",
      "title": {
        "ja": "一人開発でつまづいた時の処方箋",
        "en": "Recipes when you are in a trouble with one-man development"
      },
      "description": "一人で開発していると一番辛いことは、やはり相談する相手がいないことではないでしょうか。壁にぶち当たった時に、そもそも何を調べればいいのかすら分からないこともあります。\r\n特定のエラーを解決する方法や、ワークアラウンドなどは他の開発者が記事として残してくれていることは多いですが、問題解決までのフローについては、経験則によるところが大きく解説記事などはあまり多くありません。\r\n\r\nこのセッションでは、そのような問題解決のフローにフォーカスして、一人開発での生産性向上をサポートする内容を紹介していきます。\r\n\r\n主な処方箋\r\n- ビルドが通らないときの処方箋\r\n- アプリがクラッシュするときの処方箋\r\n- アプリの評価が悪いときの処方箋\r\n- などなど",
      "startsAt": "2020-02-20T16:00:00+09:00",
      "endsAt": "2020-02-20T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "6802fed3-c3b6-4293-88d9-9da37f7db9d0"
      ],
      "roomId": 11514,
      "targetAudience": "- 仕事もしくはプライベートで一人で開発することがある人\r\n- 一人で開発していてデバッグ時によく詰まってしまう人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28652,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156662",
      "title": {
        "ja": "Arrowの歩き方",
        "en": "Walk through Arrow"
      },
      "description": "Kotlinで関数型ProgrammingをするためのLibrary、ArrowというLibraryをご存知でしょうか？\r\n\r\n本セッションではそんなArrowについて興味のある人に\r\nArrowがどのようなライブラリなのかを知ってもらうことを前提としたセッションになります。\r\n\r\n具体的には\r\n\r\n* Arrowの代表的なDataType\r\n* 便利なTypeClassとKotlinでの表現力の限界、そしてArrowにおけるその突破方法\r\n* Monadは難しいという思いの払拭\r\n* Tagless Finalによる抽象化(※)\r\n* ArrowFxとCoroutines(※)\r\n\r\nといった、Arrowの代表的な機能の紹介やその利用方法を\r\nそして\r\n\r\n* そもそもArrowをAndroidに適応することに関して\r\n\r\nを具体的なコードを踏まえて紹介したいと思います。\r\n\r\n\r\n※は聴衆の人の事前知識で調整",
      "startsAt": "2020-02-20T16:00:00+09:00",
      "endsAt": "2020-02-20T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "3025abd9-ecb6-486b-85ee-e6305aa2ec5c"
      ],
      "roomId": 11515,
      "targetAudience": "Arrowに興味を持っている人\r\nNullableは便利なんだけど、もっと他の表現がないか探している人\r\n似たようなコードに対する、これまでとは違った共通化方法を知りたい人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156066",
      "title": {
        "ja": "Androidで音声合成(TTS)をフル活用するための知識と実践事例",
        "en": "The knowledge and practices to make the best use of TTS on Android apps"
      },
      "description": "近年、Siriなどの音声アシスタントアプリやAmazon Echo、Google Home といったスマートスピーカーなど、「音声」領域のサービスが立て続けに登場し、注目を集めています。音声領域のサービスの肝になる技術の１つが、人間の声を人工的に作り上げ読み上げる技術、「音声合成(Text To Speech = TTS)」です。私たちが利用可能な音声合成技術は急速に精度を上げており、様々なサービスに質の高い「声」を組み込むことが可能になっています。\r\n\r\n本セッションでは、Androidで音声合成を活用するための周辺知識から具体的な実装方法について紹介をします。実装方法の紹介では、基本的な音声合成の利用方法や音声とUIを連動させる方法、アプリのアクセシビリティを向上させるための利用方法などについて紹介する予定です。\r\n\r\n\r\n【前半: 周辺知識編】\r\n・音声合成の概要\r\n・アプリで利用可能な音声合成の紹介\r\n　　- Android標準の音声合成を利用する場合\r\n　　- 外部サービスを利用した音声合成を利用する場合\r\n　　- 独自で音声合成を利用する場合\r\n・導入事例の紹介\r\n\r\n【後半: 実践編】\r\n・音声合成の実装方法の紹介 (予定)\r\n　　- 音声合成の基本的な実装方法\r\n　　- 音声合成とUIを連動させる方法\r\n　　　...etc\r\n・音声合成を利用したアプリのアクセシビリティ向上\r\n",
      "startsAt": "2020-02-20T16:00:00+09:00",
      "endsAt": "2020-02-20T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "5208d614-cd46-4599-b7a5-cd3fd5d7c592"
      ],
      "roomId": 11516,
      "targetAudience": "- Androidで音声合成の利用に興味がある人\r\n- Androidで音声合成の利用を検討している人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "b582a97d-7b8a-4ad8-8ebf-2c5b92e8ca39",
      "title": {
        "ja": "Codelabs",
        "en": "Codelabs"
      },
      "description": null,
      "startsAt": "2020-02-20T16:00:00+09:00",
      "endsAt": "2020-02-20T17:00:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 12203,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 60,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156876",
      "title": {
        "ja": "動かす",
        "en": "MOTION"
      },
      "description": "Android には様々なアニメーション API があります。アプリにアニメーションを実装するとき、どの API を使えばいいか迷った経験はありませんか。適切な API を使い分ける判断基準が分からず何事も一つのやり方で済ませてしまってはいませんか。\r\n\r\nAndroid には ObjectAnimator などの基本的な API から、それらの API に基づいて実装される Transition や ItemAnimator などの高度な API まで、数多くのアニメーション API が用意されています。中にはもはや忘れてしまって構わない API もあれば、場合によって使い分けていく API もあります。このセッションの前半で、まずそれらを整理します。\r\n\r\nさらに、アニメーションに関係する View の性質を解説します。アプリごとに洗練されたアニメーションを作り込むためにはカスタムの実装が必要になることもあります。スムーズなアニメーションを作り込むためには基本を押さえておくことが大切です。\r\n\r\n最後に、マテリアル デザインの Motion の章 (material.io/design/motion) にあるようなアニメーションの具体的なパターンを題材として、どの API をどのように使うべきか考えていきましょう。\r\n\r\n- 様々なアニメーション API\r\n  - もはや忘れて構わないアニメーション API\r\n  - 2020 年に押さえておくべきアニメーション API\r\n- アニメーションを実装する上で押さえておきたい View の性質\r\n  - レイアウトについて\r\n  - View の位置について\r\n- マテリアル デザインの動き\r\n  - Dissolve と Fade through\r\n  - リスト\r\n    - Stagger\r\n    - Oscillation\r\n  - ナビゲーション\r\n    - 共通要素\r\n    - コンテナーのアニメーション",
      "startsAt": "2020-02-20T17:00:00+09:00",
      "endsAt": "2020-02-20T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "1dcd8d28-7ad2-4c6b-aa2a-92f3e3b83556"
      ],
      "roomId": 11511,
      "targetAudience": "Android でアニメーションを作りたい人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156546",
      "title": {
        "ja": "Privacy First Machine Learning",
        "en": "Privacy First Machine Learning"
      },
      "description": "How to maintain privacy of app users when developing machine learning & artificial intelligent solutions, including how to personalise the app experience with machine learning, develop performant and scalable machine learning on the cloud, and use that effectively on mobile devices without compromising the privacy of the app users.\r\n\r\nThis also includes security concepts in machine learning, and describes how to attain portability across platforms by re-utilising the same concepts and architectures.",
      "startsAt": "2020-02-20T17:00:00+09:00",
      "endsAt": "2020-02-20T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "792a7a14-72c5-472d-bec3-e65ba327b760"
      ],
      "roomId": 11512,
      "targetAudience": "Understanding of basic app development is all that's needed. This will take app developers with no prior machine learning experience and take them through the journey.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": true
    },
    {
      "id": "149804",
      "title": {
        "ja": "Sneaking inside Kotlin features",
        "en": "Sneaking inside Kotlin features"
      },
      "description": "Kotlin has many language features even though none of them are supported by JVM or Android ART. This talk will go through all the language features and understand how they work internally for JVM or ART compatibility.\r\n\r\nFor example:\r\n1. Do all know, how features like default arguments and default methods (in interface) works?\r\n2. Do all know, that switch statement can only work with integers? Then how when expression works with almost all data types?\r\n3. Do all know, how inline classes works?\r\n4. And many more features.\r\n\r\nMany developers in kotlin community uses all these APIs without knowing how they work. After this talk, they will have a good understanding of how these features work internally which obviously make them a better programmer.",
      "startsAt": "2020-02-20T17:00:00+09:00",
      "endsAt": "2020-02-20T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "e7ace08f-10bc-4e0c-a3f1-2471868b5261"
      ],
      "roomId": 11513,
      "targetAudience": "Anybody who know Kotlin and Java are intended audience.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": true
    },
    {
      "id": "156805",
      "title": {
        "ja": "これから始めるKotlin Coroutinesの導入",
        "en": "Introduce Kotlin Coroutines from now"
      },
      "description": "Kotlin Coroutinesが正式リリースされて1年が過ぎ、もはや私たちのAndroidアプリ開発には欠かせないものとなりました。\r\nコルーチンはRxJavaのようなストリームやコールバック方式と比べて非同期的な処理を直感的に記述でき、これまでRxJavaを使っていたようなユースケースをある程度置き換えられるようになっています。\r\n​\r\n​\r\n私はこの1年と少しでKotlin Coroutinesを複数のアプリに導入し既存のパラダイムの置き換えを進めてきました。\r\nKotlin Coroutinesの導入を行ったことでAndroidアプリ開発が楽になりましたが、大変になったこともありました。\r\n恐らくKotlin Coroutinesが難しく感じて導入をためらっている方もいるでしょう。\r\n​\r\nこのセッションでは、既存のAndroidアプリにKotlin Coroutinesを導入したことによってAndroidアプリ開発がどう変わったか、\r\nKotlin Coroutinesの導入を行ったことで困ったことをどう解決したかなどを紹介します。\r\n皆様のKotlin Coroutinesの導入の参考になれば幸いです。\r\n​\r\n- RxJavaを使って書かれたコードを置き換える\r\n- RxJavaを使った世界観にKotlin Coroutinesを混ぜる\r\n- Retrofitを用いたHTTP通信をKotlin Coroutinesに対応\r\n- RoomをKotlin Coroutinesで利用する\r\n- Kotlin CoroutinesとView層との組み合わせ\r\n- Kotlin CoroutinesとLiveDataの使い分け\r\n- Kotlin CoroutinesをViewModelで活用する\r\n- Kotlin Coroutinesとテスト\r\n- 例外処理\r\n- キャンセル可能に作る",
      "startsAt": "2020-02-20T17:00:00+09:00",
      "endsAt": "2020-02-20T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "e12d8b0a-762e-4c6e-8c4b-318c434a82d5"
      ],
      "roomId": 11514,
      "targetAudience": "- 既存のアプリでRxJavaを利用していてKotlin Coroutinesの導入を検討している人\r\n- Kotlin Coroutinesを導入してみたがうまく活用できていなくて困っている人\r\n- Kotlin Coroutinesを使ったテストに困っている人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "b2622329-df1f-4382-8fe0-7e84ec45458e",
      "title": {
        "ja": "Party",
        "en": "Party"
      },
      "description": null,
      "startsAt": "2020-02-20T18:00:00+09:00",
      "endsAt": "2020-02-20T20:00:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 11511,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 120,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "154105",
      "title": {
        "ja": "Android Then & Now",
        "en": "Android Then & Now"
      },
      "description": "Android development has come a long way since 1.0. How did we get here? And now that we're here, what do we do next?\r\nThis talk will go over the landscape of Android development through the years and the releases, and talk about what Android platform priorities are now, and what that means for developers.",
      "startsAt": "2020-02-21T10:00:00+09:00",
      "endsAt": "2020-02-21T10:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "152f613f-841b-407d-9a65-3703ec2dfae2",
        "d4d861f4-0319-49d3-8ca1-7778cd2fb6dc"
      ],
      "roomId": 11511,
      "targetAudience": "All Android app developers.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28651,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156065",
      "title": {
        "ja": "世界一わかりやすいClean Architecture",
        "en": "The easiest guide in the world to learning Clean Architecture"
      },
      "description": "近年、DDDやClean Architectureといったアプリケーションのアーキテクチャについての関心が非常に高まっています。\r\nClean Architectureは、それらの著名なアーキテクチャの中でも特に有用で汎用性の高いアーキテクチャのひとつです。\r\n\r\nしかし反面Clean Architectureは、その本質を誤解されいるケースも多いようです。\r\nかの有名な同心円状のモデルは、けして「あの図に登場する要素の通りに作りましょう」という意味ではありません。\r\n\r\nそこで本セッションではClean Architectureの「誤解されがちな二つのことと、真に理解すべき三つのこと」についてお話し、Clean Architectureの本質を理解いただこうと思います。\r\n\r\n内容はこちらがベースとなりますが、今後ブラッシュアップの予定です。\r\nhttps://www.nuits.jp/entry/easiest-clean-architecture-2019-09\r\n\r\nアジェンダ\r\n- 誤解されがちな二つとその解説\r\n　- 「あの同心円状のモデルのレイヤーや要素の通り実装せよ」というわけではないこと\r\n　- 「データや処理の流れを一方通行にせよ」というわけでもないこと\r\n- 真に理解すべき三つのこと\r\n　- 依存性は、より上位レベルの方針にのみ向けよ\r\n　- 制御の流れと依存方向は分離・コントロールせよ\r\n　- 「上位レベルの方針」とはなにか\r\n- 上記を理解いただくためのサンプルの解説\r\n　- あの同心円状のモデルの登場要素のままだがクリーンではない設計と実装\r\n　-　上記をクリーンにリファクタリングした設計と実装",
      "startsAt": "2020-02-21T10:00:00+09:00",
      "endsAt": "2020-02-21T10:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "72b4cb54-58f4-4dfe-b75c-c2faff1f07bd"
      ],
      "roomId": 11512,
      "targetAudience": "Clean Architectureをこれから導入したいと考えている人\r\nClean Architectureに今一つしっくりこないと考えている人\r\nそういった方々に、本当に重要なポイントに絞って説明いたします。",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "150650",
      "title": {
        "ja": "Making Apps ready for scoped storage coming in Android Q",
        "en": "Making Apps ready for scoped storage coming in Android Q"
      },
      "description": "When Android Q Beta 1 was released, the main surprising change for developers  was what Google calls “scoped storage”. The main two reasons for making this change: Security & to reduce leftover \"app clutter\". In brief, our ability to work\r\nwith files and filesystems will get substantially curtailed, even for apps with a targetSdkVersion of 28 or lower. \r\n\r\nHowever in subsequent Android Q beta releases 4 & 5, it has now been finalised that Apps can either have normal or legacy storage.\r\nWith legacy storage, everything behaves as it did in Android 4.4 through 9.0. Without legacy storage, apps still can use getExternalFilesDir() and similar directories without permissions. However, the rest of external storage appears to be inaccessible via filesystem APIs. As per documentation : \r\n“An app that has a filtered view always has read/write access to the files that it creates, both inside and outside its app-specific directory”\r\nThe idea is that we should start adapting now. For some apps, switching to the Storage Access Framework                                 (e.g. ACTION_OPEN_DOCUMENT) will be easy. For some apps, it will be painful. We must not wait until 2020, as mentioned in documentation. We should Start migrating our apps now to use the alternative approaches. Through this talk we will try to assess different approaches to be adopted for the changes coming via scoped storage and making sure that apps continue to work seamlessly. \r\n",
      "startsAt": "2020-02-21T10:00:00+09:00",
      "endsAt": "2020-02-21T10:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "531ff5e0-fb19-412a-9aa6-50a0045e1cd9",
        "bfe6396b-fca5-4fca-b118-602d124a7076"
      ],
      "roomId": 11513,
      "targetAudience": "With basic Android Knowledge.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28651,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": true
    },
    {
      "id": "156246",
      "title": {
        "ja": "Androidアプリに潜む脆弱性 ~LINEの場合~",
        "en": "Potential vulnerability in Android apps in the case of LINE"
      },
      "description": "Androidアプリケーションのセキュリティと聞かれて皆さんは何を思い浮かべるでしょうか？\r\nデータの暗号化・ProGuard/R8といったコード難読化・不正購入、はたまたAndroid本体のセキュリティなど様々な要素が挙がるでしょう。\r\n今回はその中でも特に「脆弱性」にフォーカスしてみます。\r\n\r\nAndroid OSやライブラリに含まれていることもありますが、何気なく書いたあなたのコードが原因になっているかもしれません。\r\nついつい書いてしまいがちなコードの中に脆弱性につながる書き方が潜んでいることは多々としてあります。\r\n実際に、LINEのAndroidアプリでも書かれたコード上に脆弱性があり、脆弱性報奨金制度「LINE Security Bug Bounty Program」での報告や内部チェックで発見・修正をしてきました。\r\n\r\nでは、実際にどのような脆弱性がAndroidアプリに混入してしまったのでしょうか？\r\nこのセッションでは、LINEのAndroidアプリに含まれていた脆弱性の紹介を行います。また、それらの脆弱性に対してどのような対策を講じたか紹介を行います。",
      "startsAt": "2020-02-21T10:00:00+09:00",
      "endsAt": "2020-02-21T10:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "ac65e990-2298-41b5-ab5a-49b366a23928"
      ],
      "roomId": 11514,
      "targetAudience": "Androidアプリの脆弱性に興味のある方\r\n",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28647,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "155128",
      "title": {
        "ja": "Deep dive into Android Text",
        "en": "Deep dive into Android Text"
      },
      "description": "TextViewは最も基本的なwidgetですが、ほとんどの方にとってその中身はブラックボックスなのではないでしょうか？　実際、文字列がsetTextでセットされてから、最終的に画面に出るまでには非常に多くの処理がなされています。その一つ一つを見ていくと、とてもではないですが40分では終わりませんので、Android(Java)が公開しているAPIを元に、どの様に使うのか、何のためにあるのか、中でどの様な処理が行われているかを解説していきたいと思います。具体的には以下のトピックについて解説していきます。（時間が足りない場合は絞ります）\r\n\r\n- UnicodeのBiDirectional Algorithm\r\n- 複数のスタイルがついたテキストの描画\r\n- システムのフォント選択アルゴリズム\r\n- 改行アルゴリズム\r\n\r\nなどなど\r\n\r\n\r\nTextView is one of the most basic widgets but that would be a black-box for most developers. Actually, there are lots of processes from calling setText to the final graphical output on the screen. There are too many topics for this and unable to arrange all of them in 40min, so I will talk about the Android (or Java) APIs, especially how to use, how it works and why it is necessary. Here is the concrete topics: (I may drop some of them for meeting 40 min talk)\r\n\r\n- Unicode BiDirectional Algorithm\r\n- Multiple style text\r\n- Font selection algorithm\r\n- Line breaking algorithm\r\n\r\netc\r\n",
      "startsAt": "2020-02-21T11:00:00+09:00",
      "endsAt": "2020-02-21T11:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "ad44e7e5-5ba9-427c-a30f-7f447969f213"
      ],
      "roomId": 11511,
      "targetAudience": "AndroidでTextViewを使ったことがある方。テキスト描画処理全般に興味がある方。\r\n\r\nAny audience who has experience of using TextView or are interested in text rendering .",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156408",
      "title": {
        "ja": "Master of Dagger",
        "en": "Master of Dagger"
      },
      "description": "In recent Android application development, using DI framework has become commonplace.\r\nDagger, Java's DI framework, is often used in Android application development, and is also used in the Google I/O official application and Android Architecture Components samples.\r\n\r\nIn this session, I will explain the mechanism of Dagger from the very beginning for people who use Dagger for some reason and those who use Dagger for watching.\r\nIf you listen to this session, you will understand Dagger completely.\r\n\r\nThe content of this session is based on a book called “Master of Dagger” written by the speaker.\r\n\r\nTalking in this session\r\n- What is DI\r\n- binding, object graph, and Component\r\n- How to separate Modules\r\n- Cases where Subcomponent is required\r\n- How Scope can help\r\n- What the Dagger Android Support Library is doing\r\netc",
      "startsAt": "2020-02-21T11:00:00+09:00",
      "endsAt": "2020-02-21T11:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "580fb501-aece-4bf4-b755-32fda033b3bd"
      ],
      "roomId": 11512,
      "targetAudience": "- People who have never used Dagger.\r\n- People who use Dagger somehow.\r\n- People who don't know when Scope and Subcomponent are necessary and useful.\r\n- People who don't know what Dagger Android Support Library is doing.\r\n",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "セッションの部屋と時間が変更になりました。",
        "en": "This session room and time has been changed."
      },
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "155374",
      "title": {
        "ja": "ptraceシステムコールから見える世界　Androidアプリでのセキュリティ攻防戦",
        "en": "The world that ptrace system call reveals - Android app security war."
      },
      "description": "このセッションではgdb等のデバッガ、Frida等の動的解析ツールの技術の根幹部分であるptraceシステムコールについて扱います。\r\nptraceシステムコールを利用すると、他アプリケーションのプロセスメモリ空間やレジスタにアクセスが可能になり処理を解析することが可能となります。\r\n正当に利用すれば便利なデバッガとして機能しますが、悪用するとゲームでの不正行為やアプリケーションのクラックにも利用できてしまいます。\r\n今回は他アプリケーションのプロセスメモリを読み書きする方法の説明から始まり、ブレークポイントを設置しての簡易的なデバッガを実装します。\r\nこういった原理的な考察を通じどのようにアプリケーションを解析から守るか、セキュアな状態に保つか考察していきます。\r\n\r\n講演者の所属企業はゲームセキュリティを専門とする企業であり、業務としてソーシャルゲームのセキュリティ診断等を行っています。\r\n講演では実演する解析される側の題材として、自作のゲームを用いて分かりやすく解説を行います。\r\n本講演でのセキュリティの攻防戦から、セキュリティ対策の重要性や防御手法の知見を深めるきっかけとなれば幸いです。\r\n",
      "startsAt": "2020-02-21T11:00:00+09:00",
      "endsAt": "2020-02-21T11:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "ce9bbdc0-5bb5-4d73-aa4e-2ac054f601a0"
      ],
      "roomId": 11513,
      "targetAudience": "情報セキュリティ技術に興味がある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28647,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "153684",
      "title": {
        "ja": "俺が今までやらかした失敗事例、やらかしそうになったヒヤリハット事例を紹介する",
        "en": "Case studies : incidents and/or near accidents based on my experiences"
      },
      "description": "Androidアプリ開発を長くやっていると、当然、とんでもない失敗をたくさんやらかします。\r\n\r\nリリースの翌日大炎上。クラッシュ数グラフがうなぎ登り。低評価レビューの嵐。そこまで行かずとも、危うくとんでもないバグ付きアプリをリリースするところだったぜ、というヒヤリハット事例もたくさん。皆さんもそんな経験ありますよね。\r\nこのセッションでは、そんなAndroidアプリを開発していると遭遇する可能性のある失敗事例、失敗に繋がってしまった間違った設計、それに対してどのように対策をしたのか等を私の経験から紹介していこうと思います。\r\n「あるある」「すでにやらかしたわ」と盛り上がりつつ「え、そんなことあるのか」「なるほどそうすればいいのか」という発見が一つでもあればと思います。",
      "startsAt": "2020-02-21T11:00:00+09:00",
      "endsAt": "2020-02-21T11:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "c27d048c-8c02-4de5-84fb-606de1cc2447"
      ],
      "roomId": 11514,
      "targetAudience": "・Androidアプリ開発者の方\r\n・Androidアプリ開発をこれから始めようと思っている人\r\n・Androidアプリ開発プロジェクトのマネージャーの方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156932",
      "title": {
        "ja": "詳解 WindowInsets",
        "en": "Deep dive into WindowInsets"
      },
      "description": "昨今の Android では, ノッチ付きのデバイスによって Navigation Bar の幅が増えたり, Gesture Navigation によって Status Bar がほとんどなくなったことにより, より画面全体を活用したUI(Edge-to-Edge)が求められるようになりました. これは, 没入感を与えるという点でメリットがある一方で, それに対応するには WindowInsets への理解が欠かせません. 本セッションでは, WindowInsets の基本的な使い方と, 画面全体を活用したUI(Edge-to-Edge)の実践を重点的に解説したあと, さらに先へ行くための, Gesture Navigation の基礎や, 対応方法をかんたんに紹介します.",
      "startsAt": "2020-02-21T11:00:00+09:00",
      "endsAt": "2020-02-21T11:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "b05febee-ba2d-422b-806c-e07dcb9f4600"
      ],
      "roomId": 11515,
      "targetAudience": "WindowInsets や Gesture Navigation について知りたい方\r\nアプリUIの上下が黒いままのアプリを作っている方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "e3319b2e-3f2f-4965-85a4-9c97a24f4e2d",
      "title": {
        "ja": "Lunch",
        "en": "Lunch"
      },
      "description": null,
      "startsAt": "2020-02-21T12:00:00+09:00",
      "endsAt": "2020-02-21T13:00:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 12203,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 60,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156788",
      "title": {
        "ja": "Dissecting Kotlin: Conventions",
        "en": "Dissecting Kotlin: Conventions"
      },
      "description": "Learning to write idiomatic Kotlin is a process, but guidance and insight can be found in Kotlin itself. By examining how language features are designed and implemented, we can perhaps better understand the thinking behind Kotlin and better answer \"What is idiomatic Kotlin?\"\r\n\r\nIn this session, we'll examine conventions: a mechanism by which several Kotlin language features are accessed. We talk about what conventions are, run through live-coded examples of several of convention-based features, look at some underlying bytecode, and discuss how conventions reflect the principles and values of Kotlin and the Kotlin team.\r\n\r\nBy understanding how and why conventions work as they do, we can better follow those underlying Kotlin principles when both using those features and when writing our own code and tools.",
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "2d41d3fb-3321-4b47-a401-ae84a5de2423"
      ],
      "roomId": 11511,
      "targetAudience": "Beginner to intermediate Kotlin users",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "155449",
      "title": {
        "ja": "残り15%のユーザーにリーチするためのAccessibility",
        "en": "Accessibility to reach the remaining 15% of users"
      },
      "description": "アクセシビリティ、という言葉を耳にした方は多いかと思います。\r\n多くの方はアクセシビリティの機能は障害者向けである、自分のアプリには不要である、\r\nと、特別な対応を行わないかもしれません。\r\nしかし、アクセシビリティを考えない実装を行うことによって、\r\nアクセシビリティが必要なユーザーにアプリを使ってもらえないどころか、\r\n意図しない動作を引き起こす要因にもなりえます。\r\n\r\n毎年Google I/Oではアクセシビリティに関するセッションが複数行われ、\r\n私たちはいつでもアクセシビリティユーザーである、\r\nsituational disability(状況的障害)が起こる、という話があり、\r\n正しくアクセシビリティが実装されると全ての人にメリットがあるものになります。\r\n\r\n本セッションでは主にTalkBackなどのアクセシビリティの実装方法や、アクセシビリティが正しくされない場合のリスク、\r\n実際のアクセシビリティ機能の使い方やAPIの説明やアクセシビリティの自動テストなど、\r\n今までリーチしていなかったユーザーにリーチするようになるアプリを開発するためのTIPSを説明します。",
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "6988a352-4da6-4eb8-b0d8-1050523620de"
      ],
      "roomId": 11512,
      "targetAudience": "全てのAndroid開発者",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "153109",
      "title": {
        "ja": "Let's Draw! ✏️ Using the Android Canvas to build custom components",
        "en": "Let's Draw! ✏️ Using the Android Canvas to build custom components"
      },
      "description": "Have you ever wanted to make your app stand out from the rest? Or has your UI designer presented a custom view to you and you have no idea how to go about making it? Don't panic, you can draw it yourself!\r\n\r\nIn this talk, we will go back to the basics of how to draw onto a Canvas to create your own custom view. We will also cover some of the more advanced things you can do with the Canvas, such as using BitmapShaders and Matrices to achieve magical effects within your app. Lastly, we will look at different ways in which you can animate your own custom views with Canvas Drawing.",
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "75244713-51c4-4977-82ea-25a4d340af26"
      ],
      "roomId": 11513,
      "targetAudience": "Android Developers who want to start drawing custom views.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28651,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": true
    },
    {
      "id": "156729",
      "title": {
        "ja": "持続的なアプリ開発のためのDXを支える技術",
        "en": "Technology to compose DX of continuous application development"
      },
      "description": "Android の歴史はすでに 10 年を超え、数多のアプリケーションがストアで公開されています。これらのアプリケーションの中には、何年も継続的にバージョンアップを重ねているものもたくさんあります。\r\n\r\nこのセッションでは、このような持続的なアプリケーション開発・リリースをうまく回す秘訣として DX という言葉をとらえ、アーキテクチャやテストのほか、日々の開発に関わるワークフローをメンテナンスするための考え方や手立てとして、モバイル CI や Android 向け各種ツールキットの導入と効率化、Gradle をベースにした独自タスク開発の方法などを紹介します。",
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "33ff8262-ff9a-4d77-be1f-e288d62ba93f"
      ],
      "roomId": 11514,
      "targetAudience": "Android アプリの開発からリリース、運用までを一通り経験した中級者",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28653,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156238",
      "title": {
        "ja": "Navigation Componentを実戦投入した際の感動、便利さ、そしてつまづき",
        "en": "Impressions, convenience, and risks of Navigation Component on production environment based on my experience"
      },
      "description": "Android Architecture ComponentsのNavigation Componentは、Fragment間の遷移をよりシンプルに実装できるようにしたライブラリです。\r\n既存のFragment遷移処理はFragmentManagerによるトランザクション処理を、バックスタックなども考慮しながらコード上で書いていました。Navigationはそれらのトランザクション処理をラップしているので、利用することでコードの簡略化が可能となります。それだけでなく、NavigationGraphと呼ばれるxmlによってFragmentの遷移図をグラフィカルに作成/表示することにより、画面遷移をよりわかりやすく実装することができる画期的なコンポーネントです。argumentsの型安全化(Safe Args)、ディープリンクによる遷移などの便利な機能も含まれています。\r\n\r\n本セッションでは、スタディプラスアプリにおいてNavigation導入を行なった際に得られた知見と、それを基にプロジェクトに導入する際に考慮したいことなどを紹介します。また、内部実装をもとに、実際に内部でNavigationが行なっていることも紹介したいと思っております。本セッションが開発者の方々のNavigation導入のきっかけになれば幸いです。\r\n内容としては以下のようなものを予定しております。\r\n- 基本的な遷移の実装について\r\n    - NavHostFragmentとFragmentContainerView\r\n    - Fragmentの遷移とNavigator\r\n    - DialogFragment/BottomSheetDialogFragmentの遷移とNavigator\r\n    - BottomNavigationの連携\r\n    - ディープリンクに依る遷移\r\n    - NavigationGraphのネスト\r\n    - 遷移アニメーションの指定と注意点\r\n- Fragment間のデータ受け渡しについて\r\n    - Safe Args機能\r\n    - ActivityViewModel、NavGraphViewModelに依るデータ受け渡し\r\n    - Safe ArgsとNavGraphViewModelの併用時の注意点\r\n- バックキー制御方法について\r\n    - Fragment単位で制御する\r\n    - Activity全体で制御する\r\n- 最初に表示されるFragmentの制御について\r\n    - NavigationGraphごと分ける\r\n    - NavigationGraphはそのままで開始Fragmentだけ変更する\r\n- 利用する上で起こりうるクラッシュの原因と対応方法について\r\n- CustomNavigatorの作成について\r\n- 導入時の設計パターンについて\r\n    - アプリ全体を1Activityにまとめる場合\r\n    - 1機能ごとに1Activityにまとめる場合",
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "b623d9e0-8058-4f2d-a689-fd7130f7cfd5",
        "da1a5096-9f48-4658-a190-b03b6070a431"
      ],
      "roomId": 11515,
      "targetAudience": "・AAC Navigationに興味がある方\r\n・AAC Navigationを実際にプロダクトコードに組み込もうと考えている方\r\n・AAC Navigationの内部実装に興味がある方\r\n・Fragment間の遷移とデータの渡し方に苦しんでいる方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156374",
      "title": {
        "ja": "秒間30個のデータを捌くリアルタイムチャートの実装",
        "en": "How to implement real-time charts that can process 30 elements per sec"
      },
      "description": "昨今、Androidアプリでのチャート表示はMPAndroidChartというOSSライブラリをよく使われているのを目にします。\r\nこのライブラリは様々な種類のチャートを表示することができ、非常に便利なライブラリなのですが、動的・リアルタイムでのデータ追加は強引に行うことはできますが、ライブラリ公式ではサポートされていません。\r\n\r\n今回 bitFlyer の主力機能である『プロ向け取引ツール bitFlyer Lightning』のAndroidネイティブ版において、秒間最大30個超のデータを受け取って処理しリアルタイムに表示するキャンドルスティックチャートを用意する場面に出会いました。\r\nMPAndroidChartを用いて強引にリアルタイムに見えるような実装を行ってもパフォーマンスがあまり良くないチャートになってしまいました。\r\n\r\nそのため、今回はOSSライブラリを使わずに、大量のデータをリアルタイムに受け取り、処理を行い、描画するキャンドルスティックチャートを自前で実装し実現しました。\r\n\r\n本セッションでは秒間30個超のような高速でストリームに流れてくるデータを受け取り処理を行い、リアルタイムに表示を更新するキャンドルスティックチャートを実装する手順、中身、チャートに用いるデータの管理方法、Tipsなどを1ステップずつ解説します。\r\n\r\n<予定している内容>\r\n- チャートの基本\r\n- Androidアプリにおけるチャートを表示する際のソリューション\r\n- スクロール、拡大縮小、長押しの取得が可能なチャートの実装方法\r\n- 高速に追加されるチャートに表示するデータの管理方法\r\n- 実装するにあたっての罠やTips\r\n\r\n※ 時間があれば、応用として非リアルタイムラインチャートについての実装にも軽く触れる予定です。\r\n",
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "07f3973b-befc-4cbe-9faa-6fad659ef9d9"
      ],
      "roomId": 11516,
      "targetAudience": "Androidにおけるチャートの表示に興味がある方\r\nリアルタイムチャートの実装に興味がある方\r\n高速で追加されるデータの管理に興味がある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156578",
      "title": {
        "ja": "Androidでもビジュアルリグレッションテストをはじめよう",
        "en": "Let's start visual regression testing for Android apps"
      },
      "description": "iOSやJS界隈ではおなじみとなったビジュアルリグレッションテストが、\r\nライブラリの活用により最近Androidでも可能になってきました。\r\nビジュアルリグレッションテスト周りのAndroidライブラリ紹介や実際のテスト結果を紹介します。\r\n\r\n予定アジェンダ\r\n- ビジュアルリグレッションテストとは\r\n- Androidテストでスナップショットを撮るライブラリ紹介\r\n  - Android X Test Runner, Composer, facebook/screenshot-test\r\n    - 導入難易度について\r\n    - 実際のテストコードについて\r\n    - 長所・短所について\r\n- 差分画像を使ってデグレを検知する\r\n  - 2019年10月からAndroidでもデグレ検知が可能になった話\r\n- Firebase Test Labの活用について",
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T13:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "e5e38968-5aad-4032-8a96-04d7e624ce36"
      ],
      "roomId": 11517,
      "targetAudience": "これからAndroidでビジュアルリグレッションテストをはじめたい人\r\n過去にAndroidでビジュアルリグレッションテストをはじめようと思ったけど挫折した人\r\n",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28652,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "46c15868-2b4b-49cd-8358-b95eab4c30bb",
      "title": {
        "ja": "Meetup",
        "en": "Meetup"
      },
      "description": null,
      "startsAt": "2020-02-21T13:00:00+09:00",
      "endsAt": "2020-02-21T17:00:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 12203,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 240,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156666",
      "title": {
        "ja": "実践 Dynamic Feature Module",
        "en": "Practical Dynamic Feature Module"
      },
      "description": "Dynamic Feature Moduleの基本的なことから実装時のTipsなどを紹介します。\r\nDynamic Feature Moduleの実装はハードルが高く難しい印象があると思いますが、実際に実装した経験を元に導入の助けになるような内容を話す予定です。\r\n\r\n<予定内容>\r\n- Dynamic Feature Moduleの基本\r\n- 実装時のTipsや注意点\r\n- Dynamic Feature Module + Navigation\r\n- デバッグ方法",
      "startsAt": "2020-02-21T14:00:00+09:00",
      "endsAt": "2020-02-21T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "f4445e26-7dff-4294-9ae9-321e26e9c183"
      ],
      "roomId": 11511,
      "targetAudience": "Dynamic Feature Moduleに興味ある方\r\nマルチモジュールをある程度理解できてる方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": true
    },
    {
      "id": "156769",
      "title": {
        "ja": "Enforcing code conventions with Lint.",
        "en": "Enforcing code conventions with Lint."
      },
      "description": "It’s well known that as a team grows, communication overhead increases geometrically until it takes up most of the time of the members. Static analysis can help us reduce the time cost, streamline code review processes, as well as to keep big teams synchronized.\r\n\r\nThis session will cover how to start writing custom Lint checks introducing new APIs and caveats. Also, some examples and ideas will be shown to improve code consistency by enforcing code conventions across the team using custom lint checks.\r\n\r\nFinally, some concrete examples on how custom Lint checks are helping LINE to keep code consistency, streamline reviews, etc will be shown.",
      "startsAt": "2020-02-21T14:00:00+09:00",
      "endsAt": "2020-02-21T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "9d0e551a-69b3-4354-8264-78c974038e6b"
      ],
      "roomId": 11512,
      "targetAudience": "Anybody can attend this session, the basics will be covered. It will be particulary interesing for developers working in big teams.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28653,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156922",
      "title": {
        "ja": "APKファイルはいかにして作られるのか",
        "en": "How APK files are generated"
      },
      "description": "Androidアプリをビルドするとき、ソースファイル、リソースファイル、ライブラリがコンパイルされてDEXファイルが作られ、APKファイルが作られます。\r\nこれは公式ドキュメントにも記載がある内容です。\r\nしかし実際に自分が指定している設定がどのように影響し、R8が何をどのようにコンパイルしてAPKファイルが作られるのかイメージはできているでしょうか。\r\n\r\nこのセッションではAPK作成の流れを追いながら、そのとき内部では何が行われているのか、図を用いたり適宜コードを参照することで理解を深めていければと思います。\r\n点と点になっている知識を繋げ、ビルド周りの理解を深める一歩になれば幸いです。\r\n\r\n発表内容の予定\r\n・.apk作成の流れの概要\r\n・.classが作られるまで\r\n・.dexが作られるまで（R8まわり）\r\n・.apkが作られるまで\r\n・+a: .apkをインストールしたあと（バイトコード実行環境）",
      "startsAt": "2020-02-21T14:00:00+09:00",
      "endsAt": "2020-02-21T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "5e938594-2bf3-41f6-aa33-3fb655097b3d"
      ],
      "roomId": 11513,
      "targetAudience": "APKファイル生成の流れを知らない方\r\n公式ドキュメントの「ビルドを設定する」（https://developer.android.com/studio/build）がよくわからない方\r\nR8が実際に何をしているのか知らない方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28651,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "155877",
      "title": {
        "ja": "プロダクション開発前に知っておきたいFlutterのアプリケーションアーキテクチャ",
        "en": "Flutter application architecture that you should know before starting production development"
      },
      "description": "Google謹製のクロスプラットフォームフレームワーク Flutter の1.0が公表されて早くも1年が経ちました。\r\nFlutterは過去のDroidKaigiにおいても継続的に話題に上がっていましたが、今や知名度も高くなり、プロダクションでの使用事例も聞かれるようになりました。\r\n\r\nプロダクションで使用する際には、アプリケーションアーキテクチャをしっかりと検討する必要があります。\r\nFlutter開発においてよく見られるアーキテクチャを数例とりあげ、それぞれのメリット・デメリット、チームやプロダクトの特性に合わせた選び方について、実際にプロダクションでFlutterを採用した経験からお話をさせていただきます。\r\n\r\n内容（予定）\r\n・見通しの良いコードとは\r\n・Flutterだからアーキテクチャを考えたい理由\r\n　・Clean ArchitectureをFlutterで実現するには？\r\n　・宣言的UIこそが、ドメインロジックと表示ロジックを分離する切り札\r\n・状態管理に使用されるパターン\r\n　・StatefulWidgetに状態を管理させる\r\n　・ScopedModelパターン\r\n　・BLoCパターン\r\n・状態管理を支える技術\r\n　・ProviderなどがDIを可能にする技術\r\n　・Stream\r\n　・ChangeNotifierとWidgetのbuildタイミング問題、その回避方法",
      "startsAt": "2020-02-21T14:00:00+09:00",
      "endsAt": "2020-02-21T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "cdfa81b3-8739-4c61-8495-11976f5764e7"
      ],
      "roomId": 11514,
      "targetAudience": "・Flutterをプロダクション採用したい方\r\n・Flutterを使った開発に興味がある方\r\n・Androidアプリ開発で培ったアーキテクチャの知識がFlutter開発でも通用するのか確かめたい方\r\n",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28656,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156644",
      "title": {
        "ja": "Interactive Canvasを使ってGUIを持ったActions on Googleを作る",
        "en": "Create Actions on Google with GUI by using Interactive Canves"
      },
      "description": "AndroidやGoogle Nest Hubなど画面を持つデバイスでのActions（音声対話アプリ）にグラフィカルなユーザーインターフェイスを表示できる新機能「Interactive Canvas」が2019年8月にリリースされました。「動物しりとり」という1日に200回ほど使われている発表者のActionsに発表者がどのようにInteractive Canvasを導入してもともと音声のみだったユーザー体験を作り変えていったのか、事例を元にInteractive Canvasの使い方、作り方を解説します。",
      "startsAt": "2020-02-21T14:00:00+09:00",
      "endsAt": "2020-02-21T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "1d014c9e-6a06-4124-9af9-7df8db6e2761"
      ],
      "roomId": 11515,
      "targetAudience": "Actions on Googleを作ってAndroidやGoogle Nest Hub向けに音声アシスタントアプリをリリースしたいと思っている人が対象です。\r\nJavascriptの知識がなくても全体の流れがわかる発表内容になっていますが、Javascriptとnode.jsの知識があれば内容をより理解でき、すぐに実践に移せると思います。\r\n",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156700",
      "title": {
        "ja": "Android x SDLでクルマと繋がる",
        "en": "Connect with Car by Android × SDL"
      },
      "description": "昨年のGoogle I/OでAndroid Auto, Android Automotiveといった自動車向け環境のアップデートがありましたが, Android x 自動車のプラットフォームはそれ以外にも多く存在します.\r\nその中から, 本セッションでは最近日本国内でも名前を見かける様になってきたSDL(SmartDeviceLink)で動作するAndroidアプリ開発についての知見を共有します.\r\n\r\n内容(予定)\r\n- SDLについて説明\r\n- 車載デバイスとの接続を意識した設計\r\n- 通常のAndroidアプリ開発との違い\r\n- つらいところとそれの乗り越え方\r\nなど",
      "startsAt": "2020-02-21T14:00:00+09:00",
      "endsAt": "2020-02-21T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "3b564ac7-e550-4de8-9292-3173ce72c116"
      ],
      "roomId": 11516,
      "targetAudience": "- 自動車というIoTデバイスと繋がってみたい方.\r\n- 外部デバイスを含んだシステムのアプリ設計に興味がある方.\r\n- マルチモジュールプロジェクトの導入事例について興味のある方.",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28650,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "155408",
      "title": {
        "ja": "Scadeを使って「Swift」で始めるAndroidアプリ開発",
        "en": "Get started Android app development with *Swift* and Scade"
      },
      "description": "多くのものが出てきたクロスプラットフォーム開発のツール。\r\n\r\n現在ではXamarin, React Native や Flutter が知られており、近年はKotlin/Native も広く知られるようになりました。\r\n\r\nそんな中、2017年から細々と開発が行われていたScadeをご存じでしょうか？\r\n\r\nこのセッションでは、そんな「Swift」を使ってクロスプラットフォーム開発のできる\"Scade\"の紹介をします。\r\n\r\n目次案は以下のような内容を考えています\r\n- 既存のツールとの比較\r\n- Swiftで開発できることのメリット\r\n- Scadeで出来ること/出来ないこと\r\n\r\n..etc\r\n\r\n\r\nMany cross-platform development tools have appeared.\r\n\r\nXamarin, React Native, and Flutter are currently known, but in recent years Kotlin / Native has become widely known.\r\n\r\nDid you know that Scade was developed since 2017?\r\n\r\nIn this session, we will introduce “Scade” that can be used for cross-platform development using “Swift”.\r\n\r\nThe draft table of contents considers the following:\r\n-Comparison with existing tools\r\n-Advantages of development using Swift\r\n-What you can / can't do with Scade\r\n\r\n..etc",
      "startsAt": "2020-02-21T14:00:00+09:00",
      "endsAt": "2020-02-21T14:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "3009a2e0-81b7-466b-9116-a3f7a64d66d6"
      ],
      "roomId": 11517,
      "targetAudience": "Androidで簡単な開発の知識がある方\r\nクロスプラットフォームに興味がある方\r\n\r\nEasy development knowledge on Android.\r\nInterested in cross-platform.",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28656,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156201",
      "title": {
        "ja": "Testing as a Culture",
        "en": "Testing as a Culture"
      },
      "description": "Automated testing is a very important aspect of building any kind of software, including Android apps. There are many resources on the Internet on how to test our app and everybody has their own opinion on what is the most effective method. In Android, automated testing does not only mean unit tests, as there are different ways to automate the testing of our application. When building an app, setting up automated testing should be a culture, instead of an afterthought. However, there are still some teams who have very minimal tests and some don't even have any tests at all for their projects.\r\nIn this talk, I will discuss the mindset for testing an Android application. A mental model that we can apply when writing and improving our automated testing. I will also explore the types of tests that are available in an Android project and then present some techniques and tools that can help us structure our tests, such as the testing pyramid, Project Nitrogen, and Firebase Robo Test. In addition, I will talk about testing conventions and how it can help us write cleaner, more readable, and more maintainable test code. Lastly, I will share my experience of migrating a legacy codebase to a new architecture that is more scalable and testable.",
      "startsAt": "2020-02-21T15:00:00+09:00",
      "endsAt": "2020-02-21T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "a92fcca5-0cb3-459d-ac07-56f59dbb0302"
      ],
      "roomId": 11511,
      "targetAudience": "Any Android app developers who are interested in automated testing.\r\nAndroid app developers who want to start writing or improve their automated tests.\r\nRequires basic knowledge of Android development.\r\n",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28652,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156640",
      "title": {
        "ja": "System UIをコントロールして、画面を最大限に生かしたアプリを構築する",
        "en": "Build apps that make the best uses of device screens by controlling System UI"
      },
      "description": "端末の画面を最大限に活かし、より良い体験を提供するために、System UI（Status Bar、Navigation Bar）を考慮することは非常に重要です。\r\nしかし、Android Q、Q以前などでのバージョンごとの差異、ノッチ対応、画面構成によっては、WindowInsetsを取得する必要があるなど、考慮しなければならない点が多くあります。\r\n\r\nこの発表では、最初にSystem UIをコントロールするために抑えておくべき要素を、実際の挙動を見ながら、1つ1つ理解していきます。\r\n具体的には、次のことを説明します。\r\n\r\n- fitsSystemWindowsの挙動について\r\n- ThemeからSystem UIの設定をする\r\n- Navigation Component（Single Activity）との兼ね合い\r\n- WindowInsetsを取り、動的にSystem UIを調整する\r\n    - FloatingActionButtonとの兼ね合い\r\n- systemUiVisibilityについて\r\n- ノッチ端末（cutout）への対応\r\n\r\n最後に、実践的な例として、Google I/OなどのアプリのSystem UIをどのようにすれば構築出来るかを説明します。\r\n\r\nSystem UIの基礎 〜 実践までを理解することで、画面を最大限に生かした没入感のあるアプリを実装出来るようになることを目指します。",
      "startsAt": "2020-02-21T15:00:00+09:00",
      "endsAt": "2020-02-21T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "d36b8c88-3cca-4895-b7d0-80f1fbdcc307"
      ],
      "roomId": 11512,
      "targetAudience": "初学者向けです。\r\n\r\n- エッジ ツー エッジーに対応したい人\r\n- Status BarやNavigation Barのコントロール方法を知りたい人\r\n- fitsSystemWindowsを何となく使っている人\r\n",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156665",
      "title": {
        "ja": "Peer-to-peer communications with BLE on Android",
        "en": "Peer-to-peer communications with BLE on Android"
      },
      "description": "When you think of Bluetooth on Android this first thing that pops into your head is probably a phone controlling a speaker or smart watch but, that’s not all the technology can do. Bluetooth is powerful and the API for Android is flexible allowing you to solve some pretty interesting problems.\r\n \r\nI work for YAMAP, the number one outdoor app in Japan. One of our main goals is to keep our users safe while exploring the outdoors. In the event of an emergency, we want the most up to date location information, even if the user is hiking out of cellular service range. To accomplish this we implemented a large scale peer-to-peer BLE network and along the way encountered many special cases and learned lessons that could benefit anyone trying to communicate over BLE on Android. \r\n\r\nIn this talk we’ll take a hands-on look at implementing BLE communication between app instances and cover the following topics in detail.\r\n\r\n- Bluetooth types and their availability on android  \r\n- Checking for  specific device feature availability  (a bit trickier \r\n  than you might expect)\r\n- Recommended configurations for BLE clients and servers \r\n  including tips on battery life and performance\r\n- Notes about interoperability with app instances running on iOS\r\n",
      "startsAt": "2020-02-21T15:00:00+09:00",
      "endsAt": "2020-02-21T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "9f86aafb-3f7e-4d1f-902d-ead9960e7d49"
      ],
      "roomId": 11513,
      "targetAudience": "Aimed at intermediate Android developers.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156799",
      "title": {
        "ja": "Understanding RecyclerView",
        "en": "Understanding RecyclerView"
      },
      "description": "Androidアプリを開発する上でRecyclerViewは非常に便利なViewです。主に多量のアイテムを表示するために利用するViewですが、RecyclerViewは様々なコンポーネントから構成されており、そのコンポーネントをカスタマイズすることでただアイテムを並べるだけでなく様々な表現やパフォーマンスの向上が可能になっています。代表的なもので以下のようなコンポーネントがあります。\r\n\r\n- RecyclerView.ItemDecoration\r\n- RecyclerView.LayoutManager\r\n- RecyclerView.ItemAnimator\r\n- RecyclerView.Adapter\r\n- RecyclerView.RecycledViewPool\r\n- SnapHelper\r\n- ItemTouchHelper\r\n- DiffUtil\r\n\r\n例えば、RecyclerView.ItemDecorationをカスタマイズすればアイテム間にフレキシブルにマージンを付ける事が可能です。応用すればDroidKaigi 2019アプリのセッション一覧画面のようにアイテムの横に時間を表示することも可能です。RecyclerView.LayoutManagerをカスタマイズすれば同じくDroidKaigi 2019アプリのタイムテーブル画面のような複雑なレイアウトを実現することが可能です。\r\nこのように、RecyclerViewは非常に拡張性の高いViewです。いつか来るであろうデザイナーからの高い要求に「出来ます」と余裕の一言を返すために、本セッションで以下の2つを通してRecyclerViewの理解を深めましょう。\r\n\r\n# RecyclerView.ItemDecorationでCanvas直書き装飾\r\n前述したようにItemDecorationはアイテム間にマージンをつけるだけにとどまりません。RecyclerViewのCanvasにアクセスすることでRecyclerViewのアイテム間を縦横無尽に装飾する事が可能です。\r\n\r\n# LinearLayoutManagerをミニマム再実装してRecyclerView.LayoutManagerの実装理解する\r\nRecyclerView随一の複雑かつ重要なコンポーネント、LayoutManagerの実装方法をLinearLayoutManagerのミニマム実装を通して理解しましょう。\r\n\r\n* セッション時間に余裕があればRecyclerView.ItemAnimatorの実装方法も紹介するかもしれません。",
      "startsAt": "2020-02-21T15:00:00+09:00",
      "endsAt": "2020-02-21T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "ca18d593-1553-4519-8efc-e900a442ae19"
      ],
      "roomId": 11514,
      "targetAudience": "- RecyclerViewを使用したことがある方\r\n- ItemDecoration系のライブラリやLinearLayoutManagerは使うが実装方法は知らない方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "155320",
      "title": {
        "ja": "FlutterをRenderObjectまで理解する",
        "en": "Dive into Flutter up to RenderObject"
      },
      "description": "FlutterはWidgetをツリー構造に組み合わせて、UIレイアウトを構築します。\r\nそれでは、構築されたWidgetツリーはどのようにスクリーンにレンダリングされるのでしょうか。\r\n重要な概念はElementとRenderObjectです。\r\nRenderObjectはレンダリングの責務を担い、ElementはWidgetとRenderObjectの仲介役の責務を担っています。\r\n\r\nElementとRenderObjectは、重要な概念であるものの触れる機会が少ないため、開発者は普段あまり意識しないかもしれません。\r\nしかし、WidgetとElementとRenderObjectの関係や、それぞれの働きを理解することで、品質やパフォーマンスについてより考慮した実装ができ、またカスタムしたレイアウトやUIエフェクトを実装する際の一助になるでしょう。\r\n本セッションでは聴講者が、Widget,Element,RenderObjectを理解して、パフォーマンスを考慮した実装ができることをゴールとします。\r\n\r\nまた、RenderObjectWidget(RenderObjectを生成するメソッドを持つクラス)を直接用いて、\r\nグラフィックを描画する方法についてもご紹介いたします。",
      "startsAt": "2020-02-21T15:00:00+09:00",
      "endsAt": "2020-02-21T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "253f0fa8-f06e-40d0-ae7a-2ddcabd195ee"
      ],
      "roomId": 11515,
      "targetAudience": "- Widgetを用いてFlutterのUIを構築したことがある方\r\n- パフォーマンスを考慮したUIを構築したい方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28656,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156819",
      "title": {
        "ja": "KotlinではじめるGradle Plugin",
        "en": "Gradle Plugin : Getting started with Kotlin"
      },
      "description": "発表内容\r\n- プラグインの開発環境について\r\n- デモプロジェクトの作成\r\n- KotlinでHelloworldの実装\r\n- 動作確認とデバック方法の説明\r\n- リソースファイルの読み込みや成果物の出力方法について\r\n- その他よく使うGradleAPIの紹介\r\n- Pluginの公開方法について\r\n- (時間があったら Android Gradle Pluginの実装の話を入れます)\r\n\r\n話せないこと\r\n- build.gradle.kts についてのTips\r\n\r\nGradlePluginの作り方は情報が断片的であったり、ニッチな情報の多くがGroovyで説明されていたり、とっつきにくい印象を持たれやすいと思います。本セッションでは2020年らしくGradle Pluginの体系的な情報をKotlinを使ってご紹介します。",
      "startsAt": "2020-02-21T15:00:00+09:00",
      "endsAt": "2020-02-21T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "94c1fea0-93e2-4983-a389-2c94ccd9a2eb"
      ],
      "roomId": 11516,
      "targetAudience": "- 初心者から中級者まで\r\n\r\nGradleを知っていてbuild.gradleを多少なりとも編集されたことがあれば把握できると思います。",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": {
        "ja": "諸事情によりキャンセルされました。",
        "en": "This session has been canceled."
      },
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": true
    },
    {
      "id": "151629",
      "title": {
        "ja": "通話アプリの作り方",
        "en": "How to build calling apps"
      },
      "description": "AndroidのオーディオAPIには様々なものがありますが、いざ音声を扱うアプリケーションを作ろうとすると、APIの使い方に加えて一般的な音声処理の知識も要求され、なかなかとっつきにくいところがあります。\r\nそこで本セッションでは、初学者向けに音声処理の基礎知識及びAndroid上で利用できる音声処理のAPIについて紹介した上で、通話アプリを題材に、音声処理のデータフローや実装上のハマりどころ等を解説していきます。\r\n\r\nアジェンダとしては、次のようなものを予定しています。\r\n\r\n* 音声処理の基礎\r\n  * PCM\r\n  * サンプリングレート\r\n  * チャンネル\r\n* Android上のオーディオAPI\r\n  * SoundPool\r\n  * AudioRecord/AudioTrack\r\n  * OpenSL ES/AAudio/Oboe\r\n* 通話機能を作ってみる - 技術選定\r\n  * プロトコル\r\n  * コーデック\r\n  * ライブラリ\r\n* 構成とデータフロー\r\n* 実装\r\n  * 再生と録音\r\n  * エンコード/デコード\r\n  * リサンプリング\r\n  * ステレオ/モノラル変換\r\n* Tips\r\n  * 音声周りのデバッグ手法\r\n  * パフォーマンスチューニング",
      "startsAt": "2020-02-21T15:00:00+09:00",
      "endsAt": "2020-02-21T15:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "af707fc9-5a94-4d9a-9efe-75885f87f204"
      ],
      "roomId": 11517,
      "targetAudience": "* 通話アプリの実装に興味がある人\r\n* 音声処理の基礎を知りたい人\r\n* Android上のオーディオの取り扱いに興味がある人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156835",
      "title": {
        "ja": "実践マルチモジュール",
        "en": "Practical multi-modules"
      },
      "description": "近年のAndroidアプリ開発では1つのアプリを複数のモジュールに分割しながら開発することが増えてきています。モジュールの分割はビルド時間の高速化やアーキテクチャ構造の強制化といった様々なメリットがあります。一方で、マルチモジュール構造が一般的になる以前から存在するアプリはモノリシックな構造で実装されていることが多く、機能開発と並行してマルチモジュール構造に移行していくのは容易ではありません。\r\n\r\n本セッションでは、マルチモジュールの概要やメリット・デメリットからスタートして、レイヤー単位での分割や機能単位での分割といったモジュール分割の方法論や、機能同士の依存管理や機能横断の画面遷移といったマルチモジュール環境におけるいくつかのTips、そして、発表者が実務で扱ったモノリシック構造からマルチモジュール構造への移行方法を紹介します。\r\n\r\n- マルチモジュールについて\r\n　- マルチモジュールの概要\r\n　- モジュール分割の方法論\r\n　　- レイヤー単位での分割\r\n　　- 機能単位での分割\r\n　　- 共通コードの扱い方\r\n　- マルチモジュール環境におけるTips\r\n　　- ライブラリのバージョン管理\r\n　　- 機能同士の依存管理\r\n　　- 機能横断の画面遷移\r\n　　- Daggerとの連携方法\r\n\r\n- 大規模アプリでの導入方法\r\n　- 全体の構造理解とモジュール構造の決定\r\n　- 共通コードを配置するためのCoreモジュールの作成\r\n　- 機能開発と並行してモジュール分割を進めるための方法論\r\n　- 機能単位でモジュール分割を進める際の障害と回避策",
      "startsAt": "2020-02-21T16:00:00+09:00",
      "endsAt": "2020-02-21T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "2670ad95-16dc-4133-b2f7-4998a82b3262"
      ],
      "roomId": 11511,
      "targetAudience": "- マルチモジュール構造に興味はあるが導入に踏み切れていない人\r\n- モノリシックなアプリをどのように分割していけばいいのか悩んでいる人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156528",
      "title": {
        "ja": "Zen and the Art of Operator Overloading",
        "en": "Zen and the Art of Operator Overloading"
      },
      "description": "Operator overloading is awesome! Or was it terrible? It will definitely make your code more concise. Or was it that it makes it completely illegible? Either way, let's talk about operator overloading  extension functions, and sealed classes! In this talk we'll look at how these work in Kotlin and explore what is happening under the hood. We'll consider best practices for each, as well as how we can dial everything up to 11 by using infix functions to create even more expressive domain specific languages. After this talk, you'll be able to apply what you've learned to find the \"middle way\" to apply these tools to create expressive, concise, and readable code.",
      "startsAt": "2020-02-21T16:00:00+09:00",
      "endsAt": "2020-02-21T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "d1d3a2be-a29f-4a2c-8a00-cf1e7bbfc567"
      ],
      "roomId": 11512,
      "targetAudience": "Developers who have at a least basic understanding of Kotlin and are interested in exploring intermediate to advanced topics.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156623",
      "title": {
        "ja": "Re:ゼロから始めるPlay Billing Library",
        "en": "Re: Zero - starting uses of Play Billing Library"
      },
      "description": "GoogleはGoogle I/O 2019にて、新しいPlay Billing Library 2.0と同時にPlay Billing Libraryの今後のロードマップを発表しました。\r\nPlay Billing Library 2.0では払い戻し機能であるacknowledgeと新しい支払い方法であるPending transactionsを新機能として導入しており、1.xから2.0にアップデートする際にはこれらの新機能に対応する必要があります。\r\nまたPlay Billing Libraryは今後、毎年開催されるGoogle I/Oにて新しいメジャーバージョンがリリースされます。\r\nこれまでアプリ内課金を実装するために利用してきたAIDLやPlay Billing Library 1.xは2021年のGoogle I/Oまでのサポートです。\r\nサポート期間が終了したAIDLやPlay Billing LibraryはPlayストアへの新しいアプリの公開や更新ができなくなります。\r\nつまり、Play Billing Library 2.0と同時にAIDLやPlay Billing Library 1.xのサポートが終了します。\r\n現在Google Playの課金機能をつかってアプリ内課金を提供しているアプリは今後、2年に1回は必ずPlay Billing Libraryのアップデートを行う必要があります。\r\nまだAIDLを利用して課金機能を提供している場合はPlay Billing Libraryに置き換えたり、Play Billing Library 1.xを使っている場合は2.0にアップデートする必要があります。\r\n\r\n本セッションでは、AIDLからPlay Billing Libraryに置き換えたい人、Play Billing Library 2.0にアップデートしたい人、これからPlay Billing Libraryを新たに導入したい人に向けて、Play Billing Libraryの概要や今後のロードマップ、AACを利用した具体的な実装方法や検証方法まで、マネーフォワードでの実際のプロダクトへの導入事例を交えて詳しくお話します。\r\n\r\n■ 発表予定の内容\r\n- Play Billing Libraryのロードマップ\r\n- Play Billing Library 2.0の新機能についての詳細\r\n- AIDLからPlay Billing Libraryに置き換える\r\n- Play Billing Library 1.xから2.xにアップデートする\r\n- Android Architecture ComponentとPlay Billing Libraryを使って定期購入を実装する\r\n- アプリ内課金の検証について\r\n- マネーフォワード MEで年額課金を導入したときのハマりどころについてのノウハウ（新しいアイテムの追加、プラン変更時の注意点、検証方法など）",
      "startsAt": "2020-02-21T16:00:00+09:00",
      "endsAt": "2020-02-21T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "9bd231a5-d846-4f6e-873a-54a2fae779d7"
      ],
      "roomId": 11513,
      "targetAudience": "- Google Playを使った課金機能を提供している、またはこれから提供したいと思っている人\r\n- AIDLからPlay Billing Libraryに置き換えたい人\r\n- Play Billing Library 2.0にアップデートしたい人\r\n- これからPlay Billing Libraryを新たに導入したい人",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28654,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156858",
      "title": {
        "ja": "瀕死のシステムが強くなって復活する話",
        "en": "The revival of our system that has almost died"
      },
      "description": "弊社が展開しているタクシー配車サービスにおける乗務員様向けのシステムは今、大きな転換期を迎えています。\r\n約半年に渡って進めてきた、大規模リファクタと開発対象デバイス刷新のプロジェクトがいよいよ陽の目を見るためです。\r\nそんな新システム開発の裏で最低限のメンテしかされず、死にゆく運命にいる現行のシステムがあります。\r\nとあることがきっかけで、今まで想定されていなかった多種多様なユースケースへの対応が必要となり、\r\n現行のシステムにも新たに生まれ変わるチャンスが巡ってきました。\r\n\r\nただし、現在の実装の成熟具合から、現行のシステムを拡張する形での対応は難しく\r\nまた大規模に改築するにしても、\r\n平行開発している新システムとのコード的な共存(マルチモジュール)を意識して対応しなければならない状況です。\r\n\r\n本セッションでは、上記対応を行った中で学んだ\r\n・多種多様なタクシー乗務のユースケースへ対応するための状態変化に強いアプリアーキテクチャ\r\n・対象端末が違う別のシステムと共存するためのノウハウ\r\nについてお話させて頂きます。\r\n\r\nキーワード\r\n - Flux\r\n - MVVM\r\n - マルチモジュール\r\n - ステートマシン\r\n - システムアーキテクチャ",
      "startsAt": "2020-02-21T16:00:00+09:00",
      "endsAt": "2020-02-21T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "c04d8047-0b4d-498b-a416-941e156b5b95"
      ],
      "roomId": 11514,
      "targetAudience": "・様々なユースケースよって複雑に状態変化するような、アプリアーキテクチャに興味のある方\r\n・複数端末(Phone、タブレット)への対応に興味がある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "156705",
      "title": {
        "ja": "Androidだって音楽アプリが作りたい(M5Stackによる実演付き)",
        "en": "We would love to create audio apps even on Android with M5Stack demos"
      },
      "description": "AndroidではMarshmallowでAndroid MIDI APIが追加され、MIDI機器と連携する音楽アプリが作れるようになりました。\r\nしかし、iOSに比べてAndroid用の音楽アプリの数はそれほど増えていません。原因はなんでしょうか？\r\nM5StackというマイコンでBLE MIDIコントローラを作りながら、Android用音楽アプリの可能性について探ってみました。\r\n\r\nこのセッションでは、BLE MIDIを使ったアプリの作り方やiOSとの違い、レイテンシー等の課題ついてお話します。\r\nまた、MIDIコントローラとしてM5Stackを使い実際に音を出しながら説明していきます。\r\n\r\n対象者\r\n- MIDIを使った音楽アプリを作ってみたい方\r\n- AndroidとM5Stackを繋いで何か作ってみたい方\r\n- オリジナルのMIDIコントローラーを作ってみたい方\r\n\r\nお話する内容\r\n- MIDIってなに?\r\n- BLE MIDI機器との接続方法と通信方法\r\n- なぜAndroidでは音楽アプリが少ないのか\r\n- レイテンシーについて\r\n- Android 10で追加されたNative MIDI APIについて\r\n- M5Stackで作ったBLE MIDIコントローラを使った実演",
      "startsAt": "2020-02-21T16:00:00+09:00",
      "endsAt": "2020-02-21T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "6ae6e16e-5711-4bcf-9adf-4b6fedc5805c"
      ],
      "roomId": 11516,
      "targetAudience": "- MIDIを使った音楽アプリを作ってみたい方\r\n- AndroidとM5Stackを繋いで何か作ってみたい方\r\n- オリジナルのMIDIコントローラーを作ってみたい方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28650,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "153412",
      "title": {
        "ja": "Androidでスクリーン配信をする技術",
        "en": "Techniques to achieve media projection on Android apps"
      },
      "description": "Androidでは、iOSのReplayKit同様にスクリーンキャプチャできる技術としてMediaProjectionがあります。今回は、MediaProjectionを使ってスクリーン配信アプリを実装する上で、ハマったことなどについてお話していきます。\r\n\r\n＜基本＞\r\n1. MediaProjectionで画面をキャプチャする実装の話\r\n2. OpenGLで映像フィルターを実装する話\r\n2. MediaCodecでH.264にエンコードしてRTMPで送信する話\r\n＜応用＞\r\n3. Xperia 1やPixel 3 XLなど、特殊なアスペクト比のデバイスに対する対処策について\r\n4. MediaProjectionを使って、端末内部の音声をキャプチャする話\r\n5. Oboe(AAudio/OpenSL ES)と組み合わせて、音声を低遅延でキャプチャする組み合わせについて",
      "startsAt": "2020-02-21T16:00:00+09:00",
      "endsAt": "2020-02-21T16:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "e4b79746-f0d2-4d82-81a1-a1f2a8bc8b4a"
      ],
      "roomId": 11517,
      "targetAudience": "Androidのライブ配信アプリに興味のある方\r\nOpenGL ESに興味のある方\r\n映像配信に興味のある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156685",
      "title": {
        "ja": "Android UIs: Patterns, Practices, Pitfalls",
        "en": "Android UIs: Patterns, Practices, Pitfalls"
      },
      "description": "Nearing the end of its lifespan, Android's UI framework is now chock-full of useful tools for building UIs. Sometimes it seems like there's a hundred ways to achieve something. Other times you have no idea where to even begin.\r\n\r\nThis talk outlines when and when not to apply certain UI components. It discusses tips and tricks Android developers can apply when building their UIs, as well as outlines some of the common gotchas to avoid. Topics discussed will include:\r\n\r\nAnimations and Transitions\r\nLayout hierarchies\r\nNot using ConstraintLayout for everything\r\nDrawables\r\nCustom Views/ViewGroups\r\nThreading and timing\r\nThemes and styles",
      "startsAt": "2020-02-21T17:00:00+09:00",
      "endsAt": "2020-02-21T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "ea8b9ce3-c07c-4683-9e4e-81f6680e896d"
      ],
      "roomId": 11511,
      "targetAudience": "Android developers who have trouble selecting the right tools for the job when building UIs, or simply don't know what options exist. This talk could also be enjoyed by designers who would like to communicate with developers about what's possible when it comes to Android UIs.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28648,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156069",
      "title": {
        "ja": "FirebaseでつくるサーバレスAndroidアプリの開発",
        "en": "Development for serverless android applications with Firebase"
      },
      "description": "Firebaseには多くの機能があり、Firebaseを使用するとサーバレスのアプリをとても簡単に開発できます。\r\n\r\n本セッションでは、実際のFirebaseコンソールとAndroidのアプリのロジックを照らし合わせながら、\r\nあえてFirebaseにある機能だけを使用したアプリ開発の一連の流れをお話しします。\r\n\r\n＜主なトピック＞\r\n- Firebaseのプロジェクトの作成から設定\r\n- 認証とユーザ管理\r\n  - AuthenticationとFirestoreを使用した認証とユーザ管理をAndroidのアプリのロジックとFirebaseコンソールを照らし合わせながら作成します。\r\n- クラウド データベースを使用したデータの取得と更新\r\n  - Firestoreを使用したデータの取得と登録/更新/削除についてAndroidのアプリのロジックとFirebaseコンソールを照らし合わせながら確認します。\r\n- クラウド データベースのユーザデータの管理\r\n  - ユーザや、権限ごとのFirebaseクラウド データベース上のアクセス制御について確認します。\r\n- Push通知の送信と受信\r\n  - アプリの特定の操作をトリガーとしたPush通知の送信とその受信についてアプリのロジックとFirebaseコンソールを照らし合わせながら確認します。\r\n- それ以外にFirebase SDKを使用したアプリ開発のTips\r\n  - Remote Configやみんな大好きなAnalytics、クラッシュ分析などの実例を見ながらのご紹介\r\n- Firebaseだけでアプリ開発をする場合に困ったことの共有\r\n  - Firestoreへのグループ化したデータの取得の方法だったり、いくつかのナレッジを共有いたします。",
      "startsAt": "2020-02-21T17:00:00+09:00",
      "endsAt": "2020-02-21T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "09209394-36c6-446e-bb01-22d08f53c5dd"
      ],
      "roomId": 11512,
      "targetAudience": "- 個人でアプリを開発をしたいけど、WebAPIを開発せずにアプリだけ開発したいという方\r\n- Firebaseを使うとどんなアプリを作れるのか気になっている方\r\n- Androidアプリ開発初心者",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28649,
      "interpretationTarget": true,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER"
      ],
      "noShow": false
    },
    {
      "id": "154499",
      "title": {
        "ja": "The Secrets of the Build Scan Plugin and the internals of Gradle",
        "en": "The Secrets of the Build Scan Plugin and the internals of Gradle"
      },
      "description": "Build scans provide some very useful information about Gradle Builds. Build time, configuration time, task execution time.\r\nScans even show garbage collection time, time spent downloading dependencies, time spent in annotation processors? What can you do with all this data?\r\n\r\nIn this talk I'll share how to surface actionable information from your Gradle build immediately to developers without using the build scan plugin or Gradle Enterprise.\r\n\r\nThis talk will also show how to use the Gradle Doctor plugin and how it can help improve your build speeds: https://github.com/runningcode/gradle-doctor",
      "startsAt": "2020-02-21T17:00:00+09:00",
      "endsAt": "2020-02-21T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "89694c9d-5de0-4f39-a50a-ed48d6eb56ca"
      ],
      "roomId": 11513,
      "targetAudience": "The intended audience is users with some basic knowledge of Gradle and those who would like to learn more about Gradle as well as improve their build speeds.",
      "language": "ENGLISH",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28655,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "ADVANCED"
      ],
      "noShow": false
    },
    {
      "id": "156941",
      "title": {
        "ja": "KotlinのDelegated Propertiesを活用してAndroidアプリ開発をもっと便利にする",
        "en": "Make Android apps development more convenient with Kotlin's Delegated Properties"
      },
      "description": "Kotlinには委譲プロパティ（Delegetaed proprties）という仕組みがあります。\r\n標準ライブラリのlazyや、AndroidXのActivityやFragmentに実装されているviewModelなどで普段からあまり意識せずに利用している方も多いと思います。\r\n本セッションでは、標準ライブラリやAndroidXなどの著名ライブラリに含まれている委譲プロパティ、および拙作のライブラリであるKotprefなどの事例を交えながら、委譲プロパティの仕組み、Kotlinでの実現方法、活用事例や自作する際の方法などをお話します。\r\n\r\n本セッションに含まれる内容\r\n- KotlinのDelegated propertiesとは\r\n- Delegated propertiesの仕組み\r\n- 標準ライブラリで提供されるDelegated properties\r\n- 各種ライブラリでの事例\r\n- Kotprefを題材に実際の実装方法について知る",
      "startsAt": "2020-02-21T17:00:00+09:00",
      "endsAt": "2020-02-21T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "6a0485da-acd4-421a-84db-cfda44d176d4"
      ],
      "roomId": 11514,
      "targetAudience": "KotlinのDelegated propertiesがどのように実現されているのか知りたい方\r\nDelegated propertiesをあまり活用できていないが今後活用していきたいと考えている方\r\n自作のDelegated propertiesを作りたい方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28646,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "156794",
      "title": {
        "ja": "Robolectricの限界を理解してUIテストを高速に実行しよう",
        "en": "Let's run UI Test faster with understanding limit of Robolectric"
      },
      "description": "Espressoを使ったUIテストは、ユニットテストでは手の届かない動作確認にとても役立ちます。\r\nそのような役立つ面がある一方で、UIテストには実行時間が長いという問題が付いてまわります。\r\nたとえば、実行時間の長さ故に、Pull Requestの度にUIテストを実行するのは非現実的です。\r\n\r\nそのような悩みを解決するために登場したのが、GoogleのProject Nitrogenの一環として実装が進んでいるRobolectric 4です。\r\nRobolectric 4が採用しているアプローチは、Espressoを使ったUIテストを高速なローカルJVM上で(Local Testとして)実行するというものです。\r\n\r\nところが、いざUIテストをLocal Testとして動かそうとすると、次のようなトラブルに直面します。\r\n\r\n・サンプルレベルのUIテストコードは動くのに、いざ実プロダクトのテストを動かそうとすると動かない\r\n・エラーメッセージを見ても何が問題なのか分からない\r\n\r\n日々忙しい中でこれらのトラブルを自力で解決するのは大変ですが、予め動作する範囲が分かっていたらどうでしょうか？\r\n動作する範囲内でUIテストを書ければ、Robolectricのトラブルに巻き込まれなくなります。\r\n日々のPull Requestの度にUIテストを実行することも夢ではありません！\r\n\r\n本セッションでは、Robolectricで動作するUIテストの範囲を明らかにすべく、以下のようなトピックを紹介します。\r\n\r\n・EspressoのUIテストをRobolectricで動かすための準備\r\n・Android Jetpack Componentsのうち、Robolectricで動く機能、動かない機能\r\n・Espresso APIのうち、Robolectricで動く機能、動かない機能\r\n・Robolectricで画面更新を待ち合わせる方法のベストプラクティス\r\n・Robolectricで動かしたいオススメUIテスト\r\n",
      "startsAt": "2020-02-21T17:00:00+09:00",
      "endsAt": "2020-02-21T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "b64b9957-1570-41b7-99b5-b727676ef23c"
      ],
      "roomId": 11515,
      "targetAudience": "・ユニットテストではカバーできないテスト自動化に取り組もうとされている方\r\n・AndroidのUIテストが遅すぎて困っている方\r\n・Robolectricに挑戦してみたものの、動かない原因がわからず諦めてしまった方\r\n\r\n※Espresso初心者の方も付いていけるように配慮しますが、時間の制約から、詳しいAPIの説明などは割愛すると思います。",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28652,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "153686",
      "title": {
        "ja": "Customize ExoPlayer",
        "en": "Customize ExoPlayer"
      },
      "description": "ExoPlayerはカスタム可能なインターフェースを提供し、より使用者に適した形で最適化できるように設計されています。\r\n様々なシチュエーションにおいて、どのようにPlayerを最適化することができるかを主に話します。\r\n\r\nまた、動画再生ではクラッシュではなく、動画が止まったり映像がカクついたりするような再生不具合が発生しますが、そのような際のデバッグ方法や調査方法などのTipsも紹介します。\r\n\r\n例) 例外(BehindLiveWindowExceptionなど)を減らすためにどのようなことができるか。\r\n\r\n例) 再生開始時の最初に取得すべき解像度を考える\r\n\r\n例) TVやMobile、LIVE/VODでどのようなことを考慮すべきか",
      "startsAt": "2020-02-21T17:00:00+09:00",
      "endsAt": "2020-02-21T17:40:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "fbb08aa9-7aa1-4b70-b676-32905f5fd8d9"
      ],
      "roomId": 11516,
      "targetAudience": "動画再生を伴うアプリを開発している方\r\nAndroidでの動画再生に興味がある方\r\nExoPlayerなどを使って動画再生を行ったことがある方",
      "language": "JAPANESE",
      "lengthInMinutes": 40,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "INTERMEDIATE"
      ],
      "noShow": false
    },
    {
      "id": "4c7f2d64-9abe-4a40-8825-568c8bf4f4ac",
      "title": {
        "ja": "Closing",
        "en": "Closing"
      },
      "description": null,
      "startsAt": "2020-02-21T18:00:00+09:00",
      "endsAt": "2020-02-21T18:20:00+09:00",
      "isServiceSession": true,
      "isPlenumSession": false,
      "speakers": [],
      "roomId": 11511,
      "targetAudience": "TBW",
      "language": "TBD",
      "lengthInMinutes": 20,
      "sessionCategoryItemId": 28657,
      "interpretationTarget": false,
      "asset": {
        "videoUrl": null,
        "slideUrl": null
      },
      "message": null,
      "sessionType": "NORMAL",
      "levels": [
        "BEGINNER",
        "INTERMEDIATE",
        "ADVANCED"
      ],
      "noShow": false
    }
  ],
  "rooms": [
    {
      "id": 11511,
      "name": {
        "ja": "App bars",
        "en": "App bars"
      },
      "sort": 0
    },
    {
      "id": 11512,
      "name": {
        "ja": "Backdrop",
        "en": "Backdrop"
      },
      "sort": 1
    },
    {
      "id": 11513,
      "name": {
        "ja": "Cards",
        "en": "Cards"
      },
      "sort": 2
    },
    {
      "id": 11514,
      "name": {
        "ja": "Dialogs",
        "en": "Dialogs"
      },
      "sort": 3
    },
    {
      "id": 11515,
      "name": {
        "ja": "Pickers",
        "en": "Pickers"
      },
      "sort": 4
    },
    {
      "id": 11516,
      "name": {
        "ja": "Sliders",
        "en": "Sliders"
      },
      "sort": 5
    },
    {
      "id": 11517,
      "name": {
        "ja": "Tabs",
        "en": "Tabs"
      },
      "sort": 6
    },
    {
      "id": 12203,
      "name": {
        "ja": "Exhibition",
        "en": "Exhibition"
      },
      "sort": 7
    }
  ],
  "speakers": [
    {
      "id": "00f8c0a3-55cc-4c3a-af45-730fe03ea6b8",
      "firstName": "",
      "lastName": "",
      "bio": "Android Developer (2016~)",
      "tagLine": "Android Developer",
      "profilePicture": "https://sessionize.com/image?f=d308b3fb459d21d08bf87c29defc8fd8,400,400,1,0,a3-55cc-4c3a-af45-730fe03ea6b8.f82d39cd-2b97-4fd6-b80e-b03288f8915c.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156383",
        "156686"
      ],
      "fullName": "Ryo Yamazaki"
    },
    {
      "id": "07f3973b-befc-4cbe-9faa-6fad659ef9d9",
      "firstName": "",
      "lastName": "",
      "bio": "前職は某インターネットメディア企業の京都支社にてAndroidアプリ開発に従事。\r\n\r\n2019年1月に株式会社 bitFlyer に入社とともに上京。\r\n現在は bitFlyer のプロトレーダー機能である bitFlyer Lightning のAndroid版の開発や、利用率向上施策の開発を行い、暗号資産業界を陰ながら支えている。\r\n\r\n趣味はゲーム・アニメ・ヘアカラー。\r\n最近は自転車にもハマっている。痩せるのが目標。\r\n\r\nTwitter: https://twitter.com/yaginier\r\n",
      "tagLine": "Android Engineer at bitFlyer, Inc.",
      "profilePicture": "https://sessionize.com/image?f=969461a84f476e14bcf855c44aad2f4f,400,400,1,0,4497ad41-efe4-4350-9b48-b336e37e86e0.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156374"
      ],
      "fullName": "yagi2"
    },
    {
      "id": "09209394-36c6-446e-bb01-22d08f53c5dd",
      "firstName": "",
      "lastName": "",
      "bio": "2019 - Rakuten, Inc. Android Engineer\r\n2016 - Qrio, Inc. Android/iOS Engineer\r\n2015 - Gree, Inc. Android Engineer\r\nBefore 2014 - Free Application Engineer",
      "tagLine": "キッチンペーパーおにぎり",
      "profilePicture": "https://sessionize.com/image?f=8035a1c7ae30868e1aeecefd17e4fc87,400,400,1,0,94-36c6-446e-bb01-22d08f53c5dd.3dae6206-570b-4598-a530-5e23efa3d124.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156069"
      ],
      "fullName": "Nosaka"
    },
    {
      "id": "11cc830a-2767-4da6-9499-c7fa006a9441",
      "firstName": "",
      "lastName": "",
      "bio": "A graduate student who is studying in computer science and Android.",
      "tagLine": "University of Tsukuba",
      "profilePicture": "https://sessionize.com/image?f=ef97787a4b628d980f224b8c83399048,400,400,1,0,4aeb522e-b52a-467c-b300-3be1c9bf0e92.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "154763"
      ],
      "fullName": "chigichan24"
    },
    {
      "id": "152f613f-841b-407d-9a65-3703ec2dfae2",
      "firstName": "",
      "lastName": "",
      "bio": "Romain manages the Android Toolkit team. The team's projects included AndroidX/Jetpack, the UI toolkit, Kotlin support and graphics.",
      "tagLine": "Android Toolkit",
      "profilePicture": "https://sessionize.com/image?f=bfdfd8df4877afa2bd3557378f4fd771,400,400,1,0,d068ae37-2e0e-49bd-a4be-b376a32367a8.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "154105"
      ],
      "fullName": "Romain Guy"
    },
    {
      "id": "159e1276-8177-4bda-b630-6201df9d897b",
      "firstName": "",
      "lastName": "",
      "bio": "セキュリティエンジニアを経験し、現在は金融サービスのAndroid開発者をやっています。Firebase Japan User GroupのOrganizerをしています。",
      "tagLine": "Firebase Japan User Group Organizer, Android Engineer of Financial Service",
      "profilePicture": "https://sessionize.com/image?f=58b8f076b7dcabe6837ca7aceca5e415,400,400,1,0,76-8177-4bda-b630-6201df9d897b.5cd80d3b-d790-49f4-9c7e-01ea2ab544d3.png",
      "isTopSpeaker": false,
      "sessions": [
        "156213"
      ],
      "fullName": "コキチーズ"
    },
    {
      "id": "17bd0ded-66d8-437a-8a62-525844850b34",
      "firstName": "",
      "lastName": "",
      "bio": "位置ゲームやBlockchainを手がけるモバイルファクトリーのシニアエンジニア。\r\nスクラムマスターとしてプロダクトの価値を高める傍ら、Kotlin/MPPでプロダクトのリプレースを行う。\r\nGotanda.unityや吉祥寺.pmなどにもよく出没する。日本酒が好き。",
      "tagLine": "Mobile Factory.inc,   Senior engineer.",
      "profilePicture": "https://sessionize.com/image?f=e1908be69fb41f2b773af8d6899e5e93,400,400,1,0,2da151be-9646-4f59-839d-c1ec458abe86.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156842"
      ],
      "fullName": "yashims85"
    },
    {
      "id": "1d014c9e-6a06-4124-9af9-7df8db6e2761",
      "firstName": "",
      "lastName": "",
      "bio": "SonyEricssonで携帯電話開発に従事し、Xperia X10開発後同社を退社しGoldrush Computing株式会社を設立。以降Android, iOS, Google Home向けのアプリケーション開発を行う。得意な言語はKotlin, Java, Swift, Obj-C, C, Javascript, Python, Assembly(ARM)",
      "tagLine": "CEO, Goldrush Computing株式会社",
      "profilePicture": "https://sessionize.com/image?f=ac395409ab8ef94da4c80e9b25aa1b2c,400,400,1,0,9e-6a06-4124-9af9-7df8db6e2761.333e230b-a519-4181-bf31-cee50f23af8f.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156644"
      ],
      "fullName": "@mizutory"
    },
    {
      "id": "1dcd8d28-7ad2-4c6b-aa2a-92f3e3b83556",
      "firstName": "",
      "lastName": "",
      "bio": "AndroidX (Room, Transition) を開発しています。",
      "tagLine": "Developer Programs Engineer, Google",
      "profilePicture": "https://sessionize.com/image?f=16b9de26388887780a4e2186e19d5743,400,400,1,0,ee709c7f-8795-4cb9-a394-f71100e0caa0.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156876"
      ],
      "fullName": "荒木佑一"
    },
    {
      "id": "24aa5e63-d4a5-4e9d-8f54-0c95e7c339e8",
      "firstName": "",
      "lastName": "",
      "bio": "DeNAでタクシー配車サービス MOV のAndroidアプリを担当しています。\r\nSoftware Engineer at DeNA Co., Ltd.\r\nJP / EN\r\nGo Niners!",
      "tagLine": "Software Engineer at DeNA Co., Ltd.",
      "profilePicture": "https://sessionize.com/image?f=ee5b4ff13b9b77ad7991020b5207b297,400,400,1,0,63-d4a5-4e9d-8f54-0c95e7c339e8.155d971b-d031-42ec-aa3b-e2ce23c8535b.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156777"
      ],
      "fullName": "shinmiy"
    },
    {
      "id": "253f0fa8-f06e-40d0-ae7a-2ddcabd195ee",
      "firstName": "",
      "lastName": "",
      "bio": "2019.04~ Recruit Lifestyle Co., Ltd. ",
      "tagLine": "Recruit Lifestyle Co., Ltd. software development engineer",
      "profilePicture": "https://sessionize.com/image?f=904c60aede9ad26cb0885345dc239b1f,400,400,1,0,f52812ba-259b-47d9-b7b2-c97c5f51c3c0.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "155320"
      ],
      "fullName": "kiri"
    },
    {
      "id": "2670ad95-16dc-4133-b2f7-4998a82b3262",
      "firstName": "",
      "lastName": "",
      "bio": "Android Engineer",
      "tagLine": "eureka, Inc.",
      "profilePicture": "https://sessionize.com/image?f=281c5386bee5d6fd4cf970b47a66c279,400,400,1,0,c5142399-eb60-466f-9f77-f18eae578cd6.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156835"
      ],
      "fullName": "yuyakaido"
    },
    {
      "id": "2d41d3fb-3321-4b47-a401-ae84a5de2423",
      "firstName": "",
      "lastName": "",
      "bio": "Huyen Tue Dao is an Android developer and Google Developer Expert for Android and Kotlin. She currently works on the Trello Android app at Atlasssian and is also co-producer of the \"Android Dialogs\" YouTube channel.\r\n\r\nHuyen lives in Denver, CO though is often found in the DC Metro area. When not up late programming, she is often found up late gaming (video, board, card, anything).",
      "tagLine": "Android Code Monkey @ Trello",
      "profilePicture": "https://sessionize.com/image?f=4fba0036ec10512c17e5818e8ad8b302,400,400,1,0,082b63d1-68c8-4245-862e-15f1263325ff.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156788"
      ],
      "fullName": "queencodemonkey"
    },
    {
      "id": "3009a2e0-81b7-466b-9116-a3f7a64d66d6",
      "firstName": "",
      "lastName": "",
      "bio": "iOSエンジニア。\r\nエンジニアとしてWeb, Android, iOSの開発に従事。\r\n現在はiOSをメイン開発している。\r\n\r\niOS Developer.\r\nEngaged in web, Android and iOS development as an engineer.\r\nCurrently, iOS is the main development.\r\n\r\n\r\n",
      "tagLine": "every, inc. / iOS Developer",
      "profilePicture": "https://sessionize.com/image?f=cd34196ac8d2db33e13a28d2ef672879,400,400,1,0,e0-81b7-466b-9116-a3f7a64d66d6.ea00c28e-fce7-475f-9b13-a43285c2ae77.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "155408"
      ],
      "fullName": "Hiromu Tsuruta"
    },
    {
      "id": "3025abd9-ecb6-486b-85ee-e6305aa2ec5c",
      "firstName": "",
      "lastName": "",
      "bio": "an Android + Λrrow lover",
      "tagLine": "cat",
      "profilePicture": "https://sessionize.com/image?f=eadad6b8b32b26222ed03cf57e9feff5,400,400,1,0,735f779e-0620-4de0-90b0-4fd1c4bd3b55.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156662"
      ],
      "fullName": "daneko"
    },
    {
      "id": "33ff8262-ff9a-4d77-be1f-e288d62ba93f",
      "firstName": "",
      "lastName": "",
      "bio": "Engineer, Cyclist, Gymnast, Photographer",
      "tagLine": "Drivemode, Inc. Engineer.",
      "profilePicture": "https://sessionize.com/image?f=12fa0637e113ee335b1c12a9acbacae2,400,400,1,0,2b2ff3e1-98ae-4346-a122-9ee95ff47a78.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156729"
      ],
      "fullName": "KeithYokoma"
    },
    {
      "id": "3b564ac7-e550-4de8-9292-3173ce72c116",
      "firstName": "",
      "lastName": "",
      "bio": "Androidエンジニア, \r\nハードウェアと連携するシステムを担当することが多い.",
      "tagLine": "NAVITIME JAPAN CO., LTD. , Android Engineer",
      "profilePicture": "https://sessionize.com/image?f=cbbc498d863811c4f498fdde99954be7,400,400,1,0,c7-e550-4de8-9292-3173ce72c116.26fdf319-ecba-486c-b6df-b64a24733a4b.png",
      "isTopSpeaker": false,
      "sessions": [
        "156700"
      ],
      "fullName": "Yoshihiro Kobayashi"
    },
    {
      "id": "49b04902-1ce9-4276-99ba-4420556ac63d",
      "firstName": "",
      "lastName": "",
      "bio": "大阪のソフトウェア開発会社有限会社シーリス代表。\r\n最近はTensorFlowによる機械学習と、Androidアプリケーションへの組み込みに取り組んでいます。",
      "tagLine": "Programmer at C-LIS CO., LTD. ",
      "profilePicture": "https://sessionize.com/image?f=24e65289fa8e4f5b9d9256b804c595d6,400,400,1,0,710d4e29-707f-4bd3-a0de-bbddb5005394.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156202"
      ],
      "fullName": "keiji_ariyama"
    },
    {
      "id": "5208d614-cd46-4599-b7a5-cd3fd5d7c592",
      "firstName": "",
      "lastName": "",
      "bio": "グロービス でアプリエンジニアをしています。\r\n学生時代から音声系のサービスを開発・運用していました。",
      "tagLine": "グロービス ,アプリエンジニア,VoiceUI,音声アシスタント",
      "profilePicture": null,
      "isTopSpeaker": false,
      "sessions": [
        "156066"
      ],
      "fullName": "maKunugi N/A"
    },
    {
      "id": "531ff5e0-fb19-412a-9aa6-50a0045e1cd9",
      "firstName": "",
      "lastName": "",
      "bio": "Rohan has been developing Android apps for more than 5 years. He prefers self learning and love to collaborate with other developers to share the knowledge. Working with startups from beginning, he has worked on variety of apps including E-Commerce, Chat, Recording, Music Player. He is part of multiple Android groups and frequent BlrDroid and GDG meetups.",
      "tagLine": "Lead Android Developer @ Ailoitte",
      "profilePicture": "https://sessionize.com/image?f=2ddb124af0474810ec9d03117ab2a351,400,400,1,0,e0-fb19-412a-9aa6-50a0045e1cd9.0c7ed9df-c23c-422f-9066-824061df2c58.jpeg",
      "isTopSpeaker": false,
      "sessions": [
        "150650"
      ],
      "fullName": "Rohan Kandwal"
    },
    {
      "id": "532dc16d-b69d-4f68-aa49-fe9878b0b52f",
      "firstName": "",
      "lastName": "",
      "bio": "Adarsh is a Googler on the Android Studio team. He has written multiple guides for developers focused on optimizing Gradle build configurations and harnessing features of the Android plugin for Gradle—from beginner topics like creating build variants, to advanced ones like managing complex multi-module builds. In addition, he's also written guides for using Android Studio's support for external native build system's, such as CMake and ndk-build.",
      "tagLine": "Google",
      "profilePicture": "https://sessionize.com/image?f=998f105906c25566a9203f3be236f494,400,400,1,0,e7bdf209-26ed-4e56-bbc9-8fcd0b9b5eea.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156539"
      ],
      "fullName": "Adarsh Fernando"
    },
    {
      "id": "551effb7-8f0f-41e8-ab7c-99b4a299324f",
      "firstName": "",
      "lastName": "",
      "bio": "様々なプロジェクトを経験させていただけたおかげで、現在はAndroid・iOSのエンジニアとして活動しています。\r\n\r\n弊社で現在提供中のシフト管理アプリ「CAST」ではiOS版とAndroid版をネイティブ実装で1人で作成。\r\n\r\n技術書典5のサークル参加をきっかけに技術本の執筆もはじめました。",
      "tagLine": "hachidori.inc, Android, iOS, 開発なんでもござれ",
      "profilePicture": "https://sessionize.com/image?f=2ad6c98d74d8bce114599a3dcfc62ba7,400,400,1,0,b7-8f0f-41e8-ab7c-99b4a299324f.84cbde2d-6cfc-4995-bfe5-f1b12571da61.png",
      "isTopSpeaker": false,
      "sessions": [
        "153542"
      ],
      "fullName": "kuluna"
    },
    {
      "id": "580fb501-aece-4bf4-b755-32fda033b3bd",
      "firstName": "",
      "lastName": "",
      "bio": "あんざいゆき\r\n- twitter : https://twitter.com/yanzm\r\n- blog : http://y-anz-m.blogspot.jp/ (Y.A.Mの雑記帳）\r\n- 株式会社ウフィカ\r\n- book : Master of Fragment とか\r\n",
      "tagLine": "株式会社ウフィカ",
      "profilePicture": "https://sessionize.com/image?f=74af9ffcf2418f075cffcee1402fd9da,400,400,1,0,01-aece-4bf4-b755-32fda033b3bd.b27ac996-bd96-4dcd-8be0-9acd46b4e835.png",
      "isTopSpeaker": false,
      "sessions": [
        "156408"
      ],
      "fullName": "Yuki Anzai"
    },
    {
      "id": "5e938594-2bf3-41f6-aa33-3fb655097b3d",
      "firstName": "",
      "lastName": "",
      "bio": "ゲームプログラマ→SIer→アプリエンジニア(iOS→Android)",
      "tagLine": "LINE",
      "profilePicture": "https://sessionize.com/image?f=688d2692b3d984f212a124813e5fdb77,400,400,1,0,94-2bf3-41f6-aa33-3fb655097b3d.039a6521-51f3-42d7-9c00-2044c2ed296a.png",
      "isTopSpeaker": false,
      "sessions": [
        "156922"
      ],
      "fullName": "takasy"
    },
    {
      "id": "63a3dc07-8891-4cf2-b74c-15c6f626f7f3",
      "firstName": "",
      "lastName": "",
      "bio": "アプリエンジニア (Android / iOS)\r\n趣味開発  (Android / iOS / UWP / Windows / macOS / Ubuntu)",
      "tagLine": "ピクシブ株式会社",
      "profilePicture": "https://sessionize.com/image?f=5e7342e07d46df24824343a1cb4b6559,400,400,1,0,165268c5-e1b0-4d70-bfbf-9264ea6cedc4.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156919"
      ],
      "fullName": "六々 (@496_)"
    },
    {
      "id": "65df1273-b4f7-47c9-a225-ab63a521f5b4",
      "firstName": "",
      "lastName": "",
      "bio": "A mobile application and backend developer at DeployGate, Inc.",
      "tagLine": "DeployGate Inc.",
      "profilePicture": "https://sessionize.com/image?f=6fcc8ad4c2f319bd67b1c628212f7dc1,400,400,1,0,c28693bf-832b-43f8-936b-098ecaa1cdfb.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "153892"
      ],
      "fullName": "Jumpei Matsuda"
    },
    {
      "id": "66296294-ca8f-4d03-85f3-d26aa5035c5d",
      "firstName": "",
      "lastName": "",
      "bio": "Siyamed is a tech lead on the Android UI Toolkit team focused on text and UI widgets. Prior to Google, he developed consumer internet and Android applications in San Francisco, and co-founded a vertical search engine platform. He has a bachelor's degree in computer science.",
      "tagLine": "Android Text Lead / Google",
      "profilePicture": "https://sessionize.com/image?f=12b37f60489473b108ccaf2a572520db,400,400,1,0,94-ca8f-4d03-85f3-d26aa5035c5d.33a3d2a1-f437-4bbe-b68f-c50f7ca28c64.png",
      "isTopSpeaker": false,
      "sessions": [
        "155123"
      ],
      "fullName": "Siyamed Sınır"
    },
    {
      "id": "6802fed3-c3b6-4293-88d9-9da37f7db9d0",
      "firstName": "",
      "lastName": "",
      "bio": "Android application engineer at Smarby, Inc.",
      "tagLine": "Smarby, Inc",
      "profilePicture": "https://sessionize.com/image?f=f8a805c98bf49f66f6c5b1bc7a0277dd,400,400,1,0,883c6ae7-9d13-4ff6-ac79-a555f7d931cf.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156056"
      ],
      "fullName": "androhi"
    },
    {
      "id": "697e3809-2e7e-4314-8db1-0fb5fd0c9f8b",
      "firstName": "",
      "lastName": "",
      "bio": "DroidKaigi2017 エンジニアが武器にするMaterial Design\r\nDroidKaigi2018 詳解 ViewGroupのレイアウト内部実装",
      "tagLine": "人間とAndroidのハーフ",
      "profilePicture": "https://sessionize.com/image?f=90df0c25025d26ff4983bb69943c3506,400,400,1,0,f2d8e77a-1c9f-45d2-af5a-78ca09c2f106.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156210"
      ],
      "fullName": "HiroYUKI Seto"
    },
    {
      "id": "6988a352-4da6-4eb8-b0d8-1050523620de",
      "firstName": "",
      "lastName": "",
      "bio": "Android developer since Dount\r\niOS developer since  iPhone OS 2.2",
      "tagLine": "Amazia,inc.",
      "profilePicture": "https://sessionize.com/image?f=7127d09c15e0c0c1c6f57008b29070ff,400,400,1,0,cba06aa2-48f7-4e52-9c94-8ca790f70648.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "155449"
      ],
      "fullName": "coffeegyunyu"
    },
    {
      "id": "6a0485da-acd4-421a-84db-cfda44d176d4",
      "firstName": "",
      "lastName": "",
      "bio": "Android engineer @DeNA Co., Ltd.\r\nAuthor of Kotpref (https://github.com/chibatching/Kotpref).",
      "tagLine": "DeNA Co., Ltd",
      "profilePicture": "https://sessionize.com/image?f=3c2fe49183d0e0af15732b8b50f1e14c,400,400,1,0,da-acd4-421a-84db-cfda44d176d4.332e3d28-df85-41ee-bbc4-062974efdeee.png",
      "isTopSpeaker": false,
      "sessions": [
        "156941"
      ],
      "fullName": "chibatching"
    },
    {
      "id": "6ae6e16e-5711-4bcf-9adf-4b6fedc5805c",
      "firstName": "",
      "lastName": "",
      "bio": "https://www.linkedin.com/in/kentaharada/",
      "tagLine": "Bluetooth engineer at JapanTaxi",
      "profilePicture": "https://sessionize.com/image?f=5d51016eb89b65312cc1b6a44570afcb,400,400,1,0,6e-5711-4bcf-9adf-4b6fedc5805c.bffcfbfe-f18a-4cfb-8f3c-79ebdcda5e06.png",
      "isTopSpeaker": false,
      "sessions": [
        "156705"
      ],
      "fullName": "Kenta Harada"
    },
    {
      "id": "6f216970-9b9c-4ecc-86e3-b28cd46d572e",
      "firstName": "",
      "lastName": "",
      "bio": "UI/UXデザイナー",
      "tagLine": "UI/UXデザイナー",
      "profilePicture": "https://sessionize.com/image?f=4aa6c2858335aa7f59984466172b8a3a,400,400,1,0,5a5d4a03-f37d-4d58-8776-93bd7d7428a8.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156383"
      ],
      "fullName": "Hiroki Nagayama"
    },
    {
      "id": "704adb77-0ec3-450c-8970-868b7c109df0",
      "firstName": "",
      "lastName": "",
      "bio": "時々カメラマンになるタイプのAndroidエンジニア。現職ではAmebaのAndroidアプリの開発をしています。",
      "tagLine": "CyberAgent, Inc.",
      "profilePicture": "https://sessionize.com/image?f=7e5e5c5ee2a5ee01693ad6bcdd8cf585,400,400,1,0,77-0ec3-450c-8970-868b7c109df0.b661f492-fbff-418f-88fd-9ff5d499c6a7.png",
      "isTopSpeaker": false,
      "sessions": [
        "156840"
      ],
      "fullName": "Yoshihiro Wada"
    },
    {
      "id": "72b4cb54-58f4-4dfe-b75c-c2faff1f07bd",
      "firstName": "",
      "lastName": "",
      "bio": "「持続可能なソフトウェア」の探求がライフワーク。C#、.NET、WPFあたりが住処。",
      "tagLine": "RICOH JAPAN Corp. Application Architect.",
      "profilePicture": "https://sessionize.com/image?f=9cdd64408825711bed7f84b1b17114c8,400,400,1,0,03bd7cc7-cc3b-4f82-b8eb-57cd76b93687.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156065"
      ],
      "fullName": "Atsushi Nakamura"
    },
    {
      "id": "75244713-51c4-4977-82ea-25a4d340af26",
      "firstName": "",
      "lastName": "",
      "bio": "Rebecca Franks an Android Engineer at Over, an app for graphic design on mobile, who is based in South Africa. She has over 7 years experience in developing Android applications. She enjoys public speaking and frequently speaks at conferences and local meetups. A Google Developer Expert for Android who loves making beautiful apps with magical animations. ",
      "tagLine": "Android Developer at Over & Google Developer Expert",
      "profilePicture": "https://sessionize.com/image?f=45b17f3cbe9e74ffeaba2773ac718b20,400,400,1,0,13-51c4-4977-82ea-25a4d340af26.a2eced2e-b70c-4a09-93df-c449ca3a0310.png",
      "isTopSpeaker": false,
      "sessions": [
        "153109"
      ],
      "fullName": "riggaroo"
    },
    {
      "id": "792a7a14-72c5-472d-bec3-e65ba327b760",
      "firstName": "",
      "lastName": "",
      "bio": "With over 20 years of software development experience, and over 13 years of experience building neural networks, Phil has worked with some of the most successful organisations in the world building high-performance and scalable platforms, products, and tools.",
      "tagLine": "Creative Technologist focused on UX and AI.",
      "profilePicture": "https://sessionize.com/image?f=848c80ef9c4fc94be395c88ffd0a3dab,400,400,1,0,14-72c5-472d-bec3-e65ba327b760.01f0c5de-0f90-4c76-b203-2a9e0eedd027.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156546"
      ],
      "fullName": "HelloFillip"
    },
    {
      "id": "854edee5-c9cb-49e8-80c4-a0ee63b82f7b",
      "firstName": "",
      "lastName": "",
      "bio": "DeNAのSWETチームに所属しています。\r\nAndroidとテストが好きです。",
      "tagLine": "DeNA, SWET",
      "profilePicture": "https://sessionize.com/image?f=9be0343917ac49481a84035be22cbbe0,400,400,1,0,e5-c9cb-49e8-80c4-a0ee63b82f7b.d6fd115e-5bf7-4477-af67-b968cb4ca7ad.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156764"
      ],
      "fullName": "Nozomi Takuma"
    },
    {
      "id": "89694c9d-5de0-4f39-a50a-ed48d6eb56ca",
      "firstName": "",
      "lastName": "",
      "bio": "Former Square in San Francisco, now working at Soundcloud in Berlin",
      "tagLine": "Android at Soundcloud",
      "profilePicture": "https://sessionize.com/image?f=415bd88b1c76a926d3ee27c659aa046f,400,400,1,0,9d-5de0-4f39-a50a-ed48d6eb56ca.c091953d-d32b-4b08-a057-5789670b3de1.jpeg",
      "isTopSpeaker": false,
      "sessions": [
        "154499"
      ],
      "fullName": "Nelson Osacky"
    },
    {
      "id": "8cf3b542-a0b4-451a-9326-3bd37bf69532",
      "firstName": "",
      "lastName": "",
      "bio": "Androidアプリ開発大好きエンジニア",
      "tagLine": "ヤフー株式会社",
      "profilePicture": "https://sessionize.com/image?f=0da6a4d26567b4f64dbdac2625d166a6,400,400,1,0,42-a0b4-451a-9326-3bd37bf69532.5b2a4274-0d17-4329-ad97-dbf1c54ffbfa.png",
      "isTopSpeaker": false,
      "sessions": [
        "156774"
      ],
      "fullName": "plavelo"
    },
    {
      "id": "903eaf98-bf70-42a1-80da-6af3c5ec6668",
      "firstName": "",
      "lastName": "",
      "bio": "Kyash Inc. Engineer\r\nhttps://github.com/konifar",
      "tagLine": "konifar, doraemon",
      "profilePicture": "https://sessionize.com/image?f=f7adeeda576d9a4a19f87e2fb538e475,400,400,1,0,50a020e5-f98f-41bd-b182-acc2b902a920.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156779"
      ],
      "fullName": "konifar"
    },
    {
      "id": "94c1fea0-93e2-4983-a389-2c94ccd9a2eb",
      "firstName": "",
      "lastName": "",
      "bio": "コロッケになるじゃがいもよりはましか",
      "tagLine": "cookpad",
      "profilePicture": "https://sessionize.com/image?f=ab9430d2371488b20ef851bf1aa2a140,400,400,1,0,9197eb57-ebf1-4e0f-994d-39c00fda7b96.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156819"
      ],
      "fullName": "kazy(Kazuki Yoshida)"
    },
    {
      "id": "975fd8a9-088c-4e6c-881c-b3e7504f21cc",
      "firstName": "",
      "lastName": "",
      "bio": "Nikkei software engineer / search ",
      "tagLine": "Nikkei ",
      "profilePicture": null,
      "isTopSpeaker": false,
      "sessions": [
        "156383"
      ],
      "fullName": "Ryo Kato"
    },
    {
      "id": "9bd231a5-d846-4f6e-873a-54a2fae779d7",
      "firstName": "",
      "lastName": "",
      "bio": "MoneyForward, inc.のAndroidアプリエンジニアです。\r\n\r\n◎Qiita\r\nhttp://qiita.com/syarihu\r\n\r\n◎公開中のAndroidアプリ\r\nhttps://play.google.com/store/apps/developer?id=syarihu",
      "tagLine": "Android Engineer",
      "profilePicture": "https://sessionize.com/image?f=67e6607735b8eed704dd42e483528e5f,400,400,1,0,b205e6b5-d943-42f0-b690-e432c1931bd3.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156623"
      ],
      "fullName": "syarihu"
    },
    {
      "id": "9d0e551a-69b3-4354-8264-78c974038e6b",
      "firstName": "",
      "lastName": "",
      "bio": "Spanish software engineer trying to understand Japan loves traveling and beer.",
      "tagLine": "LINE, Android engineer.",
      "profilePicture": "https://sessionize.com/image?f=8f6a05a8f5cdedd690bbd567ac110bc4,400,400,1,0,38e38065-16ef-4b2b-ae83-6de68becb058.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156769"
      ],
      "fullName": "Dani Vila Teissiere"
    },
    {
      "id": "9f86aafb-3f7e-4d1f-902d-ead9960e7d49",
      "firstName": "",
      "lastName": "",
      "bio": "I'm a self taught Android developer obsessed with learning in general.",
      "tagLine": "Android developer at YAMAP",
      "profilePicture": "https://sessionize.com/image?f=572dc80ff85a8a3bf44013b28cd56e32,400,400,1,0,5bb92bed-072e-422d-917b-d9f98f0e6887.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156665"
      ],
      "fullName": "Keinix"
    },
    {
      "id": "a7d0fcec-49dd-4e65-bcb7-ad3d1901928c",
      "firstName": "",
      "lastName": "",
      "bio": "John is a developer on Android working on Android Studio, ConstraintLayout and MotionLayout. He has worked on Android Studio, RenderScript, Vector Drawables, computed shadows and photo editor filters. ",
      "tagLine": "Android developer at Google",
      "profilePicture": "https://sessionize.com/image?f=6056eda5936b3ec108637857f140d8c3,400,400,1,0,ec-49dd-4e65-bcb7-ad3d1901928c.485a9d64-3913-4421-b715-45adb26574e1.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156785"
      ],
      "fullName": "John Hoford"
    },
    {
      "id": "a7da8c70-c13b-4ad5-aa0a-ddb502e5eed6",
      "firstName": "",
      "lastName": "",
      "bio": "Jitin works as an Android Engineer at Gojek, India where he works on aligning design and development as part of UX engineering. He previously developed travel applications for various airlines and has 5 years of industry experience. He writes about Android and Kotlin on Medium, is an open source contributor and has a certification in Android Developer Nanodegreee from Udacity.\r\n\r\nHe has previously given talks at conferences and meetups in Bangalore.",
      "tagLine": "Android Engineer, Gojek",
      "profilePicture": "https://sessionize.com/image?f=ceaa6f14bc8d7c69876fea73bd694a2e,400,400,1,0,70-c13b-4ad5-aa0a-ddb502e5eed6.e852c4c2-acb4-486a-9e57-bc69ed0c3310.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "154003"
      ],
      "fullName": "Jitin Sharma"
    },
    {
      "id": "a92fcca5-0cb3-459d-ac07-56f59dbb0302",
      "firstName": "",
      "lastName": "",
      "bio": "Has been developing for Android since 2013. Works in UI and SDK development.\r\nStudied and worked in Singapore, moved to Japan and Joined Wantedly July 2018",
      "tagLine": "Software Engineer - Wantedly",
      "profilePicture": "https://sessionize.com/image?f=dc2ba29519459365409c2fd8e8778981,400,400,1,0,a5-0cb3-459d-ac07-56f59dbb0302.a34823d3-d591-488b-afea-c20b277041a3.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156201"
      ],
      "fullName": "Malvin Sutanto"
    },
    {
      "id": "ac65e990-2298-41b5-ab5a-49b366a23928",
      "firstName": "",
      "lastName": "",
      "bio": "LINE株式会社でメッセージングアプリ「LINE 」のAndroidアプリを作成しています",
      "tagLine": "LINE Corporation",
      "profilePicture": "https://sessionize.com/image?f=e7e2f697cf0bf24cde4e8f9179b5c148,400,400,1,0,90-2298-41b5-ab5a-49b366a23928.0e09495b-396e-4531-b387-142e1b3a4862.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156246"
      ],
      "fullName": "Tamaki Hidetsugu / Ralph"
    },
    {
      "id": "ad4257bf-d24f-4955-9372-b1642660bf2b",
      "firstName": "",
      "lastName": "",
      "bio": "JB is a geeky mobile expert from Berlin. JB has been with OLX Group for more than three years, two years of which are from the Philippines and the rest from Berlin. Before that he worked for several years in a company that serviced clients such as NFL, HSN and other large media companies. He likes exploring new technologies related to mobile, especially those that can improve developer productivity. JB enjoys experiencing different cultures via food, language, and travel.",
      "tagLine": "Senior Android at OLX Group",
      "profilePicture": "https://sessionize.com/image?f=e7e154b206226b2392a3ef85d2d277de,400,400,1,0,bf-d24f-4955-9372-b1642660bf2b.e41b0983-ea5f-4db6-b73b-5c6149611665.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156912"
      ],
      "fullName": "JB Lorenzo"
    },
    {
      "id": "ad44e7e5-5ba9-427c-a30f-7f447969f213",
      "firstName": "",
      "lastName": "",
      "bio": "Seigo Nonaka is a senior software engineer at Google since 2011.\r\nHe is a member of the Android Framework team, responsible for the platform text rendering since 2015.\r\nIn the past he has worked on Google Japanese Input, Chrome and Chrome OS text input framework.",
      "tagLine": "Google LLC",
      "profilePicture": "https://sessionize.com/image?f=9da3c784086458b682b0adf20b7c2e42,400,400,1,0,a68aff6a-f046-43ed-a53c-6172d568308d.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "155123",
        "155128"
      ],
      "fullName": "Seigo Nonaka"
    },
    {
      "id": "af707fc9-5a94-4d9a-9efe-75885f87f204",
      "firstName": "",
      "lastName": "",
      "bio": "Androidが書けますが、普段は製麺をしています。",
      "tagLine": "ラーメン屋",
      "profilePicture": "https://sessionize.com/image?f=dddaf70ddf73c0331870cafbc6a4e294,400,400,1,0,c9-5a94-4d9a-9efe-75885f87f204.3ff80566-6b9a-4689-bac9-376303a914d1.png",
      "isTopSpeaker": false,
      "sessions": [
        "151629"
      ],
      "fullName": "haru067"
    },
    {
      "id": "b05febee-ba2d-422b-806c-e07dcb9f4600",
      "firstName": "",
      "lastName": "",
      "bio": "I'm a Software Engineer at justInCase, inc. and now contributing to Insurance Application and Insurance API which can enable us to contract next-generation insurance more easily.",
      "tagLine": "Software Engineer at justInCase, Inc.",
      "profilePicture": "https://sessionize.com/image?f=3f488e0e1e39ff806831b733abe45471,400,400,1,0,ee-ba2d-422b-806c-e07dcb9f4600.7586ef8f-657a-4e3b-8e52-a70d36b1d38b.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156932"
      ],
      "fullName": "Yuta Takahashi"
    },
    {
      "id": "b5fe5fdd-6fe4-41cc-b2e7-0ea294d9157f",
      "firstName": "",
      "lastName": "",
      "bio": "日焼けがすごいけどれっきとしたAndroidエンジニアです。\r\n\r\n- Twitter: https://twitter.com/masaibar",
      "tagLine": "株式会社グロービス",
      "profilePicture": "https://sessionize.com/image?f=a3ce89ec16238186ad3ffd42a45c7861,400,400,1,0,ad9b39ad-b73c-45b5-b18b-eb5837071a5f.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "155329"
      ],
      "fullName": "masaibar"
    },
    {
      "id": "b623d9e0-8058-4f2d-a689-fd7130f7cfd5",
      "firstName": "",
      "lastName": "",
      "bio": "株式会社スタディプラス Androidチーム",
      "tagLine": "株式会社スタディプラス",
      "profilePicture": "https://sessionize.com/image?f=f443857fa35a8aa36cf79b639ea20dc0,400,400,1,0,53d619f1-cfdb-44f3-a973-5999afbae307.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156238"
      ],
      "fullName": "Jason"
    },
    {
      "id": "b64b9957-1570-41b7-99b5-b727676ef23c",
      "firstName": "",
      "lastName": "",
      "bio": "Androidアプリのテスト自動化に興味を持っているエンジニアです。「Androidテスト全書」執筆。\r\nDeNAのSWETグループで、Android関連プロジェクトへの自動テスト導入などを行っています。",
      "tagLine": "テスト自動化が好きなAndroidエンジニア",
      "profilePicture": "https://sessionize.com/image?f=07f7c8b5be264c92a98213b02d70f49d,400,400,1,0,74b9b507-524d-41cc-9943-9f6cde3d50fe.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156794"
      ],
      "fullName": "外山純生 (sumio_tym)"
    },
    {
      "id": "b81c21b8-8656-4351-ba07-f1a0cfc83638",
      "firstName": "",
      "lastName": "",
      "bio": "株式会社Diverseにて老舗マッチングサービスYYCのAndroidアプリの開発を担当しています。",
      "tagLine": "Diverse.Inc",
      "profilePicture": "https://sessionize.com/image?f=a2f731144e531cf9a490fa9b1bc167d3,400,400,1,0,8df2d478-0c59-45a3-8307-9b3e73149bc9.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156349"
      ],
      "fullName": "ShimizuAsami"
    },
    {
      "id": "bae43e43-1596-40a5-b20f-5da1a0f0545f",
      "firstName": "",
      "lastName": "",
      "bio": "KotlinとElixirを書いています。Android Oneが好きです。",
      "tagLine": "Software Engineer",
      "profilePicture": "https://sessionize.com/image?f=e3a27202bb035cadc0124a83aeeeacea,400,400,1,0,43-1596-40a5-b20f-5da1a0f0545f.6cb972b8-a567-4f5d-9e06-7d5eb5bea1a0.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "155510"
      ],
      "fullName": "rnakano"
    },
    {
      "id": "bc64bad6-642c-4b19-89df-78236e4d8a2b",
      "firstName": "",
      "lastName": "",
      "bio": "Saiki Iijima is an experienced Android engineer working for Yahoo JAPAN which is one of the biggest search service in Japan.\r\nHe is in charge of auction service app. His team is practicing the extreme programming and the lean software development.\r\nHe has experience at work in game development with C# and in backend development with golang.\r\nHe loves Kotlin, wants to learn more.\r\n\r\nHe talked at\r\n・Devoxx Morocco 2018\r\n・GDG DevFest Tokyo 2018\r\n・DroidKaigi 2019\r\n\r\nOutside of work, basically he reads comics, watches anime, and sometimes enjoy playing basketball.\r\n\r\n",
      "tagLine": "Yahoo JAPAN, AndroidAppDeveloper",
      "profilePicture": "https://sessionize.com/image?f=0ce73d52e05a3e8390d3d81d6d53708d,400,400,1,0,d6-642c-4b19-89df-78236e4d8a2b.cff3f72e-69fd-4dbc-bd62-9817e49a8f0c.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156924"
      ],
      "fullName": "Saiki Iijima"
    },
    {
      "id": "be1c9688-683b-4d8a-a3f5-c74b451d079d",
      "firstName": "",
      "lastName": "",
      "bio": "Izabela has been a Software Engineer on the Android Studio team for 2 years. She works on the Android Gradle Plugin and her main areas of expertise include resources and AAPT2 support. Beside being an engineer, Iza is also a diversity and inclusion activist.",
      "tagLine": "Google",
      "profilePicture": "https://sessionize.com/image?f=261044beabc78962d591d9682bb35731,400,400,1,0,88-683b-4d8a-a3f5-c74b451d079d.6db8393c-ef57-40c3-8b47-2c213ebd4169.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156539"
      ],
      "fullName": "Izabela Orlowska"
    },
    {
      "id": "bfe6396b-fca5-4fca-b118-602d124a7076",
      "firstName": "",
      "lastName": "",
      "bio": "Gaurav is a seasoned mobile dev with 11+ years experience currently working in Maersk(shipping conglomerate). He has worked in healthcare apps for more than six years targeting different spectrum of users right from Radiologists to Wardboys specifically for US & Canada. As an android enthusiast, he is fond of connecting with the community and spends time as a weekend trainer with Edureka for new entrants in mobile ecospace. Gaurav is a regular attendee and participant of GDG BlrDroid at Bangalore.",
      "tagLine": "Lead Android Dev/Enthusiast @ Maersk",
      "profilePicture": "https://sessionize.com/image?f=36a7cbb1ec08f738cda57da2510ef18a,400,400,1,0,6b-fca5-4fca-b118-602d124a7076.972bf959-8f05-4dda-b4f1-37fd737f0dac.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "150650"
      ],
      "fullName": "Gaurav Bhatnagar"
    },
    {
      "id": "c04d8047-0b4d-498b-a416-941e156b5b95",
      "firstName": "",
      "lastName": "",
      "bio": "現在、株式会社ディー・エヌ・エーで\r\nタクシー配車サービスMOVのAndroidアプリの開発を担当\r\n\r\n略歴\r\n~ 2012.3\r\n学生時代\r\n大学では、物理学を専攻\r\n\r\n2012.4 ~ 2018.1\r\n新卒で入社したSIerで某モバイルメーカーを担当\r\nスマホにプリインされるようなアプリの開発を多数経験\r\nAndroidと嫁に出逢う\r\n\r\n2018.1 ~ 現在\r\n株式会社ディー・エヌ・エーに入社\r\nMOVのプロジェクトへ\r\n入社直後に長男爆誕",
      "tagLine": "株式会社ディー・エヌ・エー",
      "profilePicture": "https://sessionize.com/image?f=1bbef5b6bc9c90d4c727ff1cf8519885,400,400,1,0,3dd4a06c-a48b-42c4-8b1d-f129937922d3.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156858"
      ],
      "fullName": "URI"
    },
    {
      "id": "c27d048c-8c02-4de5-84fb-606de1cc2447",
      "firstName": "",
      "lastName": "",
      "bio": "Yahoo! JAPANでAndroidアプリのエンジニアをやってます。Yahoo!天気、Yahoo! JAPANアプリ、Yahoo!ブラウザー、Yahoo!きせかえ、などの開発を担当しています。",
      "tagLine": "Yahoo! JAPAN, Android engineer",
      "profilePicture": "https://sessionize.com/image?f=d721aa4428af17975209947efd500cef,400,400,1,0,8c-8c02-4de5-84fb-606de1cc2447.c497de21-f6a8-4704-8f4e-ed0a5adac1c6.png",
      "isTopSpeaker": false,
      "sessions": [
        "153684"
      ],
      "fullName": "OHMAE Ryosuke"
    },
    {
      "id": "ca18d593-1553-4519-8efc-e900a442ae19",
      "firstName": "",
      "lastName": "",
      "bio": "Software Engineer of Crew Div. Azit Inc. and Catlog Div. RABO Inc. Previously at CATS Div. CyberAgent Inc. and Pairs Div. Eureka Inc.",
      "tagLine": "Software Engineer",
      "profilePicture": "https://sessionize.com/image?f=085c3d7149a668958125b2177ef5267b,400,400,1,0,4425196b-5376-40cf-af50-3f6394bd90a5.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156799"
      ],
      "fullName": "Moyuru Aizawa"
    },
    {
      "id": "cdfa81b3-8739-4c61-8495-11976f5764e7",
      "firstName": "",
      "lastName": "",
      "bio": "マッチング事業を手がけるDiverseで主にFlutter / Androidアプリケーションの開発をしているモバイルアプリケーションエンジニア。\r\n婚活サービスのyoubrideのFlutterでのリプレイスなどを手掛けました。\r\n『Androidテスト全書』(PEAKSさん)の執筆に参加していました。",
      "tagLine": "Diverse, inc.",
      "profilePicture": "https://sessionize.com/image?f=6c3cfe755e4247e3a361740011f0e45d,400,400,1,0,b3-8739-4c61-8495-11976f5764e7.b9bb115f-6ac2-4d8e-8cb1-ec7f712a4c63.png",
      "isTopSpeaker": false,
      "sessions": [
        "155877"
      ],
      "fullName": "kikuchy"
    },
    {
      "id": "ce9bbdc0-5bb5-4d73-aa4e-2ac054f601a0",
      "firstName": "",
      "lastName": "",
      "bio": "ゲームセキュリティ専門会社、株式会社Ninjastarsでセキュリティエンジニアとしてお仕事をしています。\r\nソーシャルゲームを中心としたセキュリティ診断、ゲーム会社・エンジニアに対するセキュリティセミナーなどを行っています。 ",
      "tagLine": "株式会社Ninjastars",
      "profilePicture": "https://sessionize.com/image?f=a884cf312773feba613fff399e721a0d,400,400,1,0,c0-5bb5-4d73-aa4e-2ac054f601a0.def9977b-f4e4-45b2-86ef-e2b05e5a1f31.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "155374"
      ],
      "fullName": "Kenjiro Ichise"
    },
    {
      "id": "d1d3a2be-a29f-4a2c-8a00-cf1e7bbfc567",
      "firstName": "",
      "lastName": "",
      "bio": "Nicole is a Developer Programs Engineer at Google with a focus on privacy and Android core platform APIs. Prior to joining Google, she was a senior Android developer at Smule, and an Android lead at Funzio.",
      "tagLine": "Android DevRel @ Google",
      "profilePicture": "https://sessionize.com/image?f=bd20190c4a4fecce5966b61b6fd7f95d,400,400,1,0,be-a29f-4a2c-8a00-cf1e7bbfc567.c349d058-cc72-4918-a152-d1074e60b1b3.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156528"
      ],
      "fullName": "Nicole Borrelli"
    },
    {
      "id": "d36b8c88-3cca-4895-b7d0-80f1fbdcc307",
      "firstName": "",
      "lastName": "",
      "bio": "software engineer",
      "tagLine": "Android Developer at CyberAgent, Inc",
      "profilePicture": "https://sessionize.com/image?f=ef212b4aa120e4d77690342c6f483ffb,400,400,1,0,e81f61eb-7d70-40de-b35d-e995ec61b132.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156640"
      ],
      "fullName": "Sato Shun"
    },
    {
      "id": "d4d861f4-0319-49d3-8ca1-7778cd2fb6dc",
      "firstName": "",
      "lastName": "",
      "bio": "Tor Norbye is the tech lead for Android Studio at Google, and has worked on many of the features in the IDE (and is also the author of Android Lint.)",
      "tagLine": "Android Studio tech lead",
      "profilePicture": "https://sessionize.com/image?f=16022e8984339f854f145283b16007d0,400,400,1,0,53a86f99-7673-437b-ad07-3434819afdc0.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "154105"
      ],
      "fullName": "Tor Norbye"
    },
    {
      "id": "da1a5096-9f48-4658-a190-b03b6070a431",
      "firstName": "",
      "lastName": "",
      "bio": "スタディプラス株式会社 Androidチーム。",
      "tagLine": "株式会社スタディプラス",
      "profilePicture": "https://sessionize.com/image?f=cfb19ffe0fe4fb2d7a2e7ae2c4d604cb,400,400,1,0,894f9296-3e21-477b-8dff-574b805df1e7.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156238"
      ],
      "fullName": "nacatl"
    },
    {
      "id": "e12d8b0a-762e-4c6e-8c4b-318c434a82d5",
      "firstName": "",
      "lastName": "",
      "bio": "Application Engineer at Hatena\r\n\r\nKotlin lover",
      "tagLine": "Application Engineer at Hatena",
      "profilePicture": "https://sessionize.com/image?f=4f0191ee10adc4dc7f255c57da538009,400,400,1,0,0a-762e-4c6e-8c4b-318c434a82d5.a54cba29-74c1-48e1-9ef2-532a23b965e2.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156805"
      ],
      "fullName": "takuji31"
    },
    {
      "id": "e3267cf5-b1a7-4ce7-a4b3-223925f21316",
      "firstName": "",
      "lastName": "",
      "bio": "Takeshi is an Android engineer working for Google. He is passionate about making developers lives easier by creating reference apps, samples or libraries. To name a couple of examples, he worked on Google I/O app, Google Santa Tracker and Google Authenticator and open source libraries such as flexbox-layout.",
      "tagLine": "Google, Developer Programs Engineer",
      "profilePicture": "https://sessionize.com/image?f=6ba612307afa558cfa5da74f7615834c,400,400,1,0,42e7f8d8-a20a-4433-8023-f02a76d24429.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156785"
      ],
      "fullName": "thagikura"
    },
    {
      "id": "e4b79746-f0d2-4d82-81a1-a1f2a8bc8b4a",
      "firstName": "",
      "lastName": "",
      "bio": "フリーランスエンジニア。2019年9月に合同会社ナクサ設立。学生時代から業務でライブ配信系アプリの開発やサーバサイドの実装を行ってきた。趣味はAOSPを追っかけたり、Androidのバグ探しなど。",
      "tagLine": "NAXA LLC.",
      "profilePicture": "https://sessionize.com/image?f=60d79ff59730ad45e7fe9d374d5d83cd,400,400,1,0,61731d22-9c61-4436-9bc0-cabad65a7517.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "153412"
      ],
      "fullName": "meteor"
    },
    {
      "id": "e57f43fb-f258-4743-b6bf-cb843bc9185a",
      "firstName": "",
      "lastName": "",
      "bio": "合同会社DMM.comで働く、しがないAndroidエンジニアです",
      "tagLine": "有象無象",
      "profilePicture": "https://sessionize.com/image?f=992d469a1a17a88b1a22e96e11d97c07,400,400,1,0,bd0e1ea8-58a0-4f95-861f-ad9eedd2e167.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156392"
      ],
      "fullName": "kgmyshin"
    },
    {
      "id": "e5e38968-5aad-4032-8a96-04d7e624ce36",
      "firstName": "",
      "lastName": "",
      "bio": "動画プレイヤー開発、海外向けゲーム開発を経て、最近はAndroid/iOS/Unityでモバイルアプリ開発しています。",
      "tagLine": "torch Inc, エンジニア",
      "profilePicture": "https://sessionize.com/image?f=b335035762ebe1547f1c2c191b0c3ecd,400,400,1,0,3d1191ee-408e-4ccc-b183-e2bb81caec9e.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156578"
      ],
      "fullName": "keidroid"
    },
    {
      "id": "e7ace08f-10bc-4e0c-a3f1-2471868b5261",
      "firstName": "",
      "lastName": "",
      "bio": "I am an Android developer since last 7 years. Apart from developing Android apps for my employer, I do organize BlrKotlin (India's biggest Kotlin focused user group). I create YouTube videos for my channel \"Chanse Code\" and sometimes blog at chansek.com",
      "tagLine": "Building Android apps for Lowe's",
      "profilePicture": "https://sessionize.com/image?f=578b671b8080c7d783673c937819d6a8,400,400,1,0,c5b2684f-b59a-4ebf-b123-d3ce4ff61329.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "149804"
      ],
      "fullName": "Chandra Sekhar Nayak"
    },
    {
      "id": "ea8b9ce3-c07c-4683-9e4e-81f6680e896d",
      "firstName": "",
      "lastName": "",
      "bio": "Chris is an Android developer working for Gridstone in Melbourne, Australia. He enjoys working on apps with a strong focus on UX.",
      "tagLine": "Android dev who cares about UX",
      "profilePicture": "https://sessionize.com/image?f=2998e63d4792c507343ccca4ca7c2d4a,400,400,1,0,92821a8b-22a5-4fe0-8710-c4be044ffd5f.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156685"
      ],
      "fullName": "Chris Horner"
    },
    {
      "id": "ed1e4545-4547-4697-9515-28f4174f09dd",
      "firstName": "",
      "lastName": "",
      "bio": "Nicolas works on the Android Studio Design Tools and the ConstraintLayout library at Google",
      "tagLine": "Android Studio Design Tools & ConstraintLayout",
      "profilePicture": "https://sessionize.com/image?f=0df7e8a41afdba2339750702675bc3ea,400,400,1,0,275dc363-1403-4697-8520-aa0f7f9b8155.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "156785"
      ],
      "fullName": "Nicolas Roard"
    },
    {
      "id": "f4445e26-7dff-4294-9ae9-321e26e9c183",
      "firstName": "",
      "lastName": "",
      "bio": "Android Developer",
      "tagLine": "Android Developer",
      "profilePicture": "https://sessionize.com/image?f=5686ffa8d0fd2ac7b0a597545a3f00b5,400,400,1,0,26-7dff-4294-9ae9-321e26e9c183.5b0533db-0b3e-4562-abbc-b9a722c2904d.png",
      "isTopSpeaker": false,
      "sessions": [
        "156666"
      ],
      "fullName": "Kenji Abe"
    },
    {
      "id": "fbb08aa9-7aa1-4b70-b676-32905f5fd8d9",
      "firstName": "",
      "lastName": "",
      "bio": "2012 - 2016 Kobe University\r\n2014 - 2015 University of Washington\r\n2015 - 2016 eureka\r\n2017 - abemaTV (currently working)",
      "tagLine": "AbemaTV",
      "profilePicture": "https://sessionize.com/image?f=027abec9aaef7bcc3c8703826939f03e,400,400,1,0,6d444b65-e8d6-4396-b3a1-d6f58c56ca23.jpg",
      "isTopSpeaker": false,
      "sessions": [
        "153686"
      ],
      "fullName": "takusemba"
    }
  ],
  "questions": [
    {
      "id": 16948,
      "question": {
        "ja": "受講対象者",
        "en": "Intended Audience"
      },
      "questionType": "Long_Text",
      "sort": 0
    }
  ],
  "categories": [
    {
      "id": 16925,
      "title": {
        "ja": "セッション時間",
        "en": "Session format"
      },
      "sort": 1,
      "items": [
        {
          "id": 28643,
          "name": {
            "ja": "40分",
            "en": "40min"
          },
          "sort": 0
        }
      ]
    },
    {
      "id": 16928,
      "title": {
        "ja": "言語",
        "en": "Language"
      },
      "sort": 2,
      "items": [
        {
          "id": 28634,
          "name": {
            "ja": "English",
            "en": "English"
          },
          "sort": 1
        },
        {
          "id": 28644,
          "name": {
            "ja": "日本語",
            "en": "日本語"
          },
          "sort": 2
        },
        {
          "id": 28645,
          "name": {
            "ja": "日英混在",
            "en": "Mixed"
          },
          "sort": 3
        }
      ]
    },
    {
      "id": 16929,
      "title": {
        "ja": "カテゴリ",
        "en": "Category"
      },
      "sort": 3,
      "items": [
        {
          "id": 28646,
          "name": {
            "ja": "Kotlin",
            "en": "Kotlin"
          },
          "sort": 4
        },
        {
          "id": 28647,
          "name": {
            "ja": "セキュリティ",
            "en": "Security"
          },
          "sort": 5
        },
        {
          "id": 28648,
          "name": {
            "ja": "UI・UX・デザイン",
            "en": "UI・UX・Design"
          },
          "sort": 6
        },
        {
          "id": 28649,
          "name": {
            "ja": "アプリアーキテクチャ",
            "en": "Designing App Architecture"
          },
          "sort": 7
        },
        {
          "id": 28650,
          "name": {
            "ja": "ハードウェア",
            "en": "Hardware"
          },
          "sort": 8
        },
        {
          "id": 28651,
          "name": {
            "ja": "Androidプラットフォーム",
            "en": "Android Platforms"
          },
          "sort": 9
        },
        {
          "id": 28652,
          "name": {
            "ja": "保守・運用・テスト",
            "en": "Maintenance Operations and Testing"
          },
          "sort": 10
        },
        {
          "id": 28653,
          "name": {
            "ja": "開発体制",
            "en": "Development processes"
          },
          "sort": 11
        },
        {
          "id": 28654,
          "name": {
            "ja": "Android FrameworkとJetpack",
            "en": "Android Framework and Jetpack"
          },
          "sort": 12
        },
        {
          "id": 28655,
          "name": {
            "ja": "開発ツール",
            "en": "Productivity and Tools"
          },
          "sort": 13
        },
        {
          "id": 28656,
          "name": {
            "ja": "クロスプラットフォーム",
            "en": "Cross-platform Development"
          },
          "sort": 14
        },
        {
          "id": 28657,
          "name": {
            "ja": "その他",
            "en": "Other"
          },
          "sort": 15
        }
      ]
    }
  ]
}"""
        val feedContents = Json {
            serializersModule = SerializersModule {
                contextual(InstantSerializer)
            }
            ignoreUnknownKeys = true
        }.decodeFromString<SessionAllResponse>(
            responseText
        )
        println(feedContents)
        SessionContents()
    }
}
