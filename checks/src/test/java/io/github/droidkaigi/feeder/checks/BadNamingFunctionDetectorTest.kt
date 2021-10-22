package io.github.droidkaigi.feeder.checks

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class BadNamingFunctionDetectorTest {
    @Test
    fun test() {
        lint().files(
            kotlin(
                """
fun PreviewSpeakersItem() {
}
fun SpeakersItemPreview() {
}
                """
            )
        )
            .allowMissingSdk()
            .issues(BadNamingFunctionDetector.ISSUE)
            .run()
            .expectWarningCount(1)
    }
}
