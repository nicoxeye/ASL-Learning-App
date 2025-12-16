package com.project.learnasl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.project.learnasl.QuestionActivity.Model.QuestionModel
import com.project.learnasl.QuestionActivity.QuestionActivity
import com.project.learnasl.QuestionActivity.QuizCategories
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.data.AslPair
import com.project.learnasl.data.allLettersAslPairs

const val question = "What sign is this?"
class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LearnASLTheme {
                // for categories
                var selectedPairs by remember { mutableStateOf<List<AslPair>?>(null) }

                if (selectedPairs == null) {
                    QuizCategories(
                        onBackClick = { finish() }, // go back to mainActivity
                        onCategoryClick= { pairs ->
                            selectedPairs = pairs // alphabet or numbers

                            // full set that can be either 10 numbers or 26 letters from alphabet
                            val availableSet = pairs

                            // create list of 10 questions for the quiz
                            val gameAslPairs = pairs.shuffled().take(10)

                            val intent = Intent(this, QuestionActivity::class.java)
                            intent.putParcelableArrayListExtra("list",
                                ArrayList(questionsList(gameAslPairs, availableSet)))
                            startActivity(intent)
                        }
                    )
                }
//                val intent = Intent(this, QuestionActivity::class.java)
//                val gameAslPairs = allLettersAslPairs.shuffled().take(10) // take 10 sample questions
//                intent.putParcelableArrayListExtra("list",ArrayList(questionsList(gameAslPairs)))
//                startActivity(intent)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }

            }
        }
    }

    // loader for 10 sample questions - using AslPair class :)
    private fun questionsList(pairs: List<AslPair>, availableSet: List<AslPair>): List<QuestionModel>{
        return pairs.mapIndexed { index, item ->
            val correct = item.label
            val other_labels = availableSet
                .map { it.label }
                .filter { it != correct }
                .shuffled()
                .take(3) // 3 other random signs as answers

            val options = (other_labels + correct).shuffled() // 4 answers options

            QuestionModel(
                id = index + 1,
                question = question,
                answer_1 = options[0],
                answer_2 = options[1],
                answer_3 = options[2],
                answer_4 = options[3],
                correct_answer = correct,
                score = 1,
                img_path = item.drawing.imageRes,
                clicked_answer = null
            )
        }
    }
}

