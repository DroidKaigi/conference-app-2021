package io.github.droidkaigi.feeder.about

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import io.github.droidkaigi.feeder.core.theme.typography
import io.github.droidkaigi.feeder.other.R

@Preview
@Composable
fun AboutThisApp() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(vertical = 48.dp, horizontal = 20.dp)
    ) {
        AboutThisAppComponent()
        Spacer(modifier = Modifier.height(68.dp))
        WhatIsDroidKaigiComponent()
        Spacer(modifier = Modifier.height(34.dp))
        AboutThisAppMenuListComponent(
            onClickPrivacyPolicy = {},
            onClickLicense = {
                val intent = Intent(context, OssLicensesMenuActivity::class.java)
                context.startActivity(intent)
            },
            appVersion = stringResource(R.string.app_version_name)
        )
    }
}

@Composable
fun AboutThisAppComponent() {
    Column {
        Text(
            text = "このアプリとは",
            style = typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "DroidKaigiが情報を発信するアプリです。",
            style = typography.body1
        )
    }
}

@Composable
fun WhatIsDroidKaigiComponent() {
    Column {
        Text(
            text = "What is DroidKaigi?",
            style = typography.h6
        )
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "DroidKaigi Logo"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("DroidKaigiはエンジニアが主役のAndroidカンファレンスです。Android技術情報の共有とコミュニケーションを目的に、イベントを開催しています。")
    }
}

@Composable
fun AboutThisAppMenuListComponent(
    onClickPrivacyPolicy: () -> Unit,
    onClickLicense: () -> Unit,
    appVersion: String,
) {
    val textModifier = Modifier
        .padding(vertical = 12.dp)
    val itemModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .indication(
            indication = rememberRipple(),
            interactionSource = MutableInteractionSource()
        )

    Column {
        Divider()
        Row(
            modifier = itemModifier
                .clickable(onClick = onClickPrivacyPolicy)
        ) {
            Text(
                modifier = textModifier,
                text = "プライバシーポリシー",
                style = typography.body1
            )
        }

        Divider()
        Row(
            modifier = itemModifier
                .clickable(onClick = onClickLicense)
        ) {
            Text(
                modifier = textModifier,
                text = "ライセンス",
                style = typography.body1
            )
        }

        Divider()
        Row(modifier = itemModifier) {
            Text(
                modifier = textModifier
                    .width(0.dp)
                    .weight(1f),
                text = "アプリバージョン",
                style = typography.body1
            )
            Text(
                modifier = textModifier,
                text = appVersion,
                color = Color.Gray,
                style = typography.body1
            )
        }
    }
}
