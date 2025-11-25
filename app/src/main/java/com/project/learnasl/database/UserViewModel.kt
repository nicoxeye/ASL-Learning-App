package com.project.learnasl.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// "ViewModel is a class that is responsible for preparing and managing the data for an activity"
class UserViewModel(
    private val dao: UserDao
): ViewModel() {

    var name: String = ""

    // managing the events connected with the database
    fun onEvent(event: UserEvent) {
        when (event) {

            // coroutines so the app won't crash
            is UserEvent.SaveUser -> {
                viewModelScope.launch {
                    dao.insertDao(event.user)
                }
            }

        }
    }
}