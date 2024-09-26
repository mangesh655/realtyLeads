package com.example.reltyleads.create.ui.create

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val currentDateInMillis = remember { Calendar.getInstance().timeInMillis }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDateInMillis,
        selectableDates = PresentOrFutureSelectableDates
    )

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
object PresentOrFutureSelectableDates : SelectableDates {
    private val now = LocalDate.now()
    private val dayStart = now.atTime(0, 0, 0, 0).toEpochSecond(ZoneOffset.UTC) * 1000

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= dayStart
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= now.year
    }
}