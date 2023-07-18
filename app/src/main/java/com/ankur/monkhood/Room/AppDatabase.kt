package com.ankur.monkhood.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsersEntity::class], version = 1)
abstract class AppDatabase():RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "quote_database"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

                }
            }
            return INSTANCE!!
        }

    }
}