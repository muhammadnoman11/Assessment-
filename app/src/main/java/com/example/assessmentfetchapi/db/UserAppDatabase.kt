package com.example.assessmentfetchapi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assessmentfetchapi.db.model.UserEntity

@Database(entities = [UserEntity::class] , version = 1)
abstract class UserAppDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserAppDatabase? = null

        fun getDatabase(context: Context): UserAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserAppDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}