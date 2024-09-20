package com.example.reltyleads.persistence.database.dao

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
    suspend fun insertBooking(bookingDb: BookingDb, applicantDb: ApplicantDb, coApplicantDb: List<ApplicantDb>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApplicant(applicantDb: ApplicantDb)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApplicantDocuments(applicantDocumentDb: ApplicantDocumentDb) {
        //TODO: Write a complete query
    }

    @Query("SELECT * FROM bookings ORDER BY created_at DESC")
    suspend fun getBookings(): List<BookingDb>

    @Query("SELECT * FROM bookings WHERE created_at > :from AND created_at < :to ORDER BY created_at DESC")
    suspend fun getBookingsInDateRange(from: Long, to: Long): List<BookingDb>

    @Query("SELECT * FROM bookings WHERE project_name LIKE '%' || :searchText || '%' OR applicant_name LIKE '%' || :searchText || '%' ORDER BY created_at DESC")
    suspend fun getBookingsBySearchText(searchText: String): List<BookingDb>
}