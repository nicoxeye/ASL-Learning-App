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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.project.learnasl.data.AslPair
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.data.allLettersAslPairs
import com.project.learnasl.match.MatchCategories
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.MATCH_EXP
import com.project.learnasl.utils.UserViewModelHelper
import com.project.learnasl.utils.startMainActivity
import com.project.learnasl.utils.startMatchActivity
import kotlinx.coroutines.selects.select
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
                    var selectedPairs by remember { mutableStateOf<List<AslPair>?>(null) }

                    val startNewMatch: (List<AslPair>) -> Unit = { pairsToUse ->
                        val gameAslPairs = pairsToUse.shuffled().take(4)
                        selectedPairs = gameAslPairs
                    }

                    // hardcoded number of pairs for now to nicely fit the screen, will probably change it with difficulties?
                    //val gameAslPairs = allLettersAslPairs.shuffled().take(4)

                    if (selectedPairs == null) {
                        // show categories
                        MatchCategories (
                            onBackClick =  { finish(); startMainActivity(this) },
                            onCategoryClick = { pairsFromCategory ->
                                startNewMatch(pairsFromCategory)
                            }
                        )
                    } else

                        MatchGame(pairs= selectedPairs!!,
                            // go back to categories
                            onBackClick =  {
                                selectedPairs = null
                            },
                            //reset
                            // lets the user choose the category again but you can change it if you want to
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