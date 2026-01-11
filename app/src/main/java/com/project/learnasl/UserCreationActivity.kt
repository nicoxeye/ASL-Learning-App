package com.project.learnasl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.usercreation.CreateUser
import com.project.learnasl.utils.UserViewModelHelper


class UserCreationActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel> {
        UserViewModelHelper.getFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnASLTheme {
                CreateUser(viewModel, LocalContext.current)
            }

        }
    }
}