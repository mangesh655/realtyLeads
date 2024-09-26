package com.example.reltyleads.create.ui.create

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import com.example.reltyleads.BuildConfig
import com.example.reltyleads.R
import com.example.reltyleads.common.theme.model.DocumentType
import com.example.reltyleads.create.isEmailValid
import com.example.reltyleads.create.isNameValid
import com.example.reltyleads.create.isPhoneValid
import com.example.reltyleads.create.ui.create.viewmodel.CreateViewModel
import com.example.reltyleads.repository.model.ApplicantDocument
import com.example.reltyleads.theme.buttonColor
import com.example.reltyleads.theme.redCancelColor
import com.example.reltyleads.theme.sectionTitleColor
import com.example.reltyleads.theme.textFieldLabel
import com.example.reltyleads.theme.textFieldUnfocusedBorderColor
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ApplicantDetailsForm(viewModel: CreateViewModel) {

    Column(modifier = Modifier.fillMaxSize()) {

        viewModel.booking.applicantDetails.forEachIndexed { index, applicant ->
            ApplicantForm(
                viewModel = viewModel,
                index = index
            )
        }

        Button(
            onClick = { viewModel.addApplicant(isMainApplicant = false) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = sectionTitleColor
            ),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            Text("+ Add more co-applicant", textAlign = TextAlign.Start)
        }
    }
}

@Composable
fun ApplicantForm(
    viewModel: CreateViewModel,
    index: Int,
    modifier: Modifier = Modifier
) {
    val applicant = viewModel.booking.applicantDetails.get(index)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(if (applicant.isMainApplicant) R.drawable.ic_user else R.drawable.ic_users),
                contentDescription = if (applicant.isMainApplicant) "Applicant details" else "Co-applicant",
                tint = sectionTitleColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = if (applicant.isMainApplicant) "Applicant details" else "Co-applicant",
                color = sectionTitleColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
            if (!applicant.isMainApplicant) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = sectionTitleColor,
                    modifier = Modifier.size(24.dp).clickable {
                        viewModel.removeApplicant(index)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        FullNameTextField(onSuccess = { text ->
            viewModel.updateApplicant(applicant.copy(fullName = text), index)
        }, onError = {
            viewModel.updateApplicant(applicant.copy(fullName = ""), index)
        })
        PhoneNumberTextField(onSuccess = { text ->
            viewModel.updateApplicant(applicant.copy(mobile = text), index)
        }, onError = {
            viewModel.updateApplicant(applicant.copy(mobile = ""), index)
        })
        EmailAddressTextField(onSuccess = { text ->
            viewModel.updateApplicant(applicant.copy(email = text), index)
        }, onError = {
            viewModel.updateApplicant(applicant.copy(email = ""), index)
        })
        PanCardImageField(onSuccess = { text ->
            viewModel.updateApplicantDocument(ApplicantDocument(text, DocumentType.PAN_CARD), index)
        }, onError = {
            viewModel.removeApplicantDocument(DocumentType.PAN_CARD, index)
        })
        AadhaarCardImageField(onSuccess = { names ->
            if (names.first.isNotBlank()) {
                viewModel.updateApplicantDocument(
                    ApplicantDocument(
                        names.first,
                        DocumentType.AADHAR_CARD_FRONT
                    ), index
                )
            }

            if (names.second.isNotBlank()) {
                viewModel.updateApplicantDocument(
                    ApplicantDocument(
                        names.second,
                        DocumentType.AADHAR_CARD_BACK
                    ), index
                )
            }

        }, onError = {
            viewModel.removeApplicantDocument(DocumentType.AADHAR_CARD_FRONT, index)
            viewModel.removeApplicantDocument(DocumentType.AADHAR_CARD_BACK, index)
        })
    }
}

@Composable
fun AadhaarCardImageField(onSuccess: (Pair<String, String>) -> Unit, onError: () -> Unit) {
    var showDialogFront by remember { mutableStateOf(false) }
    var showDialogBack by remember { mutableStateOf(false) }
    var frontFileName by remember { mutableStateOf("") }
    var backFileName by remember { mutableStateOf("") }

    Column {
        Text(text = "Aadhaar image", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialogFront = true
                    showDialogBack = false
                },
            value = frontFileName,
            onValueChange = { input ->
                frontFileName = input
            },
            isError = frontFileName.isBlank(),
            supportingText = {
                if (frontFileName.isBlank())
                    Text(text = "Please upload aadhaar card front image", color = redCancelColor)
            },
            trailingIcon = {
                if (frontFileName.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "Cancel",
                        tint = redCancelColor,
                        modifier = Modifier.clickable {
                            frontFileName = ""
                            onError()
                        })
                }
            },
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Aadhaar image front", color = textFieldLabel)
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = if (frontFileName.isBlank()) redCancelColor else textFieldUnfocusedBorderColor
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialogBack = true
                    showDialogFront = false
                },
            value = backFileName,
            onValueChange = { input ->
                backFileName = input
            },
            isError = backFileName.isBlank(),
            supportingText = {
                if (backFileName.isBlank())
                    Text(text = "Please upload Aadhaar card back document", color = redCancelColor)
            },
            trailingIcon = {
                if (backFileName.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "Cancel",
                        tint = redCancelColor,
                        modifier = Modifier.clickable {
                            backFileName = ""
                            onError()
                        })
                }
            },
            placeholder = {
                Text(text = "Aadhaar image back", color = textFieldLabel)
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = if (backFileName.isBlank()) redCancelColor else textFieldUnfocusedBorderColor
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

    AttachmentPickerDialog(showDialogFront, DocumentType.AADHAR_CARD_FRONT) { fileName ->
        showDialogFront = false
        if (fileName.isNotBlank()) {
            frontFileName = fileName
            onSuccess(Pair(frontFileName, backFileName))
        } else if (frontFileName.isBlank()) {
            onError()
        }
    }
    AttachmentPickerDialog(showDialogBack, DocumentType.AADHAR_CARD_BACK) { fileName ->
        showDialogBack = false
        if (fileName.isNotBlank()) {
            backFileName = fileName
            onSuccess(Pair(frontFileName, backFileName))
        } else if (backFileName.isBlank()) {
            onError()
        }
    }
}

@Composable
fun PanCardImageField(onSuccess: (String) -> Unit, onError: () -> Unit) {

    var showDialog by remember { mutableStateOf(false) }
    var panCardFileName by remember { mutableStateOf("") }

    Column {
        Text(text = "PAN image", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialog = true
                },
            value = panCardFileName,
            onValueChange = { input ->
                panCardFileName = input
            },
            trailingIcon = {
                if (panCardFileName.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "Cancel",
                        tint = redCancelColor,
                        modifier = Modifier.clickable {
                            panCardFileName = ""
                            onError()
                        })
                }
            },
            isError = panCardFileName.isBlank(),
            supportingText = {
                if (panCardFileName.isBlank())
                    Text(text = "Please upload pan card image", color = redCancelColor)
            },
            enabled = false,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Pan image", color = textFieldLabel)
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = if (panCardFileName.isBlank()) redCancelColor else textFieldUnfocusedBorderColor
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

    AttachmentPickerDialog(showDialog, DocumentType.PAN_CARD) { fileName ->
        showDialog = false
        if (fileName.isNotBlank()) {
            panCardFileName = fileName
            onSuccess(panCardFileName)
        } else if (panCardFileName.isBlank()) {
            onError()
        }
    }
}

@Composable
fun CameraPicker(
    shouldLaunchCamera: Boolean,
    onImagePicked: (Uri?) -> Unit,
    resetCameraTrigger: () -> Unit
) {
    val context = LocalContext.current
    val fileUri = remember { mutableStateOf<Uri?>(null) }

    // Create a launcher for taking pictures
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                onImagePicked(fileUri.value)  // Pass the result URI back to the parent
            }
            resetCameraTrigger()  // Reset the camera trigger after completion
        }

    // Use snapshotFlow to observe state changes for `shouldLaunchCamera`
    LaunchedEffect(shouldLaunchCamera) {
        if (shouldLaunchCamera) {
            val file = createImageFile(context)  // Create a file to save the image
            fileUri.value = FileProvider.getUriForFile(
                context,
                "${BuildConfig.APPLICATION_ID}.provider",
                file
            )
            launcher.launch(fileUri.value)  // Launch the camera
        }
    }
}

private fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile("$timeStamp", ".png", storageDir)
}

fun saveImageToPrivateStorage(context: Context, uri: Uri, fileName: String): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val outputDir = context.filesDir // This is the app's private storage directory
    val outputFile = File(outputDir, fileName)

    inputStream.use { input ->
        FileOutputStream(outputFile).use { output ->
            input.copyTo(output)
        }
    }
    return outputFile
}

@Composable
fun PickImage(
    shouldLaunchPicker: Boolean,
    onImagePicked: (Uri?) -> Unit,
    onPickerCompleted: () -> Unit,
) {
    var uri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        uri = it
        onImagePicked(uri)
        onPickerCompleted()
    }
    LaunchedEffect(shouldLaunchPicker) {
        if (shouldLaunchPicker) {
            launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }
}

@Composable
fun FullNameTextField(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit,
    onError: () -> Unit
) {

    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Full name", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input
                isError = !isNameValid(text)
                if (isError) onError() else onSuccess(text)
            },
            placeholder = {
                Text(text = "Full name", color = textFieldLabel)
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = "Invalid Name Input")
                } else {
                    Text(text = "Name should have only characters.")
                }
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textFieldUnfocusedBorderColor,
                focusedBorderColor = sectionTitleColor,
                cursorColor = textFieldUnfocusedBorderColor
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun PhoneNumberTextField(onSuccess: (String) -> Unit, onError: () -> Unit) {
    var text by remember {
        mutableStateOf("") //TODO: take value from repository in case of edit
    }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Phone number", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input
                isError = !isPhoneValid(text)
                if (isError) onError() else onSuccess(text)
            },
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Phone number", color = textFieldLabel)
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = "Invalid Phone Input")
                }
            },
            leadingIcon = {
                //TODO: Country Codes List
                Text("+91", color = textFieldLabel)
            },
            trailingIcon = {
                //TODO: Phone Validation
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textFieldUnfocusedBorderColor,
                focusedBorderColor = sectionTitleColor,
                cursorColor = textFieldUnfocusedBorderColor
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun EmailAddressTextField(onError: () -> Unit, onSuccess: (String) -> Unit) {
    var text by remember {
        mutableStateOf("") //TODO: take value from repository in case edit
    }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Email address", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input
                isError = !isEmailValid(text)
                if (isError) onError() else onSuccess(text)
            },
            isError = isError,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Email address", color = textFieldLabel)
            },
            trailingIcon = {
                //TODO: Validation
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textFieldUnfocusedBorderColor,
                focusedBorderColor = sectionTitleColor,
                cursorColor = textFieldUnfocusedBorderColor
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun AttachmentPickerDialog(
    showDialog: Boolean = false,
    type: DocumentType,
    dismissRequest: (String) -> Unit
) {
    val context = LocalContext.current
    var shouldLaunchGalleryPicker by remember { mutableStateOf(false) }
    var shouldLaunchCameraPicker by remember { mutableStateOf(false) }
    var shouldLaunchDocumentPicker by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // Custom Dialog
        if (showDialog) {
            Dialog(onDismissRequest = { dismissRequest("") }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Choose an Option",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Gallery Option
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = {
                                shouldLaunchGalleryPicker = true
                                dismissRequest("")
                            },
                            shape = RoundedCornerShape(16.dp), // Rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor, // Background color (blue)
                                contentColor = Color.White // Text color (white)
                            )
                        ) { Text(text = "Gallery") }

                        //Camera Option
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = {
                                shouldLaunchCameraPicker = true
                                dismissRequest("")
                            },
                            shape = RoundedCornerShape(16.dp), // Rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor, // Background color (blue)
                                contentColor = Color.White // Text color (white)
                            )
                        ) { Text(text = "Camera") }

                        // Document Option
                        /*Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = {
                                shouldLaunchDocumentPicker = true
                                dismissRequest("")
                            },
                            shape = RoundedCornerShape(16.dp), // Rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor, // Background color (blue)
                                contentColor = Color.White // Text color (white)
                            )
                        ) { Text(text = "Document") }*/
                    }
                }
            }
        }
    }

    PickImage(
        shouldLaunchGalleryPicker,
        onImagePicked = { uri ->
            shouldLaunchGalleryPicker = false
            dismissRequest(getFileName(uri = uri, type = type, context = context))
        },
        onPickerCompleted = {
            shouldLaunchGalleryPicker = false
        }
    )

    CameraPicker(
        shouldLaunchCamera = shouldLaunchCameraPicker,
        onImagePicked = { uri ->
            shouldLaunchCameraPicker = false
            dismissRequest(getFileName(uri = uri, type = type, context = context))
        },
        resetCameraTrigger = {
            shouldLaunchCameraPicker = false
        })
}

fun getFileName(uri: Uri? = null, type: DocumentType, context: Context): String {
    uri?.let { image ->
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val fileName = "${type}_$timeStamp.png"
        return saveImageToPrivateStorage(context, image, fileName)?.name ?: ""
    }

    return ""
}
