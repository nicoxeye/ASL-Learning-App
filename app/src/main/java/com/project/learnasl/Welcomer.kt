package com.project.learnasl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.learnasl.ui.theme.LearnASLTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.utils.UserViewModelHelper
import com.project.learnasl.utils.startMainActivity
import com.project.learnasl.utils.startUserCreationActivity
import kotlinx.coroutines.delay
import kotlin.getValue

class Welcomer : ComponentActivity() {

    private val userViewModel by viewModels<UserViewModel> {
        UserViewModelHelper.getFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current
            // boolean
            val showCreateUser = remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                showCreateUser.value = !userViewModel.hasUser()
            }


            LearnASLTheme(
                //darkTheme = true
            ){


                GreetingScreen {
                    // will happen after the timeout
                    if (showCreateUser.value) {
                        startUserCreationActivity(this)
                        finish()
                    } else {
                        startMainActivity(this);
                        finish()
                    }

                }
            }
        }
    }
}

@Composable
fun GreetingScreen(onTimeout: () -> Unit) {
    // show activity and after a delay (3s) do a func that will be given in parameter
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        // background color for safety
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    // TODO: custom gradient:D temporary, will change it in the future (prettify)
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageLogo(
                    R.drawable.logo_star,
                    modifier = Modifier
                        .padding(bottom = 64.dp)
                        .scale(2f) // TODO: eyeing this for now, have to scale it later properly
                )
                CustomText("LEARN")
                CustomText("ASL")
                CustomText("BY")
                CustomText("PLAYING")
            }
        }
    }
}

@Composable
fun CustomText(string: String) {
    Text(
        text = string,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 48.sp,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun ImageLogo(imgRes: Int, modifier: Modifier) {

    Image(
        painter = painterResource(imgRes),
        contentDescription = "Star Logo",
        modifier = modifier,
        alignment = Alignment.Center
    )

}


