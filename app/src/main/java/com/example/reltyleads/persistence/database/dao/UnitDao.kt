package com.example.reltyleads.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb

@Dao
interface UnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: UnitDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(units: List<UnitDb>)

    @Query("SELECT * FROM units WHERE tower_id=:towerId ORDER BY number ASC")
    suspend fun getUnitsForTower(towerId: String): List<UnitDb>

    @Query("DELETE FROM units")
    suspend fun deleteAll()

   /* @Query("DELETE FROM sqlite_sequence WHERE name = 'units'")
    suspend fun deletePrimaryKeyIndex()*/
}