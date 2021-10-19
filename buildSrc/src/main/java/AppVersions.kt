package io.github.droidkaigi.feeder

object AppVersions {
    private const val versionMajor = 1
    private const val versionMinor = 1
    private const val versionPatch = 0

    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"
}
