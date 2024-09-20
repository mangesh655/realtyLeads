package com.example.reltyleads.persistence.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(tableName = "applicants", primaryKeys = ["id"])
data class ApplicantDb(
    @NotNull val id: String,
    @NotNull val name: String,
    @NotNull val mobile: String,
    @NotNull val email: String
) {

    companion object {

        val Empty = ApplicantDb(
            id = "",
            name = "",
            mobile = "",
            email = ""
        )
    }
}