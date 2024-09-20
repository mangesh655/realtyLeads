package com.example.reltyleads.persistence.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "towers",
    primaryKeys = ["id"],
    foreignKeys = [
            ForeignKey(
                entity = ProjectDb::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("project_id"),
                onDelete = ForeignKey.CASCADE
                )]
)
data class TowerDb(
    @NotNull val id: String,
    @NotNull val name: String,
    @NotNull val floors: Int,
    @NotNull @ColumnInfo(name = "project_id") val projectId: String
) {

    companion object {

        val Empty = TowerDb(
            id = "",
            name = "",
            floors = 0,
            projectId = ""
        )
    }
}