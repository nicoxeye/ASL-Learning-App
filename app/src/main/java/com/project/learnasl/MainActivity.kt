package com.project.learnasl

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.project.learnasl.utils.startFlashcardsActivity
import com.project.learnasl.utils.startMatchActivity
import com.project.learnasl.utils.startQuizActivity

class MainActivity : ComponentActivity() {
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
                    val context = LocalContext.current
                    ColumnOfButtons(context)
                }
            }
        }
    }
}

// @Preview(showBackground = true) //with func parameters doesn't work
@Composable
// TODO: make a custom function with buttons to make the code more modular (reusable) :]
fun ColumnOfButtons(context: Context) {

    Column(
        // makes it so the column will be on the center of the screen
        modifier = Modifier.fillMaxSize(),
        // 26 dp gap between children (buttons)
        verticalArrangement = Arrangement.spacedBy(26.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                startQuizActivity(context)
            },
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

}
