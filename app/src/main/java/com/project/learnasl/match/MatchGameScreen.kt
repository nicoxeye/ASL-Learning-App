package com.project.learnasl.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.R
import com.project.learnasl.data.AslPair
import kotlin.collections.plus


@Composable
fun MatchGame(
    pairs: List<AslPair>,
    onBackClick: () -> Unit,
    onNewGame: () -> Unit,
    onWin: () -> Unit,
) {
    // remembers the selected (clicked) image and label by the user
    var selectedImage by remember { mutableStateOf<AslPair?>(null) }
    var selectedLabel by remember { mutableStateOf<String?>(null) }
    // creates a set of labels, they are added if a pair is solved
    var solved by remember { mutableStateOf(setOf<String>()) }

    // timer control
    var isRunning by remember { mutableStateOf(true) }
    var finalTime by remember { mutableIntStateOf(0) }

    val shuffledImages = remember { pairs.shuffled() }
    val shuffledLabels = remember { pairs.map { it.label }.shuffled() }

    // to control the dialog showing
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.go_back),
                        contentDescription = "Go back button",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }

                Text(
                    text = "MATCH",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 64.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 64.dp)
                )
            }

            Text(
                text = "match the sign to its letter",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )


            GameTimer(
                isRunning = isRunning,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                seconds ->
                finalTime = seconds
            }

        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                shuffledImages.forEach { pair ->

                    val isSolved = solved.contains(pair.label)

                    MatchCardImage(
                        imageRes = pair.drawing.imageRes,
                        isSelected = selectedImage == pair && !isSolved,
                        isSolved = solved.contains(pair.drawing.label),
                        onClick = {
                            if (!isSolved) {
                                selectedImage = pair
                                val label = selectedLabel

                                if (label != null && pair.drawing.label == label) {
                                    solved = solved + label
                                    selectedImage = null
                                    selectedLabel = null
                                }

                            }
                        }

                    )

                }

            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                shuffledLabels.forEach { label ->
                    val isSolved = solved.contains(label)

                    MatchCardText(
                        text = label,
                        isSelected = selectedLabel == label && !isSolved,
                        isSolved = solved.contains(label),
                        onClick = {
                            if (!isSolved) {
                                selectedLabel = label

                                val img = selectedImage
                                if (img != null && img.drawing.label == label) {
                                    solved = solved + label

                                    selectedImage = null
                                    selectedLabel = null
                                }

                            }

                        }

                    )

                }

            }


        }


        // custom pop up when game is won

        // updates happen outside composition in a launched effect so updating these variables won't cause bugs now
        LaunchedEffect(solved.size) {
            if (solved.size == pairs.size) {
                // stopping the timer
                isRunning = false
                showDialog = true
            }
        }

        // if sthe dialog is shown the onWin method will run (which adds exp)
        LaunchedEffect(showDialog) {
            if (showDialog) {
                onWin()
            }
        }

        if (showDialog) {
            MatchWinDialog(
                onDismiss = { onBackClick() },
                onConfirm = { onNewGame() },
                elapsedTime = finalTime
            )

        }

    }
}



