package io.github.droidkaigi.feeder.timetable2021

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import io.github.droidkaigi.feeder.TimetableAsset
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.TimetableSpeaker
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.core.animation.FavoriteAnimation
import io.github.droidkaigi.feeder.core.animation.painterFavorite
import io.github.droidkaigi.feeder.core.animation.painterFavoriteBorder
import io.github.droidkaigi.feeder.core.getReadableMessage
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.use
import io.github.droidkaigi.feeder.core.util.collectInLaunchedEffect
import io.github.droidkaigi.feeder.currentLangTitle
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

/**
 * stateful
 */
@Composable
fun TimetableDetailScreen(
    id: String,
    onNavigationIconClick: () -> Unit,
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(timetableDetailViewModel())

    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is TimetableDetailViewModel.Effect.ErrorMessage -> {
                when (
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = effect.appError.getReadableMessage(context),
                    )
                ) {
                    SnackbarResult.ActionPerformed -> {
                    }
                    SnackbarResult.Dismissed -> {
                    }
                }
            }
        }
    }

    val contents = state.timetableContents
    val item = contents.timetableItems.first { it.id == id }
    val isFavorite = contents.favorites.contains(id)

    TimetableDetailScreen(
        item = item,
        isFavorite = isFavorite,
        onNavigationIconClick = onNavigationIconClick,
        toggleFavorite = {
            dispatch(TimetableDetailViewModel.Event.ToggleFavorite(timetableItem = item))
        },
        onOpenUrl = {
            val intent = Intent(Intent.ACTION_VIEW, it)
            startActivity(context, intent, null)
        }
    )
}

/**
 * stateless
 */
@Composable
fun TimetableDetailScreen(
    item: TimetableItem,
    isFavorite: Boolean,
    onNavigationIconClick: () -> Unit,
    toggleFavorite: (Boolean) -> Unit,
    onOpenUrl: (Uri) -> Unit,
) {
    Conference2021Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        // empty
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigationIconClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        FavoriteIcon(
                            isFavorite = isFavorite,
                            toggleFavorite = toggleFavorite,
                        )
                    },
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 0.dp
                )
            }
        ) {
            BoxWithConstraints {
                /**
                 * see [Breakpoints](https://material.io/design/layout/responsive-layout-grid.html#breakpoints)
                 */
                val margin = when {
                    maxWidth < 599.dp -> 16.dp
                    maxWidth < 904.dp -> 32.dp
                    maxWidth < 1239.dp -> (maxWidth - 840.dp) / 2
                    maxWidth < 1439.dp -> 200.dp
                    else -> (maxHeight - 1040.dp) / 2
                } + 8.dp

                Column(modifier = Modifier.padding(horizontal = margin)) {
                    TimetableDetailSessionInfo(
                        title = item.title.currentLangTitle,
                        dateTime = createSessionDate(
                            startsAt = item.startsAt,
                            endsAt = item.endsAt,
                        ),
                        language = item.language.currentLangTitle(),
                        category = item.category.title.currentLangTitle,
                    )

                    if (item is TimetableItem.Session) {
                        TimetableDetailDescription(description = item.description)
                    }
                    TimetableDetailTargetAudience(targetAudience = item.targetAudience)
                    Divider(
                        modifier = Modifier.padding(
                            top = 36.dp,
                            bottom = 24.dp,
                        ),
                        color = MaterialTheme.colors.onSurface,
                    )
                    TimetableDetailAsset(
                        asset = item.asset,
                        onOpenUrl = onOpenUrl,
                    )
                    if (item is TimetableItem.Session) {
                        TimetableDetailSpeakers(speakers = item.speakers)
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteIcon(
    isFavorite: Boolean,
    toggleFavorite: (Boolean) -> Unit,
) {
    ConstraintLayout {
        val (
            favorite,
            favoriteAnim,
        ) = createRefs()

        FavoriteAnimation(
            visible = isFavorite,
            modifier = Modifier
                .constrainAs(favoriteAnim) {
                    width = Dimension.value(80.dp)
                    height = Dimension.value(80.dp)
                    start.linkTo(favorite.start)
                    end.linkTo(favorite.end)
                    bottom.linkTo(favorite.bottom)
                }
        )
        IconToggleButton(
            checked = false,
            modifier = Modifier.constrainAs(favorite) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            content = {
                if (isFavorite) {
                    Icon(
                        painter = painterFavorite(),
                        contentDescription = "favorite",
                        modifier = Modifier.testTag("Favorite"),
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        painter = painterFavoriteBorder(),
                        contentDescription = "favorite",
                        modifier = Modifier.testTag("NotFavorite")
                    )
                }
            },
            onCheckedChange = toggleFavorite,
        )
    }
}

@Composable
private fun TimetableDetailSessionInfo(
    title: String,
    dateTime: String,
    language: String,
    category: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h5,
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        text = dateTime,
        style = MaterialTheme.typography.body1,
    )
    Spacer(modifier = Modifier.height(24.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentHeight(),
    ) {
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        1.dp,
                        if (MaterialTheme.colors.isLight) {
                            Color.Black
                        } else {
                            Color.White
                        },
                    ),
                    shape = RoundedCornerShape(50)
                )
                .padding(vertical = 2.dp, horizontal = 16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.wrapContentHeight(),
            ) {
                Icon(
                    imageVector = Icons.Default.Translate,
                    contentDescription = "Translate icon",
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp)
                        .align(Alignment.CenterVertically),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = language,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        1.dp,
                        if (MaterialTheme.colors.isLight) {
                            Color.Black
                        } else {
                            Color.White
                        },
                    ),
                    shape = RoundedCornerShape(50)
                )
                .padding(vertical = 2.dp, horizontal = 16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.wrapContentHeight(),
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
private fun TimetableDetailDescription(description: String) {
    Divider(
        modifier = Modifier.padding(vertical = 24.dp),
        color = MaterialTheme.colors.onSurface,
    )
    Text(
        text = description,
        style = MaterialTheme.typography.body1,
    )
}

@Composable
private fun TimetableDetailTargetAudience(targetAudience: String) {
    Divider(
        modifier = Modifier.padding(vertical = 24.dp),
        color = MaterialTheme.colors.onSurface,
    )
    Text(
        text = "対象者",
        style = MaterialTheme.typography.subtitle2,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = targetAudience,
        style = MaterialTheme.typography.body2,
    )
}

@Composable
private fun TimetableDetailAsset(
    asset: TimetableAsset,
    onOpenUrl: (Uri) -> Unit,
) {
    val showVideoButton = !asset.videoUrl.isNullOrBlank()
    val showSlidesButton = !asset.slideUrl.isNullOrBlank()
    if (!showVideoButton && !showSlidesButton) {
        return
    }

    Row {
        if (showVideoButton) {
            Button(
                modifier = Modifier.width(144.dp),
                colors = buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary,
                ),
                onClick = {
                    onOpenUrl(asset.videoUrl!!.toUri())
                }
            ) {
                Icon(Icons.Outlined.Videocam, contentDescription = "Open Video")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "MOVIE")
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        if (showSlidesButton) {
            Button(
                modifier = Modifier.width(144.dp),
                colors = buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary,
                ),
                onClick = {
                    onOpenUrl(asset.slideUrl!!.toUri())
                }
            ) {
                Icon(Icons.Outlined.PhotoLibrary, contentDescription = "Open Slides")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "SLIDES")
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun TimetableDetailSpeakers(
    speakers: List<TimetableSpeaker>,
) {
    Text(
        text = "Speaker",
        style = MaterialTheme.typography.subtitle2,
    )
    Spacer(modifier = Modifier.padding(16.dp))

    speakers.forEach { speaker ->
        Column {
            if (speaker.iconUrl.isNotEmpty()) {
                Surface(
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape
                ) {
                    NetworkImage(
                        url = speaker.iconUrl,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        contentDescription = "Contributor Icon"
                    )
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }

            Text(
                text = speaker.name,
                style = MaterialTheme.typography.subtitle1,

                )
            Text(
                text = speaker.tagLine,
                style = MaterialTheme.typography.subtitle2,
            )
            Spacer(Modifier.padding(vertical = 16.dp))
            Text(
                text = speaker.bio,
                style = MaterialTheme.typography.body1,
            )
            Spacer(Modifier.padding(vertical = 16.dp))
        }
    }
}

private fun createSessionDate(
    startsAt: Instant,
    endsAt: Instant,
): String {
    val start = startsAt.toLocalDateTime(currentSystemDefault()).toJavaLocalDateTime()
    val end = endsAt.toLocalDateTime(currentSystemDefault()).toJavaLocalDateTime()
    val day = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(start)
    val startTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(start)
    val endTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(end)

    return "$day $startTime-$endTime"
}

// region default preview

@Preview
@Composable
fun PreviewTimetableDetailScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideTimetableDetailViewModelFactory { fakeTimetableDetailViewModel() },
        ) {
            TimetableDetailScreen(
                id = "2",
                onNavigationIconClick = {},
            )
        }
    }
}

// endregion

// region optional preview

@Preview(widthDp = 360)
@Composable
fun PreviewSmallTimetableDetailScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideTimetableDetailViewModelFactory { fakeTimetableDetailViewModel() },
        ) {
            TimetableDetailScreen(
                id = "2",
                onNavigationIconClick = {}
            )
        }
    }
}

@Preview(device = Devices.NEXUS_7_2013)
@Composable
fun PreviewTabletTimetableDetailScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideTimetableDetailViewModelFactory { fakeTimetableDetailViewModel() },
        ) {
            TimetableDetailScreen(
                id = "2",
                onNavigationIconClick = {}
            )
        }
    }
}

@Preview(device = Devices.NEXUS_10)
@Composable
fun PreviewLargeTabletTimetableDetailScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideTimetableDetailViewModelFactory { fakeTimetableDetailViewModel() },
        ) {
            TimetableDetailScreen(
                id = "2",
                onNavigationIconClick = {}
            )
        }
    }
}

// endregion
