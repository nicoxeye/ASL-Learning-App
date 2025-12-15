package com.project.learnasl.flashcards.model

import com.project.learnasl.data.AslPair
import com.project.learnasl.data.allLettersAslPairs
import com.project.learnasl.data.allNumbersAslPair


data class Flashcard(
    val imageRes: Int,
    val text: String
)


// converting AslPair to flashcards
fun AslPair.toFlashcard(): Flashcard {
    return Flashcard(
        imageRes = this.drawing.imageRes,
        text = this.label
    )
}

// sets of flashcards
val aslAlphabetFlashcards = allLettersAslPairs.map {
    it.toFlashcard()
}

val aslNumbersFlashcards = allNumbersAslPair.map{
    it.toFlashcard()
}
