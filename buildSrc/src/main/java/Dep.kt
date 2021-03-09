package io.github.droidkaigi.feeder

object Versions {
    const val compose = "1.0.0-beta01"
}

object Dep {
    object Compose {
        const val activity = "androidx.activity:activity-compose:1.3.0-alpha02"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha03"
        const val navigation = "androidx.navigation:navigation-compose:1.0.0-alpha08"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val test = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val util = "androidx.compose.ui:ui-util:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}1"
        const val iconsExtended =
            "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val animation = "androidx.compose.animation:animation:${Versions.compose}"
    }

    object Jetpack {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val startup = "androidx.startup:startup-runtime:1.0.0"
        const val browser = "androidx.browser:browser:1.3.0"
        const val dataStore = "androidx.datastore:datastore-preferences:1.0.0-alpha06"
    }

    object MultiplatformSettings {
        const val settings = "com.russhwolf:multiplatform-settings:0.7.2"
        const val coroutines = "com.russhwolf:multiplatform-settings-coroutines:0.7.2"
        const val datastore = "com.russhwolf:multiplatform-settings-datastore:0.7.2"
        const val test = "com.russhwolf:multiplatform-settings-test:0.7.2"
    }

    object Kotlin {
        const val bom = "org.jetbrains.kotlin:kotlin-bom:1.4.30"

        // bom import does not working...
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:1.4.30"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.30"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect"
    }

    object Coroutines {
        const val bom = "org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.4.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2"
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
        const val bom = "io.ktor:ktor-bom:1.5.2"
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
        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:2.31.2-alpha"
        const val hiltAndroid = "com.google.dagger:hilt-android:2.31.2-alpha"
        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:2.31.2-alpha"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.31.2-alpha"
    }

    object Accompanist {
        const val insets = "dev.chrisbanes.accompanist:accompanist-insets:0.6.0"
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:0.6.0"
    }

    object FirebaseCrashlytics {
        const val plugin = "com.google.firebase:firebase-crashlytics-gradle:2.5.0"
        const val android = "com.google.firebase:firebase-crashlytics:17.3.1"
    }

    const val firebaseAuth = "dev.gitlive:firebase-auth:1.2.0"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx:21.0.1"

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.5.0-beta01"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0"
        const val activity = "androidx.activity:activity-ktx:1.2.0"
        const val lifecycle = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.0"
        const val hiltCommon = "androidx.hilt:hilt-common:1.0.0-alpha03"
        const val hiltLifeCycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"

        object Test {
            const val ext = "androidx.test.ext:junit:1.1.2"
            const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }

    object SQLDelight {
        const val plugin = "com.squareup.sqldelight:gradle-plugin:1.4.4"
        const val androidDriver = "com.squareup.sqldelight:android-driver:1.4.4"
        const val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:1.4.4"
    }

    object Flipper {
        // TODO We use Flipper 0.76.0 for now because app crashes with 0.77.0 and 0.78.0.
        //      We can upgrade it to newer version once following issue is resolved.
        //      https://github.com/facebook/flipper/issues/1968
        const val flipper = "com.facebook.flipper:flipper:0.76.0"
        const val networkPlugin = "com.facebook.flipper:flipper-network-plugin:0.76.0"
        const val soLoader = "com.facebook.soloader:soloader:0.10.1"
    }
}
