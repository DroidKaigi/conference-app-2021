package io.github.droidkaigi.feeder.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.feeder.core.theme.typography

@Preview
@Composable
fun AboutThisApp() {
    Column(
        modifier = Modifier
            .padding(vertical = 48.dp, horizontal = 20.dp)
    ) {
        AboutThisAppComponent()
        Spacer(modifier = Modifier.height(68.dp))
        WhatIsDroidKaigiComponent()
    }
}

@Composable
fun AboutThisAppComponent() {
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

@Composable
fun WhatIsDroidKaigiComponent() {
    Text(
        text = "What is DroidKaigi?",
        style = typography.h6
    )
    Spacer(modifier = Modifier.height(24.dp))
    // TODO: Replace DroidKaigi Logo
    Text("ToDo: Replace me to Logo image")
    Spacer(modifier = Modifier.height(24.dp))
    Text("DroidKaigiはエンジニアが主役のAndroidカンファレンスです。Android技術情報の共有とコミュニケーションを目的に、イベントを開催しています。")
}
