package com.example.reltyleads.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.persistence.database.entity.TowerDb

@Dao
interface TowerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTower(tower: TowerDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTowers(towers: List<TowerDb>)

    @Query("SELECT * FROM towers WHERE project_id=:projectId ORDER BY name ASC")
    suspend fun getTowersForProject(projectId: String): List<TowerDb>

    @Query("DELETE FROM towers")
    suspend fun deleteAll()

    /*@Query("DELETE FROM sqlite_sequence WHERE name = 'towers'")
    suspend fun deletePrimaryKeyIndex()*/
}