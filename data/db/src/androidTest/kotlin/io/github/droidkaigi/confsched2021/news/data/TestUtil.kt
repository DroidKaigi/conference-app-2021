package io.github.droidkaigi.confsched2021.news.data

actual fun runBlocking(block: suspend () -> Unit) {
    kotlinx.coroutines.runBlocking { block() }
}
