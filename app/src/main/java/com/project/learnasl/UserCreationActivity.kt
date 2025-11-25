package com.project.learnasl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.project.learnasl.database.AppDatabase
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.userprofile.CreateUser

class UserCreationActivity : ComponentActivity() {

    // i feel this should probably be initialized somewhere else...
    // or a whole other class JUST for this
    // dunno <3
    // TODO()
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "users.db"
        ).build()
    }

    private val viewModel by viewModels<UserViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            LearnASLTheme {

                // NOT optimal
                // dependency injection better...
                // TODO()

                CreateUser(viewModel)


            }

        }
    }
}
