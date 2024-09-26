package com.example.reltyleads.persistence.database.dao

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.reltyleads.persistence.database.entity.ApplicantDb
import com.example.reltyleads.persistence.database.entity.ApplicantDocumentDb
import com.example.reltyleads.persistence.database.entity.BookingDb

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBooking(bookingDb: BookingDb)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApplicant(applicantDb: ApplicantDb)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApplicantDocument(applicantDocumentDb: ApplicantDocumentDb)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBookingWithApplicants(
        bookingDb: BookingDb,
        applicantDbs: List<ApplicantDb>,
        applicantDocumentDbs: List<ApplicantDocumentDb>
    ): Boolean {
        return try {
            insertBooking(bookingDb)
            applicantDbs.forEach { applicantDb -> insertApplicant(applicantDb) }
            applicantDocumentDbs.forEach { applicantDocumentDb ->
                insertApplicantDocument(applicantDocumentDb)
            }
            true
        } catch (e: SQLiteException) {
            Log.e("BookingDao", "Error inserting booking and applicants", e)
            false
        }
    }

    @Transaction
    @Query("SELECT * FROM bookings ORDER BY created_at ASC")
    fun pagingSource(): PagingSource<Int, BookingDb>

    @Query("SELECT * FROM bookings ORDER BY created_at DESC")
    suspend fun getBookings(): List<BookingDb>

    @Query("SELECT * FROM bookings WHERE created_at > :from AND created_at < :to ORDER BY created_at DESC")
    suspend fun getBookingsInDateRange(from: Long, to: Long): List<BookingDb>

    @Query("SELECT * FROM bookings WHERE project_name LIKE '%' || :searchText || '%' OR main_applicant_name LIKE '%' || :searchText || '%' ORDER BY created_at DESC")
    suspend fun getBookingsBySearchText(searchText: String): List<BookingDb>
}