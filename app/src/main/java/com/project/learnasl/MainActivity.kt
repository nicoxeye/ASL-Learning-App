package com.project.learnasl
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import com.project.learnasl.ui.theme.LearnASLTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.project.learnasl.Dashboard.components.Banner
import com.project.learnasl.Dashboard.components.CardGrid
import com.project.learnasl.Dashboard.components.Header
import com.project.learnasl.Dashboard.components.LearningModesButtons
import com.project.learnasl.Dashboard.components.UserSection
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.match.data.allAslPairs
import com.project.learnasl.utils.UserViewModelHelper
import com.project.learnasl.utils.startFlashcardsActivity
import com.project.learnasl.utils.startMatchActivity
import com.project.learnasl.utils.startQuizActivity
import com.project.learnasl.utils.startCameraActivity
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel> {
        UserViewModelHelper.getFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //UserSection(viewModel = viewModel)
            LearnASLTheme {
                val context = LocalContext.current
                val user = viewModel.currentUser.value

                // default if something goes wrong
                var username = "Unknown";
                var experience = 0;

                if (user != null) {
                    username = user.name
                    experience = user.experience
                }

                // get one random card refreshing each time MainActivity is opened
                val ASLpair = allAslPairs.shuffled().first()
                val label =  ASLpair.label
                val image = ASLpair.drawing.imageRes

                //val scroll_state = rememberScrollState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    item {
                        Row(
                            modifier = Modifier.padding(top = 70.dp), // <- reducing size
                            // of empty space makes space for any type of menu up here :D
                        ) {}
                        UserSection(username, experience)
                        Spacer(modifier = Modifier.height(16.dp))
                        LearningModesButtons()
                        Spacer(modifier = Modifier.height(32.dp))
                        Header()
                        CardGrid(
                            { startQuizActivity(context) },
                            { startFlashcardsActivity(context) },
                            { startMatchActivity(context) },
                            { startCameraActivity(context = context) },
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        Banner(label, image)
                    }
                }
            }

        }
    }
}

