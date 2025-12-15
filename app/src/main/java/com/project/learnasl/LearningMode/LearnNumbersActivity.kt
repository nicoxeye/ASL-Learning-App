package com.project.learnasl.LearningMode

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.project.learnasl.MainActivity
import com.project.learnasl.match.data.allNumbersAslPair
import com.project.learnasl.ui.theme.LearnASLTheme

class LearnNumbersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameAslPairs = allNumbersAslPair

        enableEdgeToEdge()
        setContent {
            LearnASLTheme {
                LearningScreen(
                    asl_pairs = allNumbersAslPair,
                    onBackClick = {
                        finish()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }, "Number"
                )
            }
        }
    }
}