package com.project.learnasl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.project.learnasl.ui.theme.LearnASLTheme

class FlashcardsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // null before selecting categories
            var selectedFlashcardsSet by remember { mutableStateOf<List<Flashcard>?>(null) }
            var selectedTitle by remember { mutableStateOf<String?>(null)}
            LearnASLTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   // CATEGORIES VIEW
                   if (selectedFlashcardsSet == null) {
                       FlashcardCategories(
                           onBackClick = {
                               // go back to Main Activity (homepage)
                               intent = Intent(this, MainActivity::class.java)
                               startActivity(intent)
                           },
                           // pass title & list of flashcards (choosen set)
                           onCategoryClick = { flashcards, title ->
                               selectedFlashcardsSet = flashcards
                               selectedTitle = title
                               // after setting the states, compose should automatically start FlashcardScreen
                               // (since it's not be null anymore)
                           }
                       )
                   }
                   else {
                       FlashcardScreen(
                           initialFlashcards = selectedFlashcardsSet!!, // !! operator bc we know it's != null here
                           title = selectedTitle!!,
                           onBackClick = {
                               // after user clicks on the back button the state is cleaned to return to FlashcardCategories
                               selectedFlashcardsSet = null
                               selectedTitle = null
                           }
                       )
                   }
                }
            }
        }
    }
}
