package com.example.reltyleads.create.domain

import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import com.example.reltyleads.repository.model.Booking

interface CreateUseCase {

    suspend fun createBooking(booking: Booking) : Boolean

    suspend fun fetchProjects(): List<ProjectDb>

    suspend fun fetchProject(projectId: String) : ProjectDb

    suspend fun fetchTowers(projectId: String): List<TowerDb>

    suspend fun fetchUnits(towerId: String): List<UnitDb>

}