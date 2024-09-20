package com.example.reltyleads.persistence.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "bookings",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = UnitDb::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("unit_id"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class BookingDb(
    @NotNull val id: String,
    @NotNull @ColumnInfo("project_name") val projectName: String,
    @NotNull @ColumnInfo("tower_name") val towerName: String,
    @NotNull @ColumnInfo("floor_number") val floorNumber: Int,
    @NotNull @ColumnInfo("unit_number") val unitNumber: Int,
    @NotNull val size: Int,
    @NotNull @ColumnInfo("booking_advance") val bookingAdvance: Int,
    @NotNull @ColumnInfo("agreement_value") val agreementValue: Int,
    @NotNull @ColumnInfo("registration_date") val registrationDate: Long,
    @NotNull @ColumnInfo("applicant_name") val applicantName: String,
    @NotNull @ColumnInfo("applicant_id") val applicantId: String,
    @NotNull @ColumnInfo("co_applicant_ids") val coApplicantIds: String,
    @NotNull @ColumnInfo("created_at") val createdAt: Long,
    @NotNull @ColumnInfo("unit_id") val unitId: String
) {
    companion object {
        val Empty = BookingDb(
            id = "",
            projectName = "",
            towerName = "",
            floorNumber = 0,
            unitNumber = 0,
            size = 0,
            bookingAdvance = 0,
            agreementValue = 0,
            registrationDate = 0,
            applicantName = "",
            applicantId = "",
            coApplicantIds = "",
            createdAt = 0,
            unitId = ""
        )
    }
}