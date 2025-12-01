package com.project.learnasl.flashcards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.project.learnasl.flashcards.model.FlashcardsState

// stack animation

@Composable
fun FlashcardStack(
    flashcardsState: FlashcardsState,
    maxVisible: Int = 3,
    onFinished: () -> Unit
) {

    val currentIndex = flashcardsState.currentIndex
    val allCards = flashcardsState.flashcards
    val total = allCards.size
    val isSwiping = flashcardsState.isSwiping

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        // the card that is "painted" the last is on top
        for (i in (currentIndex + maxVisible - 1) downTo currentIndex) {
            if (i >= total) continue

            val stackIndex = i - currentIndex
            val card = allCards[i]

            // flashcards in the background
            if (stackIndex > 0) {
                BackgroundFlashcard(flashcard = card, stackIndex = stackIndex, isSwiping = isSwiping)
            }
        }

        // top flashcard
        Box(
            modifier = Modifier
                .graphicsLayer(
                    translationY = 150f // flashcard on top has to be a bit lower than the ones in the back
                )
        ) {
            // flashcard on top with its full animation
            FlashcardAnimation(flashcardsState, onFinished)
        }
    }
}
