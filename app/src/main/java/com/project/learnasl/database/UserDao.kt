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

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getUserCount(): Int

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): User?

}