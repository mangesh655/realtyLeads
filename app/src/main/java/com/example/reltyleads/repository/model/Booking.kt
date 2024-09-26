package com.example.reltyleads.repository.model

import com.example.reltyleads.common.theme.model.DocumentType


data class Booking(
    val projectName: String = "",
    val towerName: String = "",
    val floorNumber: Int = 0,
    val unitNumber: String = "",
    val unitSize: Int = 0,
    val registrationDate: Long = 0,
    val agreementValue: Long = 0,
    val applicantDetails: List<ApplicantDetails> = listOf()
)

data class ApplicantDetails(
    val fullName: String = "",
    val mobile: String = "",
    val email: String = "",
    val isMainApplicant: Boolean = false,
    val documents: List<ApplicantDocument> = listOf()
)

data class ApplicantDocument(
    var name: String,
    var documentType: DocumentType
)