package com.example.reltyleads.domain.impl

import com.example.reltyleads.domain.CreateUseCase
import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import com.example.reltyleads.repository.BookingRepository
import com.example.reltyleads.repository.InventoryRepository
import com.example.reltyleads.repository.model.Booking
import javax.inject.Inject

class CreateUseCaseImpl @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val inventoryRepository: InventoryRepository
) : CreateUseCase {

    override suspend fun createBooking(booking: Booking) {
        //val bookingDb = booking.mapToBookingDb()
        //bookingRepository.createBooking(bookingDb)
    }

    override suspend fun fetchProjects(): List<ProjectDb> {
        return inventoryRepository.fetchProjects()
    }

    override suspend fun fetchProject(projectId: String): ProjectDb {
        return inventoryRepository.fetchProject(projectId)
    }

    override suspend fun fetchTowers(projectId: String): List<TowerDb> {
        return inventoryRepository.fetchTowers(projectId)
    }

    override suspend fun fetchUnits(towerId: String): List<UnitDb> {
        return inventoryRepository.fetchUnits(towerId)
    }
}