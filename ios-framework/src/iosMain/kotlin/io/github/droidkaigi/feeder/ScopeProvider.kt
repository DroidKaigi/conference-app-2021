package io.github.droidkaigi.feeder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

interface ScopeProvider {
    val scope: CoroutineScope
}

class MainScopeProvider : ScopeProvider {
    override val scope: CoroutineScope = MainScope()
}
