package androidx.lifecycle

import app.cash.molecule.AndroidUiDispatcher
import java.io.Closeable
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

private const val JOB_KEY = "io.github.droidkaigi.feeder.ViewModelCoroutineScope.JOB_KEY"

val defaultDispatcher by lazy { AndroidUiDispatcher.Main }
var overrideDefaultContext: CoroutineContext? = null

public val ViewModel.viewModelScopeWithClock: CoroutineScope
    get() {
        val scope: CoroutineScope? = this.getTag(JOB_KEY)
        if (scope != null) {
            return scope
        }
        return setTagIfAbsent(
            JOB_KEY,
            CloseableCoroutineScope(
                SupervisorJob() +
                    (overrideDefaultContext ?: defaultDispatcher)
            )
        )
    }

internal class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}
