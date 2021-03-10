package io.github.droidkaigi.feeder.setting
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.theme.getTitle
import io.github.droidkaigi.feeder.core.theme.typography
import io.github.droidkaigi.feeder.core.use

@Preview
@Composable
fun Settings() {
    ProvideSettingViewModel(viewModel = settingViewModel()) {
        val (
            state,
            _,
            dispatch,
        ) = use(settingViewModel())
        val context = LocalContext.current

        Surface(
            color = MaterialTheme.colors.background,
        ) {
            Theme(
                context = context,
                theme = state.theme,
                onClick = {
                    dispatch(SettingViewModel.Event.ChangeTheme(theme = it))
                }
            )
        }
    }
}

@Composable
fun Theme(
    context: Context,
    theme: Theme?,
    onClick: (Theme?) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                openDialog.value = true
            }
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Text(
            text = "Theme",
            style = typography.h5,
            modifier = Modifier.padding(start = 36.dp),
        )
        Text(
            text = theme.getTitle(context),
            style = TextStyle(
                color = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth().padding(end = 36.dp),
            textAlign = TextAlign.Right
        )
        if (openDialog.value) {
            ThemeSelectDialog(
                onChangeTheme = onClick,
                theme = theme,
                context = context,
                onClick = {
                    openDialog.value = false
                }
            )
        }
    }
}

@Composable
fun ThemeSelectDialog(
    onChangeTheme: (Theme?) -> Unit,
    theme: Theme?,
    context: Context,
    onClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClick,
        title = {
            Text(text = "Theme")
        },
        text = {
            ThemeSelectRadioButton(
                onChangeTheme = onChangeTheme,
                theme = theme,
                context = context
            )
        },
        confirmButton = {
            Button(
                onClick = onClick
            ) {
                Text("OK")
            }
        },
    )
}

@Composable
fun ThemeSelectRadioButton(
    onChangeTheme: (Theme?) -> Unit,
    theme: Theme?,
    context: Context
) {
    val themes: List<Theme> = Theme.values().toList()
    var defaultIndex = 0
    themes.forEachIndexed { index, it -> if (it == theme) defaultIndex = index }
    val (selectedTheme, oThemeSelected) = remember { mutableStateOf(themes[defaultIndex]) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            themes.forEach { theme ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (theme == selectedTheme),
                            onClick = {
                                oThemeSelected(theme)
                                onChangeTheme(theme)
                            }
                        )
                ) {
                    RadioButton(
                        selected = (theme == selectedTheme),
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            oThemeSelected(theme)
                            onChangeTheme(theme)
                        }
                    )
                    Text(
                        text = theme.getTitle(context),
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}
