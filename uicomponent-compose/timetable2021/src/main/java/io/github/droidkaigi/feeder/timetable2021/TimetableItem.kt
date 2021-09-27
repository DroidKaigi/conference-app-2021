package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.fakeTimetableContents

data class TimetableItemState(val timetableItem: TimetableItem, val favorited: Boolean)

@Composable
fun TimetableItem(
    timetableItemState: TimetableItemState,
    onDetailClick: (String) -> Unit,
    onFavoriteChange: (TimetableItem) -> Unit,
    showDivider: Boolean
) {
    Column() {
        if (showDivider) Divider()
        TimetableItemContent(
            timetableItem = timetableItemState.timetableItem,
            favorited = timetableItemState.favorited,
            onDetailClick = onDetailClick,
            onFavoriteChange = onFavoriteChange
        )
    }
}

@Composable
private fun TimetableItemContent(
    timetableItem: TimetableItem,
    favorited: Boolean,
    onDetailClick: (String) -> Unit,
    onFavoriteChange: (TimetableItem) -> Unit,
) {
    ConstraintLayout(
        Modifier
            .clickable(
                onClick = {
                    onDetailClick(timetableItem.id)
                }
            )
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
            .fillMaxWidth()
            .testTag("TimetableItem")
    ) {
        val (startsAt, title, speakers, favoriteSpacer, favorite) = createRefs()

        Text(
            timetableItem.startsTimeString,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
            modifier = Modifier.constrainAs(startsAt) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )
        Text(
            timetableItem.title.currentLangTitle,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.constrainAs(title) {
                end.linkTo(parent.end, 48.dp)
                start.linkTo(startsAt.end, 16.dp)
                top.linkTo(startsAt.top)
                width = Dimension.fillToConstraints
            }
        )

        if (timetableItem is TimetableItem.Session) {
            Column(
                modifier = Modifier
                    .constrainAs(speakers) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(title.end)
                        start.linkTo(title.start)
                        top.linkTo(title.bottom, 8.dp)
                        width = Dimension.fillToConstraints
                    }
                    .testTag("Speakers")
            ) {
                timetableItem.speakers.forEachIndexed { index, speaker ->
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (speaker.iconUrl != null) {
                            NetworkImage(
                                url = speaker.iconUrl!!,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .testTag("Speakers icon"),
                                contentScale = ContentScale.Fit,
                                contentDescription = "speakers icon"
                            )
                        } else {
                            Image(
                                painter = painterResource(
                                    io.github.droidkaigi.feeder.core.R.drawable.droid_placeholder
                                ),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Fit,
                                contentDescription = "speakers icon"
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            speaker.name,
                            style = TextStyle(fontSize = 12.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.constrainAs(favoriteSpacer) {
                bottom.linkTo(favorite.top)
                top.linkTo(title.bottom)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            }
        )

        IconToggleButton(
            checked = favorited,
            modifier = Modifier.constrainAs(favorite) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                top.linkTo(favoriteSpacer.bottom)
            },
            content = {
                if (favorited) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_favorite_24),
                        contentDescription = "favorite",
                        modifier = Modifier.testTag("Favorite"),
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_favorite_border_24),
                        contentDescription = "favorite",
                        modifier = Modifier.testTag("NotFavorite")
                    )
                }
            },
            onCheckedChange = {
                onFavoriteChange(timetableItem)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimetableItem() {
    Column() {
        fakeTimetableContents().timetableItems.timetableItems.forEachIndexed { index, item ->
            TimetableItem(TimetableItemState(item, true), {}, {}, index > 0)
        }
    }
}
