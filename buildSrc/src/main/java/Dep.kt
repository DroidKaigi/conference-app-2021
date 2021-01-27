package io.github.droidkaigi.confsched2021.news

object Dep {
    object Kotlin {
        const val bom = "org.jetbrains.kotlin:kotlin-bom:1.4.30-RC"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:1.4.30-RC"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect"
    }
    object Coroutines {
        const val bom = "org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.4.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core"
    }

    object Serialization {
        const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1"
    }

    object KotlinTest {
        const val common = "org.jetbrains.kotlin:kotlin-test-common"
        const val annotationCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common"
        const val junit = "org.jetbrains.kotlin:kotlin-test-junit"
    }

    object Ktor {
        const val bom = "io.ktor:ktor-bom:1.5.0"
        const val core = "io.ktor:ktor-client-core"
        const val json = "io.ktor:ktor-client-json"
        const val logging = "io.ktor:ktor-client-logging"
        const val okHttp = "io.ktor:ktor-client-okhttp"
        const val serialization = "io.ktor:ktor-client-serialization"
        const val mock = "io.ktor:ktor-client-mock"
    }

    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.1.1"
    const val ktlint = "com.pinterest:ktlint:0.40.0"

    object Dagger {
        const val hiltAndroid = "com.google.dagger:hilt-android:2.31-alpha"
        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:2.31-alpha"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.31-alpha"
    }
}