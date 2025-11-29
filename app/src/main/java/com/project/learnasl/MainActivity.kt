package com.project.learnasl

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.learnasl.ui.theme.LearnASLTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.utils.UserViewModelHelper
import com.project.learnasl.utils.startFlashcardsActivity
import com.project.learnasl.utils.startMatchActivity
import com.project.learnasl.utils.startQuizActivity
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel> {
        UserViewModelHelper.getFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnASLTheme(
                // darkTheme = true // uncomment to see the app in darkTheme
            ) {
                Surface(
                    // fills the background of the app
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        // makes it so the column will be on the center of the screen
                        modifier = Modifier.fillMaxSize(),
                        // 26 dp gap between children (buttons)
                        verticalArrangement = Arrangement.spacedBy(26.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //testing!!
                        UserText(viewModel)

                        val context = LocalContext.current
                        ColumnOfButtons(context)
                    }

                }
            }
        }
    }
}

@Composable
fun UserText(viewModel: UserViewModel) {
    //gets the one (1) user existing in the database and shows theri name and exp for testing
    val user = viewModel.currentUser.value

    if (user != null) {
        Text("Welcome, ${user.name}",
            color = MaterialTheme.colorScheme.onBackground
        )
        Text("XP: ${user.experience}",
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

// @Preview(showBackground = true) //with func parameters doesn't work
@Composable
// TODO: make a custom function with buttons to make the code more modular (reusable) :]
fun ColumnOfButtons(context: Context) {
    Button(
        onClick = {
            startQuizActivity(context) },
            shape = RoundedCornerShape(20.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // background
                contentColor = MaterialTheme.colorScheme.onPrimary // text
            ),
        ) {
            Text(
                text = "Quiz",
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }


    Button(
        onClick = {
            startFlashcardsActivity(context)
                  },
            shape = RoundedCornerShape(20.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
        Text(
            text = "Flashcards",
            Modifier.padding(8.dp),
            style = MaterialTheme.typography.labelLarge
            )
        }


    Button(
        onClick = {
            startMatchActivity(context);
                  },
            shape = RoundedCornerShape(20.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {

        Text(
            text = "Match",
            Modifier.padding(8.dp),
            style = MaterialTheme.typography.labelLarge
            )
        }
}


