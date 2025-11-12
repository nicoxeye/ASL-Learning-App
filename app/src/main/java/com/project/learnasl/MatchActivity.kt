package com.project.learnasl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.ui.theme.LearnASLTheme
import kotlin.collections.listOf

class MatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnASLTheme(
                // darkTheme = true
            ) {
                Surface(
                    // fills the background of the app
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // hardcoded number of pairs for now to nicely fit the screen, will probably change it with difficulties?
                    val gameAslPairs = allAslPairs.shuffled().take(4)
                    MatchGame(gameAslPairs)
                }
            }
        }
    }
}


data class Drawing(
    val imageRes: Int,
    val label: String
)

data class AslPair(
    val drawing: Drawing,
    val label: String
)

val allAslPairs = listOf(
    AslPair(Drawing(R.drawable.letter_a, "A"), "A"),
    AslPair(Drawing(R.drawable.letter_b, "B"), "B"),
    AslPair(Drawing(R.drawable.letter_c, "C"), "C"),
    AslPair(Drawing(R.drawable.letter_d, "D"), "D"),
    AslPair(Drawing(R.drawable.letter_e, "E"), "E")
)

//TODO: do functions to make this code readable...
@Composable
fun MatchGame(pairs: List<AslPair>) {
    // remembers the selected (clicked) image and label by the user
    var selectedImage by remember { mutableStateOf<AslPair?>(null) }
    var selectedLabel by remember { mutableStateOf<String?>(null) }
    // creates a set of labels, they are added if a pair is solved
    var solved by remember { mutableStateOf(setOf<String>()) }

    // timer control
    var isRunning by remember { mutableStateOf(true) }
    var finalTime by remember { mutableStateOf(0) }

    val shuffledImages = remember { pairs.shuffled() }
    val shuffledLabels = remember { pairs.map { it.label }.shuffled() }

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
            TitleText()
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

        // temporary;
        // TODO: to show the puzzle is solved, do a custom pop up and option to restart or go back
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (solved.size == pairs.size) {
                // stopping the timer
                isRunning = false

                Text(
                    text = "Solved in ${finalTime}s!",
                    Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

    }
}


@Composable
fun MatchCardImage(
    imageRes: Int,
    isSelected: Boolean,
    isSolved: Boolean,
    onClick: () -> Unit
) {

    val bg by animateColorAsState( when {
        isSolved -> MaterialTheme.colorScheme.secondaryContainer //green
        isSelected -> MaterialTheme.colorScheme.tertiaryContainer //yellow
        else -> MaterialTheme.colorScheme.primaryContainer //red
    }
    )

    val borderColor = when {
        isSolved -> MaterialTheme.colorScheme.secondary
        isSelected -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }

    Surface(
        modifier = Modifier
            .size(120.dp)
            .clickable(enabled = !isSolved) { onClick() },
        color = bg,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, borderColor),
        tonalElevation = if (isSolved) 8.dp else 4.dp,
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
        )
    }
}


@Composable
fun MatchCardText(
    text: String,
    isSelected: Boolean,
    isSolved: Boolean,
    onClick: () -> Unit
) {

    val bg by animateColorAsState( when {
        isSolved -> MaterialTheme.colorScheme.secondaryContainer //green
        isSelected -> MaterialTheme.colorScheme.tertiaryContainer //yellow
        else -> MaterialTheme.colorScheme.primaryContainer //red
    }
    )

    val borderColor = when {
        isSolved -> MaterialTheme.colorScheme.secondary
        isSelected -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }

    Surface(
        modifier = Modifier
            .size(120.dp)
            .clickable(enabled = !isSolved) { onClick() },
        color = bg,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(3.dp, borderColor),
        tonalElevation = if (isSolved) 8.dp else 4.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}


// @Preview(showBackground = true)
@Composable
fun TitleText() {

    Text(
        text = "MATCH",
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 64.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 64.dp)
    )

    Text(
        text = "match the sign to its letter",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 4.dp),
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onBackground
    )

}


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
            kotlinx.coroutines.delay(1000)
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


// @Preview(showBackground = true)
@Composable
fun MatchGamePreview() {
    val gameAslPairs = allAslPairs.shuffled().take(4)
    MatchGame(gameAslPairs)
}
