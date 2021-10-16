package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.data.response.InstantSerializer
import io.github.droidkaigi.feeder.data.response.StaffResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

interface StaffApi {
    suspend fun fetch(): List<Staff>
}

fun fakeStaffApi(error: AppError? = null): StaffApi = object : StaffApi {
    override suspend fun fetch(): List<Staff> {
        if (error != null) {
            throw error
        }
        return list
    }

    // cache for fixing test issue
    @OptIn(ExperimentalStdlibApi::class)
    val list: List<Staff> = run {
        val responseText = """{
  "status": "OK",
  "staff": [
    {
      "id": 10,
      "username": "Aaa Bbb",
      "profileUrl": "https://github.com/droidkaigi_dummay_01",
      "iconUrl": "https://via.placeholder.com/200?text=aaa"
    },
    {
      "id": 11,
      "username": "Ccc Ddd",
      "profileUrl": null,
      "iconUrl": "https://via.placeholder.com/200?text=ccc"
    },
    {
      "id": 12,
      "username": "Eee Fff",
      "profileUrl": "https://github.com/droidkaigi_dummay_03",
      "iconUrl": "https://via.placeholder.com/200?text=eee"
    }
  ]
}"""
        val staffContents = Json {
            serializersModule = SerializersModule {
                contextual(InstantSerializer)
            }
            coerceInputValues = true
        }.decodeFromString<StaffResponse>(
            responseText
        ).toStaffList()
        staffContents
    }
}
