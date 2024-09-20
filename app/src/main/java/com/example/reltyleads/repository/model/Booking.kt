package com.example.reltyleads.repository.model

import com.example.reltyleads.common.theme.model.DocumentType


data class Booking(
    val projectName: String = "",
    val towerName: String = "",
    val floorNumber: Int = 0,
    val unitNumber: Int = 0,
    val unitSize: Int = 0,
    val registrationDate: Long = 0,
    val agreementValue: Int = 0,
    val applicantDetails: List<ApplicantDetails> = listOf()
)

data class ApplicantDetails(
    private val fullName: String = "",
    private val mobile: String = "",
    private val email: String = "",
    private val isMainApplicant: Boolean = false,
    private val documents: List<ApplicantDocuments> = listOf()
)

data class ApplicantDocuments(
    val name: String,
    val documentType: DocumentType
)