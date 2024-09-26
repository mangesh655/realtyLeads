package com.example.reltyleads.create.ui.create

import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.TextOverflow
import com.example.reltyleads.Listing.ui.displayCutoutWindowInsets
import com.example.reltyleads.common.ui.BackIcon

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