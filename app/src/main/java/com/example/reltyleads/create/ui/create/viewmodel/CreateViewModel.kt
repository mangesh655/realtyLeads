package com.example.reltyleads.create.ui.create.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.reltyleads.common.BaseViewModel
import com.example.reltyleads.common.theme.model.DocumentType
import com.example.reltyleads.create.isEmailValid
import com.example.reltyleads.create.isFloorNumberValid
import com.example.reltyleads.create.isNameValid
import com.example.reltyleads.create.isPhoneValid
import com.example.reltyleads.create.isUnitSizeValid
import com.example.reltyleads.create.domain.CreateUseCase
import com.example.reltyleads.repository.model.ApplicantDetails
import com.example.reltyleads.repository.model.ApplicantDocument
import com.example.reltyleads.repository.model.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val createUseCase: CreateUseCase
) : BaseViewModel() {

    var booking by mutableStateOf(Booking())
        private set

    val createBookingButtonStatus: Boolean by derivedStateOf {
        val isValid = validateBooking()
        Log.e("Mangesh", "createBookingButtonStatus: $isValid")
        isValid
    }

    private val requiredDocumentTypes =
        setOf(DocumentType.PAN_CARD, DocumentType.AADHAR_CARD_FRONT, DocumentType.AADHAR_CARD_BACK)

    private val _isBookingSuccess = MutableStateFlow<Boolean?>(null)
    val isBookingSuccess: StateFlow<Boolean?> = _isBookingSuccess


    init {
        addApplicant(true)
    }

    private fun validateBooking(): Boolean {

        Log.e("Mangesh", "validateBooking: Booking:${booking}")
        booking.applicantDetails.forEachIndexed { index, applicant ->
            Log.e("Mangesh", "validateBooking: Applicant:$index:$applicant")
            applicant.documents.forEachIndexed { index, document ->
                Log.e("Mangesh", "validateBooking: Document:$index:$document")
            }
        }

        val isProjectNameValid = booking.projectName.isNotBlank()
        val isTowerNameValid = booking.towerName.isNotBlank()
        val isFloorNumberValid = isFloorNumberValid(booking.floorNumber.toString())
        val isUnitNumberValid =
            booking.unitNumber.isNotBlank() // Assuming unitNumber must be non-empty
        val isUnitSizeValid = isUnitSizeValid(booking.unitSize.toString())
        val isRegistrationDateValid =
            booking.registrationDate > 0 // Assuming registration date must be a positive timestamp
        val isAgreementValueValid =
            booking.agreementValue >= 0 // Assuming agreement value must be non-negative

        // Validate ApplicantDetails
        val areApplicantsValid = booking.applicantDetails.all { applicant ->
            val isFullNameValid = isNameValid(applicant.fullName)
            val isMobileValid = isPhoneValid(applicant.mobile)
            val isEmailValid = isEmailValid(applicant.email)

            val presentDocumentTypes = applicant.documents.map { it.documentType }.toSet()
            val hasAllRequiredDocuments = requiredDocumentTypes.all { it in presentDocumentTypes }

            // Check if all fields in applicant are valid
            isFullNameValid && isMobileValid && isEmailValid && hasAllRequiredDocuments
        }

        // Return true if all validations pass
        return isProjectNameValid &&
                isTowerNameValid &&
                isFloorNumberValid &&
                isUnitNumberValid &&
                isUnitSizeValid &&
                isRegistrationDateValid &&
                isAgreementValueValid &&
                areApplicantsValid
    }

    fun addApplicant(isMainApplicant: Boolean) {
        val updatedApplicants = booking.applicantDetails.toMutableList()
        updatedApplicants.add(ApplicantDetails(isMainApplicant = isMainApplicant))
        updateApplicants(updatedApplicants)
    }

    fun createBooking() {
        Log.e("Mangesh", "createViewModel:createBooking: called ")
        viewModelScope.launch(Dispatchers.IO) {
            _isBookingSuccess.value = createUseCase.createBooking(booking)
        }
    }

    fun updateProjectName(value: String) {
        booking = booking.copy(projectName = value)
    }

    fun updateTowerName(value: String) {
        booking = booking.copy(towerName = value)
    }

    fun updateFloorNumber(value: Int) {
        booking = booking.copy(floorNumber = value)
    }

    fun updateUnitNumber(value: String) {
        booking = booking.copy(unitNumber = value)
    }

    fun updateUnitSize(value: Int) {
        booking = booking.copy(unitSize = value)
    }

    fun updateRegistrationDate(value: Long) {
        booking = booking.copy(registrationDate = value)
    }

    fun updateAgreementValue(value: Long) {
        booking = booking.copy(agreementValue = value)
    }

    private fun updateApplicants(value: List<ApplicantDetails>) {
        booking = booking.copy(applicantDetails = value)
    }

    fun updateApplicant(value: ApplicantDetails, index: Int) {
        val updatedApplicants = booking.applicantDetails.toMutableList()
        updatedApplicants[index] = value
        updateApplicants(updatedApplicants)
    }

    fun updateApplicantDocument(newDoc: ApplicantDocument, index: Int) {
        val applicant = booking.applicantDetails[index]
        val documentIndex =
            applicant.documents.indexOfFirst { it.documentType == newDoc.documentType }

        if (documentIndex != -1) {
            val updatedDocuments = applicant.documents.toMutableList()
            updatedDocuments[documentIndex] = newDoc
            updateApplicant(applicant.copy(documents = updatedDocuments), index)
        } else {
            updateApplicant(applicant.copy(documents = applicant.documents + newDoc), index)
        }
    }

    fun removeApplicantDocument(documentType: DocumentType, index: Int) {
        val applicant = booking.applicantDetails[index]
        val documentIndex = applicant.documents.indexOfFirst { it.documentType == documentType }

        if (documentIndex != -1) {
            val updatedDocuments = applicant.documents.toMutableList()
            updatedDocuments.removeAt(documentIndex)
            updateApplicant(applicant.copy(documents = updatedDocuments), index)
        }
    }

    fun removeApplicant(index: Int) {
        if (index != -1) {
            val updatedApplicants = booking.applicantDetails.toMutableList()
            updatedApplicants.removeAt(index)
            updateApplicants(updatedApplicants)
        }
    }
}