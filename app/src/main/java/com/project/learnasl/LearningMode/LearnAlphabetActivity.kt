package com.project.learnasl.LearningMode

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.project.learnasl.MainActivity
import com.project.learnasl.data.allLettersAslPairs
import com.project.learnasl.ui.theme.LearnASLTheme

class LearnAlphabetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameAslPairs = allLettersAslPairs

        enableEdgeToEdge()
        setContent {
            LearnASLTheme {
                LearningScreen(
                    asl_pairs = allLettersAslPairs,
                    onBackClick = {
                        finish()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }, "Letter")
            }

//                val intent = Intent(this, LearningScreen(allLettersAslPairs) { }::class.java)
//                val gameAslPairs = allLettersAslPairs //
//                intent.putParcelableArrayListExtra("list", ArrayList(gameAslPairs))
//                startActivity(intent)
////                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }
        }
    }
}