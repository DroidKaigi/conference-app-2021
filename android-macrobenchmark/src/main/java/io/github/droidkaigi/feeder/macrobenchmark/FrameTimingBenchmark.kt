/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.droidkaigi.feeder.macrobenchmark

import android.content.Intent
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@SdkSuppress(minSdkVersion = 29)
@RunWith(AndroidJUnit4::class)
class FrameTimingBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun start() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        benchmarkRule.measureRepeated(
            packageName = "io.github.droidkaigi.feeder.debug",
            metrics = listOf(FrameTimingMetric()),
            // Try switching to different compilation modes to see the effect
            // it has on frame timing metrics.
            compilationMode = CompilationMode.None,
            iterations = 10,
            setupBlock = {
                // Before starting to measure, navigate to the UI to be measured
                val intent = Intent()
                intent.setClassName("io.github.droidkaigi.feeder.debug","io.github.droidkaigi" +
                    ".feeder.MainActivity")
                startActivityAndWait(intent)
            }
        ) {
            val lazyColumn = device.findObject(By.scrollable(true))
            device.dumpWindowHierarchy(System.out)
            device.dumpWindowHierarchy(System.err)
            // Set gesture margin to avoid triggering gesture navigation
            // with input events from automation.
            lazyColumn.setGestureMargin(device.displayWidth / 5)
            for (i in 1..3) {
                lazyColumn.scroll(Direction.DOWN, 2f)
                device.waitForIdle()
            }
        }
    }

    companion object {
        private const val RESOURCE_ID = "recycler"
    }
}
