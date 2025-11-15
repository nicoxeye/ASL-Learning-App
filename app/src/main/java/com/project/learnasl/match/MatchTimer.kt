package com.project.learnasl.match

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun GameTimer(
    isRunning: Boolean,
    modifier: Modifier = Modifier,
    onTick: (Int) -> Unit // callback parameter, used in the main game to get the final time
) {
    var secondsElapsed by remember { mutableIntStateOf(0) }

    // side effect
    // it launches a coroutine that runs in the composition’s scope
    // TODO: find a better way to implement the timer with milliseconds cause it could matter if there's a ranking later
    LaunchedEffect(isRunning) {
        while (isRunning){
            // suspends this coroutine for a second (without blocking the ui)
            // so basically it freezes for one second -> then adds one to secondsElapsed = creates a basic timer
            delay(1000)
            secondsElapsed++
            // it reports the timer’s internal state every time it updates so it's possible to show it on screen
            onTick(secondsElapsed)
        }
    }

    Text(
        text = "Time: ${secondsElapsed}s",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )

}