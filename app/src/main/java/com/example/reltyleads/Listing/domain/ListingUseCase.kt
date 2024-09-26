package com.example.reltyleads.Listing.domain

import androidx.paging.PagingData
import com.example.reltyleads.persistence.database.entity.BookingDb
import kotlinx.coroutines.flow.Flow

interface ListingUseCase {

    fun listingPagingData(): Flow<PagingData<BookingDb>>
}