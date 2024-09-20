package com.example.reltyleads.repository

import com.example.reltyleads.Exceptions.ProjectFetchException
import com.example.reltyleads.persistence.database.dao.BookingDao
import com.example.reltyleads.persistence.database.dao.ProjectDao
import com.example.reltyleads.persistence.database.dao.TowerDao
import com.example.reltyleads.persistence.database.dao.UnitDao
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import com.example.reltyleads.repository.model.Booking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepositoryImpl @Inject constructor(
    private val bookingDao: BookingDao
) : BookingRepository {

    override suspend fun createBooking(booking: BookingDb) {
        //bookingDao.insertBooking(booking)
    }
}