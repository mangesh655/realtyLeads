package com.example.reltyleads.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.persistence.database.entity.ProjectDb

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectDb>)

    @Query("SELECT * FROM projects ORDER BY name ASC")
    suspend fun getProjects(): List<ProjectDb>

    @Query("DELETE FROM projects")
    suspend fun deleteAll()

    @Query("SELECT * FROM projects WHERE id=:projectId ORDER BY name ASC")
    suspend fun getProjectById(projectId: String): ProjectDb

    /*@Query("DELETE FROM sqlite_sequence WHERE name = 'projects'")
    suspend fun deletePrimaryKeyIndex()*/
}