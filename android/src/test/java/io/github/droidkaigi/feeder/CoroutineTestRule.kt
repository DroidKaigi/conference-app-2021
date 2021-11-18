package io.github.droidkaigi.feeder

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.withFrameMillis
import androidx.lifecycle.overrideDefaultContext
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.yield
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher() {
    private lateinit var clock: BroadcastFrameClock

    suspend fun awaitFrame(){
        yield()
        clock.awaitFrame()
    }

    override fun starting(description: Description?) {
        super.starting(description)
        clock = BroadcastFrameClock()
        overrideDefaultContext = clock + testDispatcher
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

// from: molecule
private suspend fun BroadcastFrameClock.awaitFrame() {
    // TODO Remove the need for two frames to happen!
    //  I think this is because of the diff-sender is a hot loop that immediately reschedules
    //  itself on the clock. This schedules it ahead of the coroutine which applies changes and
    //  so we need to trigger an additional frame to actually emit the change's diffs.
    repeat(2) {
        coroutineScope {
            launch(start = CoroutineStart.UNDISPATCHED) {
                withFrameMillis { }
            }
            sendFrame(0L)
        }
    }
}
