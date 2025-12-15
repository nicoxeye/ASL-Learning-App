package com.project.learnasl.pages

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.learnasl.Dashboard.components.Banner
import com.project.learnasl.Dashboard.components.CardGrid
import com.project.learnasl.Dashboard.components.Header
import com.project.learnasl.Dashboard.components.LearningModesButtons
import com.project.learnasl.Dashboard.components.UserSection
import com.project.learnasl.match.data.allLettersAslPairs
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.startAlphabetModeActivity
import com.project.learnasl.utils.startCameraActivity
import com.project.learnasl.utils.startFlashcardsActivity
import com.project.learnasl.utils.startMatchActivity
import com.project.learnasl.utils.startNumbersModeActivity
import com.project.learnasl.utils.startQuizActivity

@Composable
fun HomePage(username : String,
             experience: Int,
             context : Context
) {
    LearnASLTheme {
        // get one random card refreshing each time MainActivity is opened
        val ASLpair = allLettersAslPairs.shuffled().first()
        val label = ASLpair.label
        val image = ASLpair.drawing.imageRes

            //val scroll_state = rememberScrollState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            ) {
            item {
                Row(
                    modifier = Modifier.padding(top = 70.dp), // <- reducing size
                    ) { }
                UserSection(username, experience)
                Spacer(modifier = Modifier.height(16.dp))
                LearningModesButtons(
                    onAlphabetClick = { startAlphabetModeActivity(context) },
                    onNumbersClick = { startNumbersModeActivity(context) },
                    onMixedClick = {},
                )
                Spacer(modifier = Modifier.height(32.dp))
                Header()
                CardGrid(
                    { startQuizActivity(context) },
                    { startFlashcardsActivity(context) },
                    { startMatchActivity(context) },
                    { startCameraActivity(context = context) },
                )
                Spacer(modifier = Modifier.height(48.dp))
                Banner(label, image)

            }
        }
    }
}
