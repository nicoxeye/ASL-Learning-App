package com.project.learnasl
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.project.learnasl.ui.theme.LearnASLTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.project.learnasl.Dashboard.components.BottomNavItem
import com.project.learnasl.pages.HomePage
import com.project.learnasl.pages.UserPage
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.utils.UserViewModelHelper
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
                var username = "Unknown"
                var experience = 0
                var level = 1

                if (user != null) {
                    username = user.name
                    experience = user.experience
                    level = user.level
                }

                val navItemList = listOf(
                    BottomNavItem("Home", Icons.Default.Home),
                    BottomNavItem("Profile", Icons.Default.Person),
                    BottomNavItem("Settings", Icons.Default.Settings)
                )

                var selectedIndex by remember { mutableIntStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ) {
                            navItemList.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedIndex == index,
                                    onClick = {
                                        selectedIndex = index
                                    },
                                    icon = {
                                        Icon(imageVector = item.icon, contentDescription = "Icon")
                                    },
                                    label = {
                                        Text(text = item.label)
                                    }
                                )
                            }
                        }
                    }
                ) {
                        padding ->
                    ContentScreen(
                        modifier = Modifier.padding(padding),
                        selectedIndex,
                        username,
                        experience,
                        level,
                        context = context
                    )
                }
            }

        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier,
                  selectedIndex : Int,
                  username : String,
                  experience: Int,
                  level: Int,
                  context : Context) {

    when (selectedIndex) {
        0 -> HomePage(username, experience, context)
        1 -> UserPage(username, experience, level)
        2 -> { TODO() }
    }

}


