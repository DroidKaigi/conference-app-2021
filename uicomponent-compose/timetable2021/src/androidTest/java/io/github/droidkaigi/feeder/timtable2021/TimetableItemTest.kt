package io.github.droidkaigi.feeder.timtable2021

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.fakeTimetableContents
import io.github.droidkaigi.feeder.timetable2021.TimetableItem
import io.github.droidkaigi.feeder.timetable2021.TimetableItemState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimetableItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showTimetableItem_clickListenerRegistered() {
        val timetableItem = fakeTimetableContents().timetableItems[0]

        composeTestRule.setContent {
            TimetableItem(TimetableItemState(timetableItem, true), {}, {}, true)
        }

        composeTestRule.onNodeWithTag("TimetableItem").assertHasClickAction()
    }

    @Test
    fun showTimetableItem_isSpeakersNotDisplayed() {
        val timetableItem = fakeTimetableContents().timetableItems[0]

        composeTestRule.setContent {
            TimetableItem(TimetableItemState(timetableItem, true), {}, {}, true)
        }

        composeTestRule.onNodeWithTag("Speakers", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("Speakers icon", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun showTimetableItem_isSpeakersDisplayed() {
        val timetableItem = fakeTimetableContents().timetableItems[1]

        composeTestRule.setContent {
            TimetableItem(TimetableItemState(timetableItem, true), {}, {}, true)
        }

        composeTestRule.onNodeWithTag("Speakers", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun showTimetableItem_isFavoriteButtonDisplayed() {
        val timetableItem = fakeTimetableContents().timetableItems[0]

        composeTestRule.setContent {
            TimetableItem(TimetableItemState(timetableItem, true), {}, {}, true)
        }

        composeTestRule.onNodeWithTag("Favorite", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("NotFavorite", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun showTimetableItem_isNotFavoriteButtonDisplayed() {
        val timetableItem = fakeTimetableContents().timetableItems[0]

        composeTestRule.setContent {
            TimetableItem(TimetableItemState(timetableItem, false), {}, {}, true)
        }

        composeTestRule.onNodeWithTag("Favorite", useUnmergedTree = true).assertDoesNotExist()
        composeTestRule.onNodeWithTag("NotFavorite", useUnmergedTree = true).assertIsDisplayed()
    }
}
