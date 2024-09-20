package com.example.reltyleads.create.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.reltyleads.R
import com.example.reltyleads.Listing.ui.displayCutoutWindowInsets
import com.example.reltyleads.common.ui.BackIcon
import com.example.reltyleads.common.ui.formatNumberWithComma
import com.example.reltyleads.create.isNumber
import com.example.reltyleads.create.model.FormField
import com.example.reltyleads.create.model.InputType
import com.example.reltyleads.create.model.TextInputType
import com.example.reltyleads.create.viewmodel.CreateViewModel
import com.example.reltyleads.theme.buttonColor
import com.example.reltyleads.theme.sectionTitleColor
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CreateRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel = hiltViewModel()
) {
    CreateScreenContent(modifier = modifier, viewModel, onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateScreenContent(
    modifier: Modifier,
    viewModel: CreateViewModel,
    onBackClick: () -> Unit
) {

    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            CreateTopBar(
                modifier = modifier,
                onNavigationIconClick = onBackClick,
                topAppBarScrollBehavior
            )
        },
        bottomBar = { CreateBottomBar(modifier) },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->

        PageContent(
            contentPadding = innerPadding,
            modifier = Modifier
                .padding(innerPadding)
                .windowInsetsPadding(displayCutoutWindowInsets)
                .fillMaxSize(),
            viewModel = viewModel
        )
    }
}

@Composable
fun PageContent(contentPadding: PaddingValues, viewModel: CreateViewModel, modifier: Modifier) {

    val unitDetailsForm by viewModel.unitsDetailsForm.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormSection(
            sectionName = "Unit Details",
            sectionIcon = R.drawable.floor_plan,
            formFields = unitDetailsForm,
            viewModel = viewModel,
            modifier = modifier
        )

        ApplicantFormSection(
            contentPadding = contentPadding,
            viewModel = viewModel,
            modifier = modifier
        )
    }
}

@Composable
fun ApplicantFormSection(
    contentPadding: PaddingValues,
    viewModel: CreateViewModel,
    modifier: Modifier
) {

    val applicants = remember { mutableStateListOf(getApplicantFormContent(true)) }

    Column(modifier = Modifier.fillMaxSize()) {
        applicants.forEachIndexed { _, applicant ->

            FormSection(
                sectionName = if (applicant.first) "Applicant details" else "Co-applicant",
                sectionIcon = if (applicant.first) R.drawable.ic_user else R.drawable.ic_users,
                formFields = applicant.second,
                viewModel = viewModel,
                modifier = modifier
            )
        }

        Button(
            onClick = { applicants.add(getApplicantFormContent(false)) },
        ) {
            Text("+ Add more co-applicant", textAlign = TextAlign.Start, color = sectionTitleColor)
        }
    }
}

fun getApplicantFormContent(isMainApplicant: Boolean): Pair<Boolean, MutableList<FormField>> {
    return Pair(isMainApplicant, mutableListOf(
        FormField(
            placeholder = "Full name",
            type = InputType.Text(TextInputType.NAME),
            text = "",
            isVisible = true,
            singleLine = true,
        ), FormField(
            placeholder = "Phone number",
            type = InputType.Text(TextInputType.PHONE),
            text = "",
            isVisible = true,
            singleLine = true,
            leadingIcon = {
                Text("+91", color = Color.Gray, modifier = Modifier.padding(4.dp))
            },
            trailingIcon = {
                //TODO: if validated
            }
        ), FormField(
            placeholder = "Email Address",
            type = InputType.Text(TextInputType.EMAIL),
            text = "",
            isVisible = true,
            singleLine = true,
            trailingIcon = {
                //TODO: if validated
            }
        ), FormField(
            placeholder = "PAN image",
            type = InputType.File("pan_card"),
            text = "",
            isVisible = true,
            isEnabled = false,
            singleLine = true,
            trailingIcon = {
                //TODO: to upload and remove
            }
        ), FormField(
            placeholder = "Aadhaar image Front",
            type = InputType.File("aadhaar_image_front"),
            text = "",
            isVisible = true,
            isEnabled = false,
            singleLine = true,
            trailingIcon = {
                //TODO: to upload and remove
            }
        ), FormField(
            placeholder = "Aadhar image back",
            type = InputType.File("aadhaar_image_back"),
            text = "",
            isVisible = true,
            isEnabled = false,
            singleLine = true,
            trailingIcon = {
            }
        )
    ))
}

@Composable
fun FormSection(
    sectionIcon: Int = R.drawable.ic_launcher_foreground,
    formFields: List<FormField> = listOf(),
    sectionName: String = "",
    viewModel: CreateViewModel,
    modifier: Modifier = Modifier
) {
    val mutableFormFields = formFields.toMutableList()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(sectionIcon),
                contentDescription = sectionName,
                tint = sectionTitleColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = sectionName,
                color = sectionTitleColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        mutableFormFields.forEachIndexed { index, field ->
            CustomInputFields(
                onValueChange = {
                    viewModel.onFieldValueChanged(it, field) { text ->
                        mutableFormFields[index] = field.copy(text = text)
                    }
                }, modifier = Modifier.fillMaxWidth(), field
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}

@Composable
fun CustomInputFields(
    onValueChange: (Any) -> Unit,
    modifier: Modifier = Modifier,
    formField: FormField,
) {
    when (formField.type) {
        is InputType.Text -> CustomTextInputField(onValueChange, modifier, formField)
        is InputType.Dropdown -> CustomDropdownInputField(
            (formField.type as InputType.Dropdown).options, onValueChange, modifier, formField
        )

        is InputType.Date -> CustomDateInputField(
            (formField.type as InputType.Date).dateFormat, onValueChange, modifier, formField
        )

        is InputType.File -> CustomFileInputField(
            (formField.type as InputType.File).fileName, onValueChange, modifier, formField
        )
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CustomFileInputField(
    fileName: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    field: FormField
) {
    val context = LocalContext.current
    var showdialog by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableIntStateOf(0) }
    var shouldLaunchGalleryPicker by remember { mutableStateOf(false) }
    var shouldLaunchCameraPicker by remember { mutableStateOf(false) }

    CustomTextInputField(onValueChange = {
        showdialog = true
    }, modifier, field)

    CustomAttachmentPickerDialog(showdialog) { option ->
        showdialog = false
        selectedOption = option
    }

    when (selectedOption) {
        1 -> {
            //Gallery
            shouldLaunchGalleryPicker = true
        }

        2 -> {
            //Camera
            shouldLaunchCameraPicker = true
        }

        3 -> {
            //Document
        }
    }

    PickImage(
        shouldLaunchPicker = shouldLaunchGalleryPicker,
        onImagePicked = { uri ->
            uri?.let { image ->

                val fileName =
                    "IMG_${(field.type as InputType.File).fileName}_${System.currentTimeMillis()}.jpg"
                val savedFile = saveImageToPrivateStorage(context, image, fileName)
                savedFile?.let { file ->
                    //TODO: viewModel.insertFile(file)  // Save the file path to Room DB
                }
            }
            //resetting selectedOption after image is selected to re-initiate the flow if needed
            selectedOption = 0
        },
        onPickerCompleted = {
            shouldLaunchGalleryPicker = false
            //resetting selectedOption after image is selected to re-initiate the flow if needed
            selectedOption = 0
        }
    )
}

@Composable
fun CustomDateInputField(
    dateFormat: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    field: FormField
) {
    var selectedDate by remember { mutableStateOf<Long?>(System.currentTimeMillis()) }
    var showModal by remember { mutableStateOf(false) }

    val date = Date(selectedDate!!)
    val formattedDate = SimpleDateFormat(dateFormat, Locale.getDefault()).format(date)
    field.text = formattedDate
    CustomTextInputField(onValueChange = {
        showModal = true
    }, modifier, field)

    if (showModal) {
        DatePickerModal(
            onDateSelected = {
                selectedDate = it
                showModal = false
            },
            onDismiss = {
                showModal = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownInputField(
    options: List<Pair<String, String>>,
    onValueChange: (Pair<String, String>) -> Unit,
    modifier: Modifier = Modifier,
    field: FormField
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(options.getOrElse(0) { Pair("", "") }.second) }
    var searchText by remember { mutableStateOf(options.getOrElse(0) { Pair("", "") }.second) }

    // Use LaunchedEffect to handle changes in the options list
    LaunchedEffect(options) {
        // Reset the selection to the first option if the list changes
        selectedText = options.getOrElse(0) { Pair("", "") }.second
        searchText = selectedText
    }

    // Filter options based on user input
    val filteredOptions = options.filter {
        it.second.contains(searchText, ignoreCase = true)
    }
    Text(text = field.placeholder, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(4.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
            expanded = !expanded
        }) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    expanded = filteredOptions.isNotEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                if (filteredOptions.isEmpty()) {
                    return@ExposedDropdownMenu
                }
                filteredOptions.forEach { item ->
                    DropdownMenuItem(text = { Text(text = item.second) }, onClick = {
                        selectedText = item.second
                        searchText = selectedText
                        onValueChange(item)
                        expanded = false
                    })
                }
            }
        }
    }
}

@Composable
fun CustomTextInputField(
    onValueChange: (String) -> Unit, modifier: Modifier, field: FormField
) {
    val isKeyboardTypeNumber =
        field.keyboardType == KeyboardType.Phone || field.keyboardType == KeyboardType.Number
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val colorBorder =
        if (field.isError) MaterialTheme.colorScheme.error else if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
            alpha = 0.3f
        )

    var textState by remember { mutableStateOf(TextFieldValue(field.text)) }

    Column {
        Text(text = field.placeholder, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        BasicTextField(
            enabled = field.isEnabled,
            modifier = Modifier
                .height(60.dp)
                .clickable {
                    if (!field.isEnabled) {
                        onValueChange(field.text)
                    }
                },
            value = if (isKeyboardTypeNumber) {
                val cleanTextInput = textState.text.replace(",", "")
                if (isNumber(cleanTextInput)) textState else TextFieldValue("")
            } else textState,
            onValueChange = { input ->
                if (isKeyboardTypeNumber) {
                    val previousCursorPosition = input.selection.start
                    val cleanInput = input.text.replace(",", "")
                    if (isNumber(cleanInput)) {
                        onValueChange(cleanInput)
                        val formattedInput = formatNumberWithComma(cleanInput)
                        val cursorOffset = formattedInput.length - cleanInput.length
                        val newCursorPosition = (previousCursorPosition + cursorOffset).coerceIn(
                            0,
                            formattedInput.length
                        )
                        textState =
                            TextFieldValue(formattedInput, selection = TextRange(newCursorPosition))
                    }
                } else onValueChange(textState.text)
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            maxLines = field.maxLines,
            singleLine = field.singleLine,
            interactionSource = interactionSource,
            visualTransformation = if (field.keyboardType == KeyboardType.Password) {
                if (field.isVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = field.keyboardType, imeAction = field.imeAction
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 1.dp, shape = RoundedCornerShape(8.dp), color = Color.Black
                        )
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .focusRequester(focusRequester)
                ) {
                    if (field.leadingIcon != null) {
                        field.leadingIcon?.invoke()
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (textState.text.isEmpty()) {
                            Text(
                                text = field.placeholder,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorBorder,
                            )
                        }

                        innerTextField()
                    }
                    if (field.trailingIcon != null) {
                        field.trailingIcon?.invoke()
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        )
        Text(
            text = if (field.isError) field.errorMessage else "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.height(5.dp)
        )
    }
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

/*
@Composable
fun CameraPicker(
    shouldLaunchCamera: Boolean,
    onImagePicked: (Uri?) -> Unit,
    resetCameraTrigger: () -> Unit
) {
    val context = LocalContext.current
    val fileUri = remember { mutableStateOf<Uri?>(null) }

    // Create a launcher for taking pictures
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
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
                "${context.packageName}.fileprovider",
                file
            )
            launcher.launch(fileUri.value)  // Launch the camera
        }
    }
}
*/

// Function to create an image file for saving the captured image
private fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
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
fun CreateBottomBar(modifier: Modifier = Modifier) {

    BottomAppBar {
        CreateBookingButton(isEnabled = false)
    }
}

@Composable
fun CreateBookingButton(modifier: Modifier = Modifier, isEnabled: Boolean) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = {

        },
        shape = RoundedCornerShape(16.dp), // Rounded corners
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray
        ),
        enabled = isEnabled
    ) { Text(text = "Create Booking") }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTopBar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = {
            Text(
                text = "Booking Form",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        navigationIcon = {
            BackIcon(
                onClick = onNavigationIconClick,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Composable
fun CustomAttachmentPickerDialog(showDialog: Boolean, dismissRequest: (Int) -> Unit) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // Custom Dialog
        if (showDialog) {
            Dialog(onDismissRequest = { dismissRequest(0) }) {
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
                                .padding(8.dp), onClick = {
                                dismissRequest(1)
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
                                dismissRequest(2)
                            },
                            shape = RoundedCornerShape(16.dp), // Rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor, // Background color (blue)
                                contentColor = Color.White // Text color (white)
                            )
                        ) { Text(text = "Camera") }

                        // Document Option
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = {
                                dismissRequest(3)
                            },
                            shape = RoundedCornerShape(16.dp), // Rounded corners
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor, // Background color (blue)
                                contentColor = Color.White // Text color (white)
                            )
                        ) { Text(text = "Document") }
                    }
                }
            }
        }
    }
}
