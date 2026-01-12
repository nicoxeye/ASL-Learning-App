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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.flashcards.model.Flashcard
import com.project.learnasl.flashcards.model.FlashcardsState
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
fun FlashcardScreen(
    onBackClick: () -> Unit, // back to menu, -> change it to back to flashcardCategories
    initialFlashcards: List<Flashcard>, // list transmitted from flashcardCategories
    title: String, // title displayed next to the back button,
    addExp: () -> Unit,
    viewModel: FlashcardsViewModel
) {
    val flashcardState = remember {
        FlashcardsState(
            flashcards = initialFlashcards,
            onFavouriteToggled = { card -> viewModel.toggleFavourite(card)}
        )
    }

    val total = flashcardState.currentFlashcards.size
    // if in still learning mode
    val answeredCount =
        if (flashcardState.isStillLearningMode) {
                flashcardState.currentIndex
        }
        else {
            flashcardState.alreadyKnow.size + flashcardState.stillLearning.size
        }

    var showFinishDialog by remember { mutableStateOf(false) }

    LearnASLTheme (
        //darkTheme = true
    ) {
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
                        text =
                            if (flashcardState.isStillLearningMode) "Still learning Mode"
                            else title,
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FlashcardStack(
                        flashcardState,
                        onFinished = { showFinishDialog = true },
                        addExp = addExp
                    )
                }
            }
        }
        if (showFinishDialog) {
            FlashcardFinishDialog(
                onBackToMenu = onBackClick,
                onRepeatWholeSet = {
                    showFinishDialog = false
                    flashcardState.resetAll()
                },
                onRepeatStillLearning = {
                    if (flashcardState.stillLearning.isNotEmpty()) {
                        showFinishDialog = false
                        flashcardState.createStillLearningSet()
                    }
                },
                totalCards = total.toShort(),
                knownCount = flashcardState.alreadyKnow.size.toShort(),
                stillLearningCount = flashcardState.stillLearning.size.toShort(),
                // show 'repeat whole set' button if we're NOT in still learning mode
                showRepeatWholeSetButton = !flashcardState.isStillLearningMode,
                // show 'still learning' button if flashcards marked as "still learning" exist
                // and if we're NOT in still learning mode
                showStillLearningButton = !flashcardState.isStillLearningMode && flashcardState.stillLearning.isNotEmpty()
            )
        }
    }
}
