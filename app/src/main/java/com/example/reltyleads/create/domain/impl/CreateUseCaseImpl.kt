package com.example.reltyleads.create.domain.impl

import android.util.Log
import androidx.compose.material3.darkColorScheme
import com.example.reltyleads.create.domain.CreateUseCase
import com.example.reltyleads.persistence.database.entity.ApplicantDb
import com.example.reltyleads.persistence.database.entity.ApplicantDocumentDb
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.persistence.database.entity.ProjectDb
import com.example.reltyleads.persistence.database.entity.TowerDb
import com.example.reltyleads.persistence.database.entity.UnitDb
import com.example.reltyleads.repository.BookingRepository
import com.example.reltyleads.repository.InventoryRepository
import com.example.reltyleads.repository.model.Booking
import java.util.Calendar
import javax.inject.Inject

class CreateUseCaseImpl @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val inventoryRepository: InventoryRepository
) : CreateUseCase {

    override suspend fun createBooking(booking: Booking) : Boolean {

        val applicantDbList = mutableListOf<ApplicantDb>()
        val applicantDocumentList = mutableListOf<ApplicantDocumentDb>()
        var mainApplicantName = ""
        var mainApplicantId = ""
        var coApplicantIds = ""

        booking.applicantDetails.forEach { applicant ->
            val applicantId = "APC_${Calendar.getInstance().timeInMillis}"

            if (applicant.isMainApplicant) {
                mainApplicantName = applicant.fullName
                mainApplicantId = applicantId
            } else {
                coApplicantIds =
                    if (coApplicantIds.isBlank()) applicantId else "$coApplicantIds, $applicantId"
            }
            val applicantDb = ApplicantDb(
                id = applicantId,
                name = applicant.fullName,
                mobile = applicant.mobile,
                email = applicant.email
            )

            applicantDbList.add(applicantDb)

            applicant.documents.forEach { document ->
                val applicantDocumentDb = ApplicantDocumentDb(
                    name = document.name,
                    type = document.documentType,
                    applicantId = applicantId
                )

                applicantDocumentList.add(applicantDocumentDb)
            }
        }

        val currentTime = Calendar.getInstance().timeInMillis
        val bookingDb = BookingDb(
            bookingId = "IRBK_${currentTime}",
            projectName = booking.projectName,
            towerName = booking.towerName,
            floorNumber = booking.floorNumber,
            unitNumber = booking.unitNumber,
            unitSize = booking.unitSize,
            agreementValue = booking.agreementValue,
            registrationDate = booking.registrationDate,
            mainApplicantName = mainApplicantName,
            mainApplicantId = mainApplicantId,
            coApplicantIds = coApplicantIds,
            createdAt = currentTime
        )

        Log.e("Mangesh", "createUseCase:createBooking: called", )

        return bookingRepository.createBooking(bookingDb, applicantDbList, applicantDocumentList)
    }

    override suspend fun fetchProjects(): List<ProjectDb> {
        return inventoryRepository.fetchProjects()
    }

    override suspend fun fetchProject(projectId: String): ProjectDb {
        return inventoryRepository.fetchProject(projectId)
    }

    override suspend fun fetchTowers(projectId: String): List<TowerDb> {
        return inventoryRepository.fetchTowers(projectId)
    }

    override suspend fun fetchUnits(towerId: String): List<UnitDb> {
        return inventoryRepository.fetchUnits(towerId)
    }
}