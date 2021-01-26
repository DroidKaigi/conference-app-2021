package io.github.droidkaigi.confsched2021.news.data

import kotlinx.datetime.Instant
import kotlin.test.Test

class GenerateFakeContents {
    @Test
    fun generate() = runBlocking {
        val newsList = fakeNewsApi()
            .fetch()
        val format = build(newsList)

        println(format)
    }

    fun build(value: Any): String {
        return when (value) {
            is List<*> -> {
                value.joinToString { build(it!!) }
            }
            is Number -> {
                value.toString()
            }
            is String -> {
                "\"\"\"" + value.toString() + "\"\"\""
            }
            is Instant -> {
                "Instant.fromEpochMilliseconds(" + value.toEpochMilliseconds() + ")"
            }
            else -> {
                val kClass = value::class
                val primaryConstructor = kClass.constructors.toList()[0]
                kClass.qualifiedName + "(" +
                    primaryConstructor.parameters.joinToString(",") { parameter ->
                        parameter.name + "=" + build(
                            kClass.members.first { member ->
                                parameter.name == member.name
                            }.call(value)!!
                        )
                    } + ")"
            }
        }
    }
}