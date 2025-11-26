package com.project.learnasl.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: UserDao

    // https://stackoverflow.com/questions/77769872/how-can-i-efficiently-ship-a-room-database-with-my-android-app
    // ^ explains how to use the database in every activity

    // companion object is similar to static in java ergo it belongs and can be called with the class and not an object of said class
    // usage: val db = AppDatabase.getDatabase(context)
    // ^ best in  viewmodels to keep activities clean
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learn_asl_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }


}