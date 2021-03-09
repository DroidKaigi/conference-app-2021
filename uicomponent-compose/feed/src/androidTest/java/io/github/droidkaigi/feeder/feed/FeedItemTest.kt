package io.github.droidkaigi.feeder.feed

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.fakeFeedContents
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showFeedItem_clickListenerRegistered() {
        val feedItem = fakeFeedContents().contents[0].first

        composeTestRule.setContent {
            ConferenceAppFeederTheme {
                FeedItem(
                    feedItem = feedItem,
                    favorited = true,
                    showMediaLabel = true,
                    onClick = { },
                    onFavoriteChange = { }
                )
            }
        }

        composeTestRule.onNodeWithTag("FeedItem").assertHasClickAction()
    }
}
