package com.example.reltyleads.Listing.domain.impl

import androidx.paging.PagingData
import com.example.reltyleads.Listing.domain.ListingUseCase
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.repository.BookingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListingUseCaseImpl @Inject constructor(
   private val bookingRepository: BookingRepository
) : ListingUseCase {

    override fun listingPagingData(): Flow<PagingData<BookingDb>> {
        return bookingRepository.bookingPagingSource()
    }
}