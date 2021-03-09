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
      "id": "dbb2517a-f5fd-05e9-5559-05f519e39098",
      "name": "Aaa Bbb",
      "githubUrl": "https://github.com/droidkaigi_dummay_01",
      "iconUrl": "https://via.placeholder.com/200?text=aaa"
    },
    {
      "id": "0129201b-8e62-a515-447b-ed9036956fb0",
      "name": "Ccc Ddd",
      "githubUrl": null,
      "iconUrl": "https://via.placeholder.com/200?text=ccc"
    },
    {
      "id": "87894d81-40d6-b089-d99e-ca80960b4d22",
      "name": "Eee Fff",
      "githubUrl": "https://github.com/droidkaigi_dummay_03",
      "iconUrl": "https://via.placeholder.com/200?text=eee"
    }
  ]
}"""
        val staffContents = Json {
            serializersModule = SerializersModule {
                contextual(InstantSerializer)
            }
        }.decodeFromString<StaffResponse>(
            responseText
        ).toStaffList()
        staffContents
    }
}
