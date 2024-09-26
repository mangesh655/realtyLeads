package com.example.reltyleads.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.reltyleads.persistence.database.dao.BookingDao
import com.example.reltyleads.persistence.database.entity.ApplicantDb
import com.example.reltyleads.persistence.database.entity.ApplicantDocumentDb
import com.example.reltyleads.persistence.database.entity.BookingDb
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class BookingRepositoryImpl @Inject constructor(
    private val bookingDao: BookingDao
) : BookingRepository {

    override suspend fun createBooking(
        booking: BookingDb,
        applicantDbs: List<ApplicantDb>,
        applicantDocumentDbs: List<ApplicantDocumentDb>
    ): Boolean {
        return bookingDao.insertBookingWithApplicants(booking, applicantDbs, applicantDocumentDbs)
    }

    override fun bookingPagingSource(): Flow<PagingData<BookingDb>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { bookingDao.pagingSource() }
        ).flow
    }
}