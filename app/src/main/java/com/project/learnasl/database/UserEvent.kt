package com.project.learnasl.database

// when the database comes in contact with an event of the app
// click of a button etc it tells the database what to do then
sealed interface UserEvent {

    data class SaveUser(val user: User) : UserEvent

}