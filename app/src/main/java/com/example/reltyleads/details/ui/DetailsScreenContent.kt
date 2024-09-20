package com.example.reltyleads.details.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reltyleads.common.ui.BackIcon
import com.example.reltyleads.details.viewmodel.DetailsViewModel
import com.example.reltyleads.Listing.ui.displayCutoutWindowInsets
import com.example.reltyleads.Listing.ui.screenHeight
import com.example.reltyleads.Listing.ui.screenWidth
import com.example.reltyleads.persistence.database.entity.BookingDb


@JvmOverloads
@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    DetailsScreenContent(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun DetailsScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LazyRow(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    ) {
        item {
            Scaffold(
                modifier = Modifier
                    .width(screenWidth)
                    .height(screenHeight)
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                topBar = {
                    DetailsToolbar(
                        onNavigationIconClick = onBackClick,
                        topAppBarScrollBehavior = topAppBarScrollBehavior,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                containerColor = Color.White
            ) { innerPadding ->
                DetailsContent(
                    modifier = Modifier
                        .padding(innerPadding)
                        .windowInsetsPadding(displayCutoutWindowInsets)
                        .fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun DetailsContent(
    booking: BookingDb? = null,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsToolbar(
    onNavigationIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    scrolledContainerColor: Color = MaterialTheme.colorScheme.inversePrimary,
) {
    TopAppBar(
        title = {
            Text(
                text = "Booking Details",
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
            scrolledContainerColor = scrolledContainerColor
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}