package com.project.learnasl.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.project.learnasl.Dashboard.components.UserSection
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
fun UserPage(
    username: String,
    experience: Int
){
    LearnASLTheme() {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserSection(username, experience)
        }
    }
}


