package io.github.droidkaigi.feeder.timetable2021

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.insets.systemBarsPadding
import io.github.droidkaigi.feeder.Lang
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.TimetableAsset
import io.github.droidkaigi.feeder.TimetableCategory
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.TimetableItemId
import io.github.droidkaigi.feeder.TimetableSpeaker
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.core.animation.FavoriteAnimation
import io.github.droidkaigi.feeder.core.animation.painterFavorite
import io.github.droidkaigi.feeder.core.animation.painterFavoriteBorder
import io.github.droidkaigi.feeder.core.getReadableMessage
import io.github.droidkaigi.feeder.core.language.LocalLangSetting
import io.github.droidkaigi.feeder.core.language.getTextWithSetting
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.use
import io.github.droidkaigi.feeder.core.util.collectInLaunchedEffect
import io.github.droidkaigi.feeder.core.util.createAutoLinkedAnnotateString
import io.github.droidkaigi.feeder.defaultLang
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * stateful
 */
@Composable
fun TimetableDetailScreen(
    id: TimetableItemId,
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
    val item = contents.timetableItems.firstOrNull { it.id == id } ?: return
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
    Conference2021Theme(theme = Theme.SYSTEM) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
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
            },
        ) { innerPadding ->
            BoxWithConstraints(
                modifier = Modifier.systemBarsPadding(
                    top = false,
                    start = false,
                    end = false,
                )
            ) {
                /**
                 * see [Breakpoints](https://material.io/design/layout/responsive-layout-grid.html#breakpoints)
                 */
                val margin = when {
                    maxWidth <= 599.dp -> 16.dp
                    maxWidth <= 904.dp -> 32.dp
                    maxWidth <= 1239.dp -> (maxWidth - 840.dp) / 2
                    maxWidth <= 1439.dp -> 200.dp
                    else -> (maxHeight - 1040.dp) / 2
                } + 8.dp

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState()),
                ) {
                    TimetableDetailSessionInfo(
                        modifier = Modifier.padding(horizontal = margin),
                        state = TimetableDetailSessionInfoState(
                            title = item.title,
                            startsAt = item.startsAt,
                            endsAt = item.endsAt,
                            language = item.language,
                            category = item.category,
                        ),
                    )

                    if (item is TimetableItem.Session) {
                        TimetableDetailDescription(
                            modifier = Modifier.padding(horizontal = margin),
                            description = item.description,
                        )
                    }
                    TimetableDetailTargetAudience(
                        modifier = Modifier.padding(horizontal = margin),
                        targetAudience = item.targetAudience,
                    )
                    TimetableDetailAsset(
                        modifier = Modifier.padding(horizontal = margin),
                        asset = item.asset,
                        onOpenUrl = onOpenUrl,
                    )
                    if (item is TimetableItem.Session) {
                        TimetableDetailSpeakers(
                            modifier = Modifier.padding(horizontal = margin),
                            speakers = item.speakers,
                            onOpenUrl = onOpenUrl,
                        )
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

private data class TimetableDetailSessionInfoState(
    val title: MultiLangText,
    val startsAt: Instant,
    val endsAt: Instant,
    val language: String,
    val category: TimetableCategory,
) {
    val titleWithSetting: String
        @Composable get() = title.getTextWithSetting()

    val dateTime: String = createSessionDate(
        startsAt = startsAt,
        endsAt = endsAt,
    )

    val currentLangLanguage: String
        @Composable get() {
            val lang = if (LocalLangSetting.current == Lang.SYSTEM) defaultLang()
            else LocalLangSetting.current
            return if (lang == Lang.JA) {
                when (language) {
                    "JAPANESE" -> "日本語"
                    "ENGLISH" -> "英語"
                    else -> "未定"
                }
            } else {
                language
            }
        }
}

@Composable
private fun TimetableDetailSessionInfo(
    modifier: Modifier = Modifier,
    state: TimetableDetailSessionInfoState,
) {
    Text(
        modifier = modifier,
        text = state.titleWithSetting,
        style = MaterialTheme.typography.h5,
    )
    Spacer(
        modifier = modifier.height(24.dp),
    )
    Text(
        modifier = modifier,
        text = state.dateTime,
        style = MaterialTheme.typography.body1,
    )
    Spacer(
        modifier = modifier.height(24.dp),
    )
    Row(
        modifier = modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
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
                    text = state.currentLangLanguage,
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
                Image(
                    painter = painterResource(state.category.iconResId),
                    contentDescription = "Category icon",
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp)
                        .align(Alignment.CenterVertically),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = state.category.title.getTextWithSetting(),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
private fun TimetableDetailDescription(
    modifier: Modifier = Modifier,
    description: String,
) {
    Divider(
        modifier = modifier.padding(vertical = 24.dp),
        color = MaterialTheme.colors.onSurface,
    )
    Text(
        modifier = modifier,
        text = description,
        style = MaterialTheme.typography.body1,
    )
}

@Composable
private fun TimetableDetailTargetAudience(
    modifier: Modifier = Modifier,
    targetAudience: String,
) {
    Divider(
        modifier = modifier.padding(vertical = 24.dp),
        color = MaterialTheme.colors.onSurface,
    )
    Text(
        modifier = modifier,
        text = "対象者",
        style = MaterialTheme.typography.subtitle2,
    )
    Spacer(modifier = modifier.height(8.dp))
    Text(
        modifier = modifier,
        text = targetAudience,
        style = MaterialTheme.typography.body2,
    )
}

@Composable
private fun TimetableDetailAsset(
    modifier: Modifier = Modifier,
    asset: TimetableAsset,
    onOpenUrl: (Uri) -> Unit,
) {
    Divider(
        modifier = modifier.padding(
            top = 36.dp,
            bottom = 24.dp,
        ),
        color = MaterialTheme.colors.onSurface,
    )

    val showVideoButton = !asset.videoUrl.isNullOrBlank()
    val showSlidesButton = !asset.slideUrl.isNullOrBlank()
    if (!showVideoButton && !showSlidesButton) {
        return
    }

    BoxWithConstraints {
        val boxMaxWidth = maxWidth

        Row(
            modifier = modifier,
        ) {
            val buttonModifier = when {
                // If there is only one, fill
                boxMaxWidth <= 599.dp -> Modifier.weight(1f)
                else -> Modifier.width(144.dp)
            }

            if (showVideoButton) {
                Button(
                    modifier = buttonModifier,
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
                    Text(text = "MOVIE", color = MaterialTheme.colors.onSecondary)
                }
            }
            if (showVideoButton && showSlidesButton) {
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (showSlidesButton) {
                Button(
                    modifier = buttonModifier,
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
                    Text(text = "SLIDES", color = MaterialTheme.colors.onSecondary)
                }
            }
        }
    }
    Spacer(modifier = modifier.height(24.dp))
}

@Composable
private fun TimetableDetailSpeakers(
    modifier: Modifier = Modifier,
    speakers: List<TimetableSpeaker>,
    onOpenUrl: (Uri) -> Unit,
) {
    Text(
        modifier = modifier,
        text = "Speaker",
        style = MaterialTheme.typography.subtitle2,
    )
    Spacer(modifier = modifier.padding(16.dp))

    speakers.forEach { speaker ->
        if (speaker.iconUrl.isNotEmpty()) {
            Surface(
                modifier = modifier.size(60.dp),
                shape = CircleShape
            ) {
                NetworkImage(
                    url = speaker.iconUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Contributor Icon"
                )
            }
            Spacer(
                modifier = modifier.padding(16.dp),
            )
        }

        Text(
            modifier = modifier,
            text = speaker.name,
            style = MaterialTheme.typography.subtitle1,
        )
        Text(
            modifier = modifier,
            text = speaker.tagLine,
            style = MaterialTheme.typography.subtitle2,
        )
        Spacer(
            modifier = modifier.padding(vertical = 16.dp),
        )
        val styledBio = createAutoLinkedAnnotateString(
            text = speaker.bio,
            textColor = MaterialTheme.colors.onBackground
        )
        ClickableText(
            modifier = modifier,
            text = styledBio,
            style = MaterialTheme.typography.body1,
            onClick = { pos ->
                styledBio.getStringAnnotations(start = pos, end = pos).firstOrNull()
                    ?.let { range ->
                        onOpenUrl(range.item.toUri())
                    }
            }
        )
        Spacer(
            modifier = modifier.padding(vertical = 16.dp),
        )
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

private val TimetableCategory.iconResId: Int
    get() = when (id) {
        89439 -> R.drawable.kotlin
        90050 -> R.drawable.security
        89441 -> R.drawable.ui_ux
        89442 -> R.drawable.architecture
        89443 -> R.drawable.hardware
        89444 -> R.drawable.platform
        89445 -> R.drawable.testing
        89446 -> R.drawable.process
        89447 -> R.drawable.android_framework
        89448 -> R.drawable.jetpack
        89449 -> R.drawable.tools
        89450 -> R.drawable.cross_platform
        89451 -> R.drawable.other
        else -> R.drawable.other
    }

// region default preview

@Preview
@Preview(widthDp = 360)
@Preview(device = Devices.NEXUS_7_2013)
@Preview(device = Devices.NEXUS_10)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTimetableDetailScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideTimetableDetailViewModelFactory { fakeTimetableDetailViewModel() },
        ) {
            TimetableDetailScreen(
                id = TimetableItemId("2"),
                onNavigationIconClick = {},
            )
        }
    }
}

// endregion
