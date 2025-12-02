package com.project.learnasl.flashcards.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class FlashcardsState (
    val flashcards: List<Flashcard>,
    val alreadyKnow: SnapshotStateList<Flashcard> = mutableStateListOf(),
    val stillLearning: SnapshotStateList<Flashcard> = mutableStateListOf(),
) {
    var isSwiping by mutableStateOf(false)
    var currentIndex by mutableIntStateOf(0)
        private set

    // mutable list of flashcard; either default set of 26 cards or still learning set
    var currentFlashcards by mutableStateOf(flashcards)
        private set

    // feature flag; still learning mode on/off
    var isStillLearningMode by mutableStateOf(false)
        private set

    val currentFlashcard: Flashcard?
        get() = currentFlashcards.getOrNull(currentIndex)

    fun moveToNext() {
        if (currentIndex < currentFlashcards.lastIndex) {
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

    // repeats the same flashcards set
    fun resetAll() {
        currentFlashcards = flashcards
        currentIndex = 0
        alreadyKnow.clear()
        stillLearning.clear()
        isStillLearningMode = false
    }

    // creates set made of flashcards marked as "still learning" (swiped left by user)
    fun createStillLearningSet() {
        if (stillLearning.isNotEmpty()) {
            currentFlashcards = stillLearning.toList()
            currentIndex = 0
            isStillLearningMode = true
        }
    }
}
