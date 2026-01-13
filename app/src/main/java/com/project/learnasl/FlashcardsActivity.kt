package com.project.learnasl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.learnasl.flashcards.FlashcardCategories
import com.project.learnasl.flashcards.FlashcardScreen
import com.project.learnasl.flashcards.model.Flashcard
import com.project.learnasl.data.AslPair
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.flashcards.FlashcardsViewModel
import com.project.learnasl.flashcards.model.FlashcardsState
import com.project.learnasl.flashcards.model.allAppFlashcards
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.FLASHCARDS_EXP
import com.project.learnasl.utils.MATCH_EXP
import com.project.learnasl.utils.UserViewModelHelper
import kotlin.getValue

class FlashcardsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val userViewModel by viewModels<UserViewModel> {
            UserViewModelHelper.getFactory(application)
        }

        val flashcardsViewModel: FlashcardsViewModel by viewModels()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnASLTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   // null before selecting categories
                   var selectedFlashcardsSet by remember { mutableStateOf<List<Flashcard>?>(null) }
                   var selectedTitle by remember { mutableStateOf<String?>(null)}

                   // CATEGORIES VIEW
                   if (selectedFlashcardsSet == null) {
                       FlashcardCategories(
                           onBackClick = {
                               // go back to Main Activity (homepage)
                               finish()
                           },
                           // pass title & list of flashcards (choosen set)
                           onCategoryClick = { flashcards, title ->
                               if (title.contains("Favourites")) {
                                   selectedFlashcardsSet = flashcardsViewModel.favouriteFlashcards
                               }
                               else {
                                   selectedFlashcardsSet = flashcards
                               }
                               selectedTitle = title
                               // after setting the states, compose should automatically start FlashcardScreen
                               // (since it's not be null anymore)
                           },
                           favouriteFlashcards = flashcardsViewModel.favouriteFlashcards
                       )
                   }
                   else {
                       FlashcardScreen(
                           initialFlashcards = selectedFlashcardsSet!!, // !! operator bc we know it's != null here
                           title = selectedTitle!!,
                           viewModel = flashcardsViewModel,
                           onBackClick = {
                               // after user clicks on the back button the state is cleaned to return to FlashcardCategories
                               selectedFlashcardsSet = null
                               selectedTitle = null
                           },
                           addExp = { userViewModel.addExp(FLASHCARDS_EXP) }
                       )
                   }
                }
            }
        }
    }
}