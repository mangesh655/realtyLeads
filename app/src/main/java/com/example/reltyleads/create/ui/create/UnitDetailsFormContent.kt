package com.example.reltyleads.create.ui.create

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reltyleads.R
import com.example.reltyleads.common.ui.formatNumberWithComma
import com.example.reltyleads.create.isFloorNumberValid
import com.example.reltyleads.create.isNumber
import com.example.reltyleads.create.isPhoneValid
import com.example.reltyleads.create.isUnitSizeValid
import com.example.reltyleads.create.ui.create.viewmodel.CreateViewModel
import com.example.reltyleads.theme.sectionTitleColor
import com.example.reltyleads.theme.textFieldLabel
import com.example.reltyleads.theme.textFieldUnfocusedBorderColor
import com.example.reltyleads.theme.trailingIconColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun UnitDetailsForm(viewModel: CreateViewModel) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.floor_plan),
                contentDescription = "Unit Details",
                tint = sectionTitleColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Unit Details",
                color = sectionTitleColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        ProjectNameTextField(onSuccess = {
            viewModel.updateProjectName(it)
        }, onError = {
            viewModel.updateProjectName("")
        })
        TowerNameTextField(onSuccess = {
            viewModel.updateTowerName(it)
        }, onError = {
            viewModel.updateTowerName("")
        })
        FloorNumberTextField(onSuccess = {
            viewModel.updateFloorNumber(it.toIntOrNull() ?: 1)
        }, onError = {
            viewModel.updateFloorNumber(1)
        })
        UnitNumberTextField(onSuccess = {
            viewModel.updateUnitNumber(it)
        }, onError = {
            viewModel.updateUnitNumber("")
        })
        UnitSizeTextField(onSuccess = {
            viewModel.updateUnitSize(it.toInt())
        }, onError = {
            viewModel.updateUnitSize(-1)
        })
        ExpectedRegistrationDateTextField(onSuccess = {
            viewModel.updateRegistrationDate(it)
        }, onError = {
            viewModel.updateRegistrationDate(System.currentTimeMillis())
        })
        AgreementValueTextField(onSuccess = {
            viewModel.updateAgreementValue(it)
        }, onError = {
            viewModel.updateAgreementValue(-1)
        })
    }
}

@Composable
fun AgreementValueTextField(
    modifier: Modifier = Modifier,
    onSuccess: (Long) -> Unit,
    onError: () -> Unit
) {

    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Agreement value",
            color = textFieldLabel,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textState,
            onValueChange = { input ->
                textState = input
                val previousCursorPosition = input.selection.start
                val cleanInput = input.text.replace(",", "")
                if (isNumber(cleanInput)) {
                    isError = false
                    onSuccess(cleanInput.toLong())
                    val formattedInput = formatNumberWithComma(cleanInput)
                    val cursorOffset = formattedInput.length - cleanInput.length
                    val newCursorPosition = (previousCursorPosition + cursorOffset).coerceIn(
                        0,
                        formattedInput.length
                    )
                    textState =
                        TextFieldValue(formattedInput, selection = TextRange(newCursorPosition))
                } else {
                    isError = true
                    onError()
                }
            },
            isError = isError,
            supportingText = {
                if(isError) Text("Invalid Agreement value")
            },
            trailingIcon = {
                Text("INR", color = trailingIconColor)
            },
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Agreement value", color = textFieldLabel)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
fun ProjectNameTextField(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit,
    onError: () -> Unit
) {

    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Project name", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input
                isError = text.isEmpty() || text.isBlank()
                if (isError) onError() else onSuccess(text)
            },
            isError = isError,
            placeholder = {
                Text(text = "Project name", color = textFieldLabel)
            },
            supportingText = {
                if (isError) {
                    Text(text = "Project name cannot be blank")
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
fun TowerNameTextField(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit,
    onError: () -> Unit
) {

    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Tower name", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input
                isError = text.isEmpty() || text.isBlank()
                if (isError) onError() else onSuccess(text)
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = "Tower name cannot be blank")
                }
            },
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Tower name", color = textFieldLabel)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
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
fun FloorNumberTextField(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit,
    onError: () -> Unit
) {

    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Floor number", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input

                isError = !isFloorNumberValid(text)
                Log.e("Mangesh", "FloorNumberTextField: ${isError}", )
                if (isError) onError() else onSuccess(text)
            },
            placeholder = {
                Text(text = "Floor number", color = textFieldLabel)
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = "Invalid Floor Number")
                }
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
fun UnitNumberTextField(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit,
    onError: () -> Unit
) {

    var text by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Unit number", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input
                isError = text.isEmpty() || text.isBlank()
                if (isError) onError() else onSuccess(text)
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text("Unit number cannot be blank.")
                }
            },
            placeholder = {
                Text(text = "Unit number", color = textFieldLabel)
            },
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
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
fun UnitSizeTextField(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit,
    onError: () -> Unit
) {

    var text by remember {
        mutableStateOf("") //TODO: take value from repository in case edit
    }
    var isError by remember { mutableStateOf(false) }

    Column {
        Text(text = "Unit size", color = textFieldLabel, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { input ->
                text = input
                isError = !isUnitSizeValid(text)
                if (isError) onError() else onSuccess(text)
            },
            placeholder = {
                Text(text = "Unit size", color = textFieldLabel)
            },
            trailingIcon = {
                Text(text = "sqft", color = trailingIconColor)
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = "Invalid unit size input")
                }
            },
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
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
fun ExpectedRegistrationDateTextField(
    modifier: Modifier = Modifier,
    onSuccess: (Long) -> Unit,
    onError: () -> Unit
) {

    var selectedDate by remember { mutableLongStateOf(Calendar.getInstance().timeInMillis) }
    //keeping record of current date
    onSuccess(selectedDate)
    var showModal by remember { mutableStateOf(false) }

    val date = Date(selectedDate)
    val formattedDate = SimpleDateFormat("dd MMM, YYYY", Locale.getDefault()).format(date)

    Column {
        Text(
            text = "Expected registration date",
            color = textFieldLabel,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showModal = true
                },
            value = formattedDate,
            enabled = false,
            onValueChange = { input ->
                if (input.isNotBlank()) {
                    onSuccess(selectedDate)
                } else {
                    onError()
                }
            },
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "Date Set Icon",
                    tint = trailingIconColor
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textFieldUnfocusedBorderColor,
                focusedBorderColor = sectionTitleColor,
                cursorColor = textFieldUnfocusedBorderColor,
                disabledBorderColor = textFieldUnfocusedBorderColor,
                disabledTextColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }

    if (showModal) {
        DatePickerModal(
            onDateSelected = { mDate ->
                mDate?.let { selectedDate = it }
                showModal = false
            },
            onDismiss = {
                showModal = false
            }
        )
    }
}