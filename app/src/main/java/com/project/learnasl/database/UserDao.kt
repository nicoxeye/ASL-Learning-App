package com.project.learnasl.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertDao(user: User)

    // TODO() update to uptade user's experience points

    // TODO by getting this and limiting the database to 1 if user exists in db, it can skip the user creation screen (im guessing)
    // it would ask this in Welcomer -> database.getusercount() == 1; yes -> skip to mainactivity; no -> to user creation
    @Query("SELECT COUNT(*) FROM user")
    suspend fun getUserCount(): Int
}