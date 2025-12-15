package com.project.learnasl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.project.learnasl.QuestionActivity.Model.QuestionModel
import com.project.learnasl.QuestionActivity.QuestionActivity
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
                val intent = Intent(this, QuestionActivity::class.java)
                val gameAslPairs = allLettersAslPairs.shuffled().take(10) // take 10 sample questions
                intent.putParcelableArrayListExtra("list",ArrayList(questionsList(gameAslPairs)))
                startActivity(intent)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }

            }
        }
    }

    // loader for 10 sample questions - using AslPair class :)
    private fun questionsList(pairs: List<AslPair>): List<QuestionModel>{
        return pairs.mapIndexed { index, item ->
            val correct = item.label
            val other_labels = allLettersAslPairs
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

