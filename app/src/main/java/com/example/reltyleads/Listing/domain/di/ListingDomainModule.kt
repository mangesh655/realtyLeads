package com.example.reltyleads.Listing.domain.di

import com.example.reltyleads.Listing.domain.ListingUseCase
import com.example.reltyleads.Listing.domain.impl.ListingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal interface ListingDomainModule {

    @Binds
    @Singleton
    fun provideListingUseCase(
        listingUseCase: ListingUseCaseImpl
    ): ListingUseCase
}