package com.project.learnasl.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.learnasl.ui.theme.LearnASLTheme


// placeholder
// TODO()
@Composable
fun SettingsPage(
    username: String,
    experience: Int,
    level: Int
){
    LearnASLTheme() {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}


@Composable
@Preview(showBackground = true)
fun SettingsPagePreview() {
    SettingsPage(username = "Nick",
        experience = 300,
        level = 4)
}


