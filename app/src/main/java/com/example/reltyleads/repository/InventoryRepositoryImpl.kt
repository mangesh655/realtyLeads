package com.example.reltyleads.repository

import com.example.reltyleads.Exceptions.ProjectFetchException
import com.example.reltyleads.persistence.database.dao.ProjectDao
import com.example.reltyleads.persistence.database.dao.TowerDao
import com.example.reltyleads.persistence.database.dao.UnitDao
import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val projectDao: ProjectDao,
    private val towerDao: TowerDao,
    private val unitDao: UnitDao
) : InventoryRepository {

    override suspend fun insertProjects(projects: List<ProjectDb>) {
        projectDao.insertProjects(projects)
    }

    override suspend fun insertProject(project: ProjectDb) {
        projectDao.insertProject(project)
    }

    override suspend fun deleteAllProjects() {
        //first delete all the entries
        projectDao.deleteAll()
        //then delete primary key index
        //projectDao.deletePrimaryKeyIndex()
    }

    override suspend fun insertTowers(towers: List<TowerDb>) {
        towerDao.insertTowers(towers)
    }

    override suspend fun insertTower(tower: TowerDb) {
        towerDao.insertTower(tower)
    }

    override suspend fun deleteAllTowers() {
        //first delete all the entries
        towerDao.deleteAll()
        //then delete primary key index
        //towerDao.deletePrimaryKeyIndex()
    }

    override suspend fun insertUnits(units: List<UnitDb>) {
        unitDao.insertUnits(units)
    }

    override suspend fun insertUnit(unit: UnitDb) {
        unitDao.insertUnit(unit)
    }

    override suspend fun deleteAllUnits() {
        //first delete all the entries
        unitDao.deleteAll()
        //then delete primary key index
        //unitDao.deletePrimaryKeyIndex()
    }

    override suspend fun fetchProjects(): List<ProjectDb> {
        return try {
            projectDao.getProjects()
        } catch (ignored: Exception) {
            throw ProjectFetchException
        }
    }

    override suspend fun fetchProject(projectId: String): ProjectDb {
        return try {
            projectDao.getProjectById(projectId)
        } catch (ignored: Exception) {
            throw ProjectFetchException
        }
    }

    override suspend fun fetchTowers(projectId: String): List<TowerDb> {
        return try {
            towerDao.getTowersForProject(projectId)
        } catch (ignored: Exception) {
            throw ProjectFetchException
        }
    }

    override suspend fun fetchUnits(towerId: String): List<UnitDb> {
        return try {
            unitDao.getUnitsForTower(towerId)
        } catch (ignored: Exception) {
            throw ProjectFetchException
        }
    }
}