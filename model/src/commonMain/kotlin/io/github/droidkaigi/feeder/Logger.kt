package io.github.droidkaigi.feeder

// FIXME: replace with library
object Logger {
    fun init() {
        // for future changes
    }

    fun d(message: String) = println(message)
    fun d(e: Throwable, message: String) {
        println(message)
        e.printStackTrace()
    }
}
