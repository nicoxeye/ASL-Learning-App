package com.project.learnasl.QuestionActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.learnasl.MainActivity
import com.project.learnasl.QuestionActivity.Model.QuestionModel
import com.project.learnasl.ScoreActivity.ScoreActivity
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.QUIZ_EXP
import com.project.learnasl.utils.UserViewModelHelper
import kotlin.getValue

// activity that uses UI and logic from QuestionScreen, if you want to use
// quiz this is the class you should be referring to
class QuestionActivity : AppCompatActivity() {

    private val userViewModel by viewModels<UserViewModel> {
        UserViewModelHelper.getFactory(application)
    }

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

                        val exp = finalScore * QUIZ_EXP / 10 // instead of adding a const value (ex 800) only add exp for the correct answers in the quiz (ex 5/10 so 50% of 800)
                        userViewModel.addExp(exp)
                        Log.d("EXP ADDITION","ADDED EXP VAL: $exp")

                        intent.putParcelableArrayListExtra("questions_list", receivedList)
                        intent.putExtra("Score", finalScore)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}