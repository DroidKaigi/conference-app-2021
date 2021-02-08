package io.github.droidkaigi.confnews2021.data

actual fun runBlocking(block: suspend () -> Unit) {
    kotlinx.coroutines.runBlocking { block() }
}
