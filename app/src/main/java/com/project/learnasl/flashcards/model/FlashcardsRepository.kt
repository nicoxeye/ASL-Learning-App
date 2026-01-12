package com.project.learnasl.flashcards.model

// since we don't use Room database in the whole app and therefore
// flashcards don't have ID's,
// this singleton will make sure favourite flashcards are remembered as long as the app is running
object FlashcardsRepository {
    var instanceFlashcards: List<Flashcard> = allAppFlashcards
}