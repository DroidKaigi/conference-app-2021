package io.github.droidkaigi.confsched2021.news

object Dep {
    object Jetpack {
        const val startup = "androidx.startup:startup-runtime:1.0.0"
        const val browser = "androidx.browser:browser:1.3.0"
    }

    object Kotlin {
        const val bom = "org.jetbrains.kotlin:kotlin-bom:1.4.21-2"

        // bom import does not working...
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21-2"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:1.4.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.21-2"
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

    object PlayServices {
        const val plugin = "com.google.gms:google-services:4.3.5"
    }

    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.1.1"
    const val ktlint = "com.pinterest:ktlint:0.40.0"

    object Dagger {
        const val hiltAndroid = "com.google.dagger:hilt-android:2.31-alpha"
        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:2.31-alpha"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.31-alpha"
    }

    object Accompanist {
        const val insets = "dev.chrisbanes.accompanist:accompanist-insets:0.5.0"
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:0.5.0"
    }

    const val firebaseAuth = "dev.gitlive:firebase-auth:1.2.0"
}
