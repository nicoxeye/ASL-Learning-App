package com.project.learnasl.flashcards.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class FlashcardsState (
    val flashcards: List<Flashcard>,
    val alreadyKnow: SnapshotStateList<Flashcard> = mutableStateListOf(),
    val stillLearning: SnapshotStateList<Flashcard> = mutableStateListOf()
) {
    var currentIndex by mutableIntStateOf(0)
        private set

    val currentFlashcard: Flashcard?
        get() = flashcards.getOrNull(currentIndex)

    fun moveToNext() {
        if (currentIndex < flashcards.lastIndex) {
            currentIndex++
        }
    }

    // auxiliary functions
    // flashcards added to "Already know" list after user swipes right
    fun markAsAlreadyKnown(flashcard: Flashcard){
        if (!alreadyKnow.contains(flashcard) && !stillLearning.contains(flashcard)) {
            alreadyKnow.add(flashcard)
        }
    }

    // flashcards added to "Still learning" list after user swipes left
    fun markAsStillLearning(flashcard: Flashcard){
        if (!stillLearning.contains(flashcard) && !alreadyKnow.contains(flashcard)) {
            stillLearning.add(flashcard)
        }
    }
}
