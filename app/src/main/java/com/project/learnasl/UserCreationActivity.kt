package com.project.learnasl

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.userprofile.CreateUser
import com.project.learnasl.utils.UserViewModelHelper
import com.project.learnasl.utils.startMainActivity

class UserCreationActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel> {
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
                if (viewModel.hasUser()) {
                    startMainActivity(context)
                    finish()
                } else {
                    showCreateUser.value = true
                }
            }

            LearnASLTheme {

                if (showCreateUser.value) {
                    CreateUser(viewModel, context)
                }

            }

        }
    }
}