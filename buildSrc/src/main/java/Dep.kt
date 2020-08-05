package io.github.droidkaigi.confsched2021.news

object Dep {
    val klock = "com.soywiz.korlibs.klock:klock:2.0.0-alpha-1.4.0-rc"

    // desktop libraries
    val skia = "org.jetbrains.skija:skija:0.4.1"
    val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.0-rc"
    val kotlinCoroutinesCore
        get() = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
    val kotlinCoroutinesSwing
        get() = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.3.7"
}