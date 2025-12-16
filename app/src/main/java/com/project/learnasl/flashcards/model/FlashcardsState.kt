package com.project.learnasl.flashcards.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

data class FlashcardsState (
    val flashcards: List<Flashcard>,
    val alreadyKnow: SnapshotStateList<Flashcard> = mutableStateListOf(),
    val stillLearning: SnapshotStateList<Flashcard> = mutableStateListOf(),
) {
    // all flashcards which stores isFavourite state
    var allFlashcards by mutableStateOf(flashcards.toMutableStateList())
        private set

    // list of favourites needed for displaying in category "Favourites"
    var favouriteFlashcards by mutableStateOf(
        allFlashcards.filter { it.isFavourite }.toMutableStateList()
    )
        private set

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

    // toggles the isFavourite status of the flashcard
    fun toggleFavourite(card: Flashcard) {
        // we use unique fields like imageRes and text to find the flashcard to update bc we don't have ID
        // find the index of the flashcard in the pool of all sets that we have
        val indexInAll = allFlashcards.indexOfFirst { it.imageRes == card.imageRes && it.text == card.text}

        // proceed only if the flashcard was found in the main list
        if (indexInAll != -1) {
            // get the current version of the flashcard from the main list
            val currentCard = allFlashcards[indexInAll]
            // create a newCard object by copying the current one and flipping the favourite status
            val newCard = currentCard.copy(isFavourite = !currentCard.isFavourite)

            // update the main list with newly created flashcard (newCard)
            allFlashcards[indexInAll] = newCard

            // check if the flashcard was added to favourites
            if (newCard.isFavourite) {
                // if it was -> add new object to the list of Favourites (in categories)
                // but it doesn't work since i didn't connect the flashcards to the room database (we'll do)
                favouriteFlashcards.add(newCard)
            } else {
                favouriteFlashcards.removeIf { it.imageRes == newCard.imageRes && it.text == newCard.text}
            }

            // find the index of the card in the current active set (e.g. numbers, alphabet etc)
            val indexInCurrent = currentFlashcards.indexOfFirst {it.imageRes == card.imageRes && it.text == card.text }
            // if the card on top of the stack (so if it's the first on display) we update it
            if (indexInCurrent != -1) {
                // convert the current list to a mutable state list to trigger UI recomposition
                currentFlashcards = currentFlashcards.toMutableStateList().apply{
                    // replace the old card object with the new one (with the updated fav status)
                    this[indexInCurrent] = newCard
                }
            }
        }
    }
}
