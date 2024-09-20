package com.example.reltyleads.repository

import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import com.example.reltyleads.repository.model.Booking

interface BookingRepository {

    suspend fun createBooking(booking: BookingDb)
}