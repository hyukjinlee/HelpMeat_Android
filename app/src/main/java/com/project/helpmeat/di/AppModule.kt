package com.project.helpmeat.di

import android.content.Context
import androidx.room.Room
import com.project.helpmeat.controller.GrillSettingsDataController
import com.project.helpmeat.repository.db.AppDatabase
import com.project.helpmeat.repository.db.DATABASE_NAME
import com.project.helpmeat.repository.db.UserInfoDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context,
            AppDatabase::class.java,
            "$DATABASE_NAME.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserInfoDAO(database: AppDatabase): UserInfoDAO {
        return database.userInfoDAO()
    }

    @Provides
    @Singleton
    fun provideGrillSettingsDataController(): GrillSettingsDataController {
        return GrillSettingsDataController()
    }
}