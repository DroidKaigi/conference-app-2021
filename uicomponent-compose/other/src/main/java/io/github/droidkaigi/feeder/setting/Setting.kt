package io.github.droidkaigi.feeder.setting
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
            effectFlow,
            dispatch,
        ) = use(settingViewModel())

        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(vertical = 48.dp, horizontal = 20.dp)
        ) {
            AboutThisAppComponent(
                context = context,
                theme = state.theme,
                onChangeTheme = {
                    dispatch(SettingViewModel.Event.ChangeTheme(theme = it))
                }
            )
            Spacer(modifier = Modifier.height(68.dp))
        }
    }
}


@Composable
fun AboutThisAppComponent(
    context: Context,
    theme: Theme?,
    onChangeTheme: (Theme?) -> Unit
) {
    Column {
        if (theme != null) {
            Text(
                text = theme.getTitle(context),
                style = typography.h6
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AlertDialog(
            onChangeTheme = onChangeTheme,
            theme = theme,
            context = context,
        )
    }
}

@Composable
fun AlertDialog(
    onChangeTheme: (Theme?) -> Unit,
    theme: Theme?,
    context: Context
) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false)  }

            Button(onClick = {
                openDialog.value = true
            }) {
                Text("Change theme")
            }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(text = "Theme")
                    },
                    text = {
                        SimpleRadioButtonComponent(
                            onChangeTheme = onChangeTheme,
                            theme = theme,
                            context = context
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("OK")
                        }
                    },
                )
            }
        }

    }
}

@Composable
fun SimpleRadioButtonComponent(
    onChangeTheme: (Theme?) -> Unit,
    theme: Theme?,
    context: Context
) {
    val radioOptions: Array<Theme> = Theme.values()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(theme?.name ?: Theme
        .SYSTEM) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                onChangeTheme(text)
                            }
                        )
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {
                            onOptionSelected(text)
                            onChangeTheme(text)
                        }
                    )
                    Text(
                        text = text.getTitle(context),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                    )
                }
            }
        }
    }
}
