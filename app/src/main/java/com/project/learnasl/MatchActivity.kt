package com.project.learnasl

import com.project.learnasl.match.MatchGame
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.match.data.allAslPairs
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.MATCH_EXP
import com.project.learnasl.utils.UserViewModelHelper
import com.project.learnasl.utils.startMainActivity
import com.project.learnasl.utils.startMatchActivity
import kotlin.getValue

class MatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val userViewModel by viewModels<UserViewModel> {
            UserViewModelHelper.getFactory(application)
        }

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

                    MatchGame(gameAslPairs,
                        // go back to main screen
                        onBackClick =  {
                            finish()
                            startMainActivity(this)
                                       },
                        //reset
                        onNewGame = {
                            finish()
                            startMatchActivity(this)
                                    },
                        onWin = {
                            userViewModel.addExp(MATCH_EXP)
                        }

                    )

                }
            }
        }
    }
}
