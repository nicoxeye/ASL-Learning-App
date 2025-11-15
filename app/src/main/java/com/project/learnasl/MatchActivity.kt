package com.project.learnasl

import com.project.learnasl.match.MatchGame
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.project.learnasl.match.data.allAslPairs
import com.project.learnasl.ui.theme.LearnASLTheme

class MatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnASLTheme(
                //darkTheme = true
            ) {
                Surface(
                    // fills the background of the app
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // hardcoded number of pairs for now to nicely fit the screen, will probably change it with difficulties?
                    val gameAslPairs = allAslPairs.shuffled().take(4)
                    MatchGame(gameAslPairs)
                }
            }
        }
    }
}
