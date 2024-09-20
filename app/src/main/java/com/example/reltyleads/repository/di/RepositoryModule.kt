package com.example.reltyleads.repository.di

import com.example.reltyleads.repository.BookingRepository
import com.example.reltyleads.repository.BookingRepositoryImpl
import com.example.reltyleads.repository.InventoryRepository
import com.example.reltyleads.repository.InventoryRepositoryImpl
import com.example.reltyleads.repository.InventorySourceHelper.InventorySourceHelper
import com.example.reltyleads.repository.InventorySourceHelper.InventorySourceHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun provideBookingRepository(
        bookingRepositoryImpl: BookingRepositoryImpl
    ): BookingRepository

    @Binds
    @Singleton
    fun provideInventoryRepository(
        inventoryRepositoryImpl: InventoryRepositoryImpl
    ): InventoryRepository

    @Binds
    @Singleton
    fun provideInventorySourceHelper(
        inventorySourceHelperImpl: InventorySourceHelperImpl
    ): InventorySourceHelper


}