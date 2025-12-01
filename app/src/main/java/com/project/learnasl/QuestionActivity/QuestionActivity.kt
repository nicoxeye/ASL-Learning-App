package com.project.learnasl.QuestionActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.project.learnasl.MainActivity
import com.project.learnasl.QuestionActivity.Model.QuestionModel
import com.project.learnasl.ScoreActivity.ScoreActivity
import com.project.learnasl.ui.theme.LearnASLTheme

// activity that uses UI and logic from QuestionScreen, if you want to use
// quiz this is the class you should be referring to
class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val receivedList=
            intent.getParcelableArrayListExtra<QuestionModel>("list") ?: arrayListOf()

        setContent {
            LearnASLTheme {
                QuestionScreen(
                    questions = receivedList,
                    onBackClick = {
                        finish()
                        val intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)
                       },
                    onFinish = {
                        finalScore-> val intent= Intent(this, ScoreActivity::class.java)
                        intent.putExtra("Score", finalScore)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}