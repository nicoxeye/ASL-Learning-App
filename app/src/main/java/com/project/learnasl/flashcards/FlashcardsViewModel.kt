package com.project.learnasl.flashcards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.project.learnasl.flashcards.model.Flashcard
import com.project.learnasl.flashcards.model.FlashcardsRepository
import com.project.learnasl.flashcards.model.allAppFlashcards

class FlashcardsViewModel : ViewModel() {
    var allFlashcards by mutableStateOf(FlashcardsRepository.instanceFlashcards)
        private set

    // every time the program asks for favouriteFlashcards,
    // ViewModel looks for cards marked as isFavourite = true in allFlashcards
    val favouriteFlashcards: List<Flashcard>
        get() = allFlashcards.filter {it.isFavourite}

    fun toggleFavourite(card: Flashcard) {
        // .map iterates through each flashcard in allFlashcards list
        val newList = allFlashcards.map {
            // checks if that's the card user has clicked on
            if (it.imageRes == card.imageRes && it.text == card.text) {
               // if it is, we copy this flashcard with the opposite state of the star
                it.copy(isFavourite = !it.isFavourite)
            }
            else { it } // return original flashcard if it's not the one we're looking for
        }
        // update the local state to trigger UI recomposition
        allFlashcards = newList
        // store the updated list in repository
        FlashcardsRepository.instanceFlashcards = newList
    }
}