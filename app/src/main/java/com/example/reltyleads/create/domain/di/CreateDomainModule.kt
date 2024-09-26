package com.example.reltyleads.create.domain.di

import com.example.reltyleads.create.domain.CreateUseCase
import com.example.reltyleads.create.domain.impl.CreateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface CreateDomainModule {

    @Binds
    @Singleton
    fun provideBookingUseCase(
        createUseCase: CreateUseCaseImpl
    ): CreateUseCase
}