package com.project.learnasl.database

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// "ViewModel is a class that is responsible for preparing and managing the data for an activity"
class UserViewModel(
    application: Application
): ViewModel() {

    private val repository: UserRepository
    val currentUser = mutableStateOf<User?>(null)

    init {
        val db = AppDatabase.getDatabase(application)
        repository = UserRepository(db.dao)

        viewModelScope.launch {
            // coroutine to get the user
            currentUser.value = repository.getUser()
        }

    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    suspend fun hasUser(): Boolean {
        return repository.getUserCount() > 0
    }

    fun addExp(exp: Int) {
        viewModelScope.launch {
            val user = repository.getUser()
            // DEBUG println("USER'S BEFORE EXP: ${user?.experience}")
            repository.addExperience(exp)
            currentUser.value = repository.getUser()
            // DEBUG println("USER'S AFTER EXP: ${currentUser.value?.experience}")
        }
    }

    suspend fun computeLvl() : Int? {
        return repository.computeLvl()
    }

    suspend fun resetExp() {
        return repository.resetExp()
    }

    suspend fun editName(name: String){
        return repository.editName(
            name = name
        )
    }

}