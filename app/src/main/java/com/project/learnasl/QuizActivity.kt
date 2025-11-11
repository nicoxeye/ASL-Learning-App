package com.project.learnasl

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.project.learnasl.QuestionActivity.Model.QuestionModel
import com.project.learnasl.QuestionActivity.QuestionActivity
import com.project.learnasl.ui.theme.LearnASLTheme

const val question = "What sign is this?"
class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor= ContextCompat.getColor(this, R.color.purple_500)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        enableEdgeToEdge()
        setContent {
            LearnASLTheme {
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putParcelableArrayListExtra("list",ArrayList(questionsList()))
                startActivity(intent)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }
            }
        }
    }

    // loader for 5 sample questions (letters A,B,C,D,E)
    private fun questionsList(): List<QuestionModel>{
        return listOf(
            QuestionModel(
                id=1,
                question =question,
                answer_1 = "Letter B",
                answer_2 = "Letter D",
                answer_3 = "Letter A",
                answer_4 = "Letter G",
                correct_answer = "a",
                score = 1,
                img_path = "letter_b",
                clicked_answer = null
            ),
            QuestionModel(
                id=2,
                question =question,
                answer_1 = "Letter C",
                answer_2 = "Letter Z",
                answer_3 = "Letter A",
                answer_4 = "Letter G",
                correct_answer = "a",
                score = 1,
                img_path = "letter_c",
                clicked_answer = null
            ),
            QuestionModel(
                id=3,
                question =question,
                answer_1 = "Letter D",
                answer_2 = "Letter E",
                answer_3 = "Letter H",
                answer_4 = "Letter A",
                correct_answer = "d",
                score = 1,
                img_path = "letter_a",
                clicked_answer = null
            ),
            QuestionModel(
                id=4,
                question =question,
                answer_1 = "Letter B",
                answer_2 = "Letter C",
                answer_3 = "Letter D",
                answer_4 = "Letter A",
                correct_answer = "c",
                score = 1,
                img_path = "letter_d",
                clicked_answer = null
            ),
            QuestionModel(
                id=5,
                question =question,
                answer_1 = "Letter J",
                answer_2 = "Letter A",
                answer_3 = "Letter U",
                answer_4 = "Letter E",
                correct_answer = "d",
                score = 1,
                img_path = "letter_e",
                clicked_answer = null
            )
        )
    }

}

