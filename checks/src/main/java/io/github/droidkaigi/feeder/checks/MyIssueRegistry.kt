package io.github.droidkaigi.feeder.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

@Suppress("UnstableApiUsage")
class MyIssueRegistry : IssueRegistry() {
    override val issues = listOf(BadNamingFunctionDetector.ISSUE)

    override val api: Int
        get() = CURRENT_API

    override val vendor: Vendor = Vendor(
        vendorName = "DroidKaigi",
        feedbackUrl = "https://github.com/DroidKaigi/conference-app-2021/issues",
        contact = "https://github.com/DroidKaigi/conference-app-2021"
    )
}
