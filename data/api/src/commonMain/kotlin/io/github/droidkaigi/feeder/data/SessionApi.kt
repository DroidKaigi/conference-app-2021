package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.SessionContents
import io.github.droidkaigi.feeder.Speaker
import io.github.droidkaigi.feeder.TimetableSlot
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
        "ja": "ウェルカムトーク",
        "en": "Welcome Talk"
      },
      "description": null,
      "startsAt": "2021-10-20T10:00:00+09:00",
      "endsAt": "2021-10-20T10:20:00+09:00",
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
        "ja": "DroidKaigiのアプリのアーキテクチャ",
        "en": "DroidKaigi App Architecture"
      },
      "description": "これはディスクリプションです。\nこれはディスクリプションです。\nこれはディスクリプションです。\nこれはディスクリプションです。",
      "startsAt": "2021-10-20T10:20:00+09:00",
      "endsAt": "2021-10-20T11:00:00+09:00",
      "isServiceSession": false,
      "isPlenumSession": false,
      "speakers": [
        "bae43e43-1596-40a5-b20f-5da1a0f0545f", "bc64bad6-642c-4b19-89df-78236e4d8a2b"
      ],
      "roomId": 11511,
      "targetAudience": "For App developer アプリ開発者向け",
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
      "id": "4c7f2d64-9abe-4a40-8825-568c8bf4f4ac",
      "title": {
        "ja": "Closing",
        "en": "Closing"
      },
      "description": null,
      "startsAt": "2021-10-21T18:00:00+09:00",
      "endsAt": "2021-10-21T18:20:00+09:00",
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
      "id": "bae43e43-1596-40a5-b20f-5da1a0f0545C",
      "firstName": "",
      "lastName": "",
      "bio": "Likes Android",
      "tagLine": "Android Engineer",
      "profilePicture": "https://github.com/takahirom.png",
      "isTopSpeaker": false,
      "sessions": [
        "155510"
      ],
      "fullName": "mhi"
    },
    {
      "id": "bae43e43-1596-40a5-b20f-5da1a0f0545f",
      "firstName": "",
      "lastName": "",
      "bio": "Likes Android",
      "tagLine": "Android Engineer",
      "profilePicture": "https://github.com/takahirom.png",
      "isTopSpeaker": false,
      "sessions": [
        "155510"
      ],
      "fullName": "taka"
    },
    {
      "id": "bc64bad6-642c-4b19-89df-78236e4d8a2b",
      "firstName": "",
      "lastName": "",
      "bio": "Likes iOS",
      "tagLine": "iOS Engineer",
      "profilePicture": "https://github.com/ry-itto.png",
      "isTopSpeaker": false,
      "sessions": [
        "156924"
      ],
      "fullName": "ry"
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
            "ja": "25分",
            "en": "25min"
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
        val speakerIdToSpeaker: Map<String, Speaker> = feedContents.speakers!!
            .groupBy { it.id!! }
            .mapValues { (_, apiSpeakers) ->
                apiSpeakers.map { apiSpeaker ->
                    Speaker(apiSpeaker.fullName!!, apiSpeaker.profilePicture!!)
                }.first()
            }
        SessionContents(
            feedContents.sessions.map { apiSession ->
                if (!apiSession.isServiceSession) {
                    TimetableSlot.Session(
                        title = MultiLangText(
                            jaTitle = apiSession.title!!.ja!!,
                            enTitle = apiSession.title.en!!,
                        ),
                        speakers = apiSession.speakers.map { speakerIdToSpeaker[it]!! }
                    )
                } else {
                    TimetableSlot.Special(
                        title = MultiLangText(
                            jaTitle = apiSession.title!!.ja!!,
                            enTitle = apiSession.title.en!!,
                        ),
                        speakers = apiSession.speakers.map { speakerIdToSpeaker[it]!! }
                    )
                }
            }
        )
    }
}
