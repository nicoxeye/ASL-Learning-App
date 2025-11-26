package com.project.learnasl.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.learnasl.database.UserViewModel

class UserViewModelHelper {

    // to make i t static
    companion object {
        // returns a ViewModelProvider.Factory for UserViewModel
        fun getFactory(application: Application): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(application) as T
                }
            }
        }
    }

}