package com.example.reltyleads.persistence.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.reltyleads.common.theme.model.DocumentType
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "applicantDocuments",
    primaryKeys = ["name"],
    foreignKeys = [
        ForeignKey(
            ApplicantDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("applicant_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ApplicantDocumentDb(
    @NotNull val name: String,
    @NotNull val type: DocumentType,
    @NotNull @ColumnInfo("applicant_id") val applicantId: String
) {

    companion object {

        val Empty = ApplicantDocumentDb(
            name = "",
            type = DocumentType.NONE,
            applicantId = ""
        )
    }
}
