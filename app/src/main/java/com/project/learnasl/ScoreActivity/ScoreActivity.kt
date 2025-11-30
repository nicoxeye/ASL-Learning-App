package com.project.learnasl.ScoreActivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import com.project.learnasl.ui.theme.LearnASLTheme

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val score = intent.getIntExtra("Score", 0)
        //val context = LocalContext.current
        setContent {
            LearnASLTheme {
                val context = LocalContext.current
                ScoreScreen(score = score, context=context)
//                ScoreScreen(score=score) {
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                }
            }
        }
    }
}