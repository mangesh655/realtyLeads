package com.example.reltyleads.domain.di

import com.example.reltyleads.domain.CreateUseCase
import com.example.reltyleads.domain.impl.CreateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DomainModule {

    @Binds
    @Singleton
    fun provideBookingUseCase(
        bookingUseCase: CreateUseCaseImpl
    ): CreateUseCase
}