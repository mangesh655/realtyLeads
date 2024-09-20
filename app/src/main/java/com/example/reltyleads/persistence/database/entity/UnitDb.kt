package com.example.reltyleads.persistence.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "units",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = TowerDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tower_id"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class UnitDb(
    @NotNull val id: String,
    @NotNull val number: String,
    @NotNull val floor: Int,
    @NotNull val size: Int,
    @NotNull @ColumnInfo(name = "tower_id") val towerId: String
) {

    companion object {

        val Empty = UnitDb(
            id = "",
            number = "",
            floor = -1,
            size = 0,
            towerId = ""
        )
    }
}