package com.project.learnasl.LearningMode

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.project.learnasl.MainActivity
import com.project.learnasl.QuestionActivity.QuestionActivity
import com.project.learnasl.QuestionActivity.QuestionScreen
import com.project.learnasl.match.data.allAslPairs
import com.project.learnasl.ui.theme.LearnASLTheme

class LearnAlphabetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameAslPairs = allAslPairs

        enableEdgeToEdge()
        setContent {
            LearnASLTheme {
                LearningScreen(
                    asl_pairs = allAslPairs,
                    onBackClick = {
                        finish()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }, "Letter")
            }

//                val intent = Intent(this, LearningScreen(allAslPairs) { }::class.java)
//                val gameAslPairs = allAslPairs //
//                intent.putParcelableArrayListExtra("list", ArrayList(gameAslPairs))
//                startActivity(intent)
////                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }
        }
    }
}