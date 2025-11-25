package com.project.learnasl.database

import androidx.room.Entity
import androidx.room.PrimaryKey


// https://www.youtube.com/watch?v=bOd3wO0uFr8&t=230s
// for future reference
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val experience: Int = 0
)