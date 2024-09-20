package com.example.reltyleads.common.ui

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BackIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Back Icon",
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}