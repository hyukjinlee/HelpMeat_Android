package com.project.helpmeat.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_NAME = "app_database"

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDAO(): UserInfoDAO
}