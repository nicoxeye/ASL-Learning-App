package com.project.learnasl.userprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.project.learnasl.database.User
import com.project.learnasl.database.UserEvent
import com.project.learnasl.database.UserViewModel


@Composable
fun CreateUser(viewModel: UserViewModel) {

    val nameInput = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Name(nameInput)

        Button(onClick = {
            val user = User(name = nameInput.value)
            viewModel.onEvent(UserEvent.SaveUser(user))

            //TODO()
            // if a user already created / exists with the same name then skip this???
            // or make it somehow only show up once in the app idkkaksokosdaklsdwidosakldlwd
        }) {
            Text("Create an account")
        }

    }
}

@Composable
fun Name(nameInput: MutableState<String> = mutableStateOf("")) {

    OutlinedTextField(
        value = nameInput.value,
        onValueChange = {
            // limit for the name to be only 10 characters cause yeah
            if (it.length <= 10) {
                nameInput.value = it
            }
        },
        label = { Text("Name") },
        placeholder = { Text("Tell us your name") },
    )

}
