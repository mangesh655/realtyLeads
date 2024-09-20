package com.example.reltyleads.persistence.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(tableName = "projects", primaryKeys = ["id"])
data class ProjectDb(
    @NotNull val id: String,
    @NotNull val name: String,
    @NotNull val developer: String,
    @NotNull val location: String
) {

    companion object {

        val Empty = ProjectDb(
            id = "",
            name = "",
            developer = "",
            location = ""
        )
    }
}