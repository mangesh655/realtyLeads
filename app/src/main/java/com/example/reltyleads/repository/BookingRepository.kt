package com.example.reltyleads.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.reltyleads.persistence.database.entity.ApplicantDb
import com.example.reltyleads.persistence.database.entity.ApplicantDocumentDb
import com.example.reltyleads.persistence.database.entity.BookingDb
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    suspend fun createBooking(
        booking: BookingDb,
        applicantDbs: List<ApplicantDb>,
        applicantDocumentDbs: List<ApplicantDocumentDb>
    ): Boolean

    fun bookingPagingSource(): Flow<PagingData<BookingDb>>
}