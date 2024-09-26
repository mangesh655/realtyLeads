package com.example.reltyleads.create.ui.create

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.reltyleads.create.ui.create.viewmodel.CreateViewModel
import com.example.reltyleads.theme.buttonColor

@Composable
fun CreateBottomBar(
    modifier: Modifier = Modifier, viewModel: CreateViewModel, onNavigateToListing: () -> Unit
) {

    BottomAppBar {
        CreateBookingButton(viewModel = viewModel, onNavigateToListing = onNavigateToListing)
    }
}

@Composable
fun CreateBookingButton(
    modifier: Modifier = Modifier, viewModel: CreateViewModel, onNavigateToListing: () -> Unit
) {
    val context = LocalContext.current
    val isBookingSuccess by viewModel.isBookingSuccess.collectAsState()

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = viewModel::createBooking,
        shape = RoundedCornerShape(16.dp), // Rounded corners
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray
        ),
        enabled = viewModel.createBookingButtonStatus
    ) { Text(text = "Create Booking") }

    LaunchedEffect(isBookingSuccess) {
        Log.e("Mangesh", "CreateBookingButton: called")
        isBookingSuccess?.let { success ->
            if (success) {
                onNavigateToListing()
                Log.e("Mangesh", "CreateBookingButton222: called ${viewModel.isBookingSuccess}")

            } else {
                Toast.makeText(context, "Failed to create booking", Toast.LENGTH_LONG).show()
            }
        }
    }
}