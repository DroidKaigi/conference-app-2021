package io.github.droidkaigi.feeder.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod

@Suppress("UnstableApiUsage")
class BadNamingFunctionDetector : Detector(), SourceCodeScanner {
    companion object {
        const val PREFIX = "Preview"
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "BadNamingFunction",
            briefDescription = "Bad naming function",
            explanation = """
                    This checks for incorrect naming function.
                    """,
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                BadNamingFunctionDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(UMethod::class.java)
    }

    override fun createUastHandler(context: JavaContext) = MethodNamingHandler(context)

    class MethodNamingHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            if (node.name.contains(PREFIX) && node.name.startsWith(PREFIX).not()) {
                context.report(
                    ISSUE,
                    node,
                    context.getNameLocation(node),
                    "The name of function for preview must start with \"Preview\".")
            }
        }
    }
}
