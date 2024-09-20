package com.example.reltyleads.persistence.database.di

import android.content.Context
import com.example.reltyleads.persistence.AppDatabase
import com.example.reltyleads.persistence.database.dao.BookingDao
import com.example.reltyleads.persistence.database.dao.ProjectDao
import com.example.reltyleads.persistence.database.dao.TowerDao
import com.example.reltyleads.persistence.database.dao.UnitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideBookingDao(appDatabase: AppDatabase): BookingDao = appDatabase.bookingDao()

    @Provides
    fun provideProjectDao(appDatabase: AppDatabase): ProjectDao = appDatabase.projectDao()

    @Provides
    fun provideTowerDao(appDatabase: AppDatabase): TowerDao = appDatabase.towerDao()

    @Provides
    fun provideUnitDao(appDatabase: AppDatabase): UnitDao = appDatabase.unitDao()
}