package com.example.reltyleads.persistence.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "bookings",
    primaryKeys = ["booking_id"]
)
data class BookingDb(
    @NotNull @ColumnInfo("booking_id") val bookingId: String,
    @NotNull @ColumnInfo("project_name") val projectName: String,
    @NotNull @ColumnInfo("tower_name") val towerName: String,
    @NotNull @ColumnInfo("floor_number") val floorNumber: Int,
    @NotNull @ColumnInfo("unit_number") val unitNumber: String,
    @NotNull @ColumnInfo("unit_size") val unitSize: Int,
    @NotNull @ColumnInfo("agreement_value") val agreementValue: Long,
    @NotNull @ColumnInfo("registration_date") val registrationDate: Long,
    @NotNull @ColumnInfo("main_applicant_name") val mainApplicantName: String,
    @NotNull @ColumnInfo("main_applicant_id") val mainApplicantId: String,
    @NotNull @ColumnInfo("co_applicant_ids") val coApplicantIds: String,
    @NotNull @ColumnInfo("created_at") val createdAt: Long,
) {
    companion object {
        val Empty = BookingDb(
            bookingId = "",
            projectName = "",
            towerName = "",
            floorNumber = 0,
            unitNumber = "",
            unitSize = 0,
            agreementValue = 0,
            registrationDate = 0,
            mainApplicantName = "",
            mainApplicantId = "",
            coApplicantIds = "",
            createdAt = 0
        )
    }
}