package com.project.learnasl.flashcards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import com.project.learnasl.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.flashcards.model.Flashcard
import com.project.learnasl.flashcards.model.FlashcardsState
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
fun FlashcardScreen(
    onBackClick: () -> Unit, // back to menu
) {

    val flashcardState = remember {
        FlashcardsState(
            flashcards = listOf(
                Flashcard(R.drawable.asl_a, "A"),
                Flashcard(R.drawable.asl_b, "B"),
                Flashcard(R.drawable.asl_c, "C"),
                Flashcard(R.drawable.asl_d, "D"),
                Flashcard(R.drawable.asl_e, "E")
            )
        )
    }

    val total = flashcardState.flashcards.size
    val answeredCount = flashcardState.alreadyKnow.size + flashcardState.stillLearning.size
    var showFinishDialog by remember { mutableStateOf(false) }

    LearnASLTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // back button + "flashcards" name
            // (in this case alphabet but it will change later if we add more sets)
            item {
                Row(
                    modifier = Modifier.padding(top = 70.dp, start = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.go_back),
                            contentDescription = "Back button"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Flashcards: ASL Alphabet",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            // progress bar + count
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 300.dp, end = 24.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$answeredCount/$total",
                        fontSize = 17.sp
                    )
                }
            }

            // progress bar
            item {
                Row(
                    modifier = Modifier.padding(top = 12.dp, start = 24.dp, end = 24.dp)
                ) {
                    LinearProgressIndicator(
                        progress = answeredCount.toFloat() / total,
                        modifier = Modifier
                            .height(14.dp)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(50)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                }
            }

            // flashcards
            item {
                Row(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box (
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        FlashcardAnimation(
                            flashcardsState = flashcardState,
                            onFinished = { showFinishDialog = true})
                    }
                }
            }
        }
        if (showFinishDialog) {
            FlashcardFinishDialog(
                onBackToMenu = onBackClick,
                totalCards = total.toShort(),
                knownCount = flashcardState.alreadyKnow.size.toShort(),
                stillLearningCount = flashcardState.stillLearning.size.toShort()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FlashcardScreenPreview() {
    FlashcardScreen(
        onBackClick = {}
    )
}