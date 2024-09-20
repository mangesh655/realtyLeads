package com.example.reltyleads.repository

import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb

interface InventoryRepository {

    suspend fun insertProjects(projects: List<ProjectDb>)

    suspend fun insertProject(project: ProjectDb)

    suspend fun deleteAllProjects()

    suspend fun insertTowers(towers: List<TowerDb>)

    suspend fun insertTower(towers: TowerDb)

    suspend fun deleteAllTowers()

    suspend fun insertUnits(units: List<UnitDb>)

    suspend fun insertUnit(units: UnitDb)

    suspend fun deleteAllUnits()

    suspend fun fetchProjects(): List<ProjectDb>

    suspend fun fetchProject(projectId: String): ProjectDb

    suspend fun fetchTowers(projectId: String): List<TowerDb>

    suspend fun fetchUnits(towerId: String): List<UnitDb>
}