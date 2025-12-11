package com.project.learnasl.database

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.floor
import kotlin.math.sqrt


// https://www.youtube.com/watch?v=bOd3wO0uFr8&t=230s
// for future reference
@Entity(tableName = "user")
class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var experience: Int = 0,
    var level: Int = 1,
) {

    constructor(named: String) : this(name = named)

    fun addExperience(experience: Int) {
        this.experience += experience
    }

    fun computeLevel(): Int {
        val level = floor(25 + sqrt((625 + 100 * experience).toDouble())) / 50
        this.level = level.toInt()
        return this.level
    }

}