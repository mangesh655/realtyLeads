package com.example.reltyleads.Listing.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.reltyleads.common.ktx.isLoading
import com.example.reltyleads.common.ktx.isPagingLoading
import com.example.reltyleads.Listing.viewmodel.ListingViewModel
import com.example.reltyleads.R
import com.example.reltyleads.common.ui.formatNumberWithComma
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.repository.model.Booking
import com.example.reltyleads.theme.cardButtonColor
import com.example.reltyleads.theme.listingCardSeparatorColor
import com.example.reltyleads.theme.listingSecondRowTextColor
import com.example.reltyleads.theme.sectionTitleColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListingRoute(
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListingViewModel = hiltViewModel()
) {
    ListingScreenContent(
        viewModel = viewModel,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToCreate = onNavigateToCreate,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListingScreenContent(
    viewModel: ListingViewModel,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreate: () -> Unit,
    modifier: Modifier
) {
    val lazyListState = rememberLazyListState()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            ListingToolbar(
                viewModel = viewModel,
                topAppBarScrollBehavior = topAppBarScrollBehavior
            )
        },
        floatingActionButton = {
            CreateListingButton(
                modifier = Modifier.padding(bottom = 16.dp),
                onClick = onNavigateToCreate
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->

        when {
            pagingItems.isLoading -> {
                PageLoading(
                    modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                    paddingValues = innerPadding
                )
            }

            else -> {
                PageContent(
                    lazyListState = lazyListState,
                    pagingItems = pagingItems,
                    onItemClick = onNavigateToDetails,
                    contentPadding = innerPadding,
                    modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
                )
            }
        }
    }

}

@Composable
fun CreateListingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(32.dp),
        containerColor = Color.Black,
        icon = { Icon(ImageVector.vectorResource(id = R.drawable.iv_document), "Add Booking", tint = Color.White) },
        text = { Text(text = "Add Booking", color = Color.White) },
    )
}

@Composable
fun PageLoading(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    PageLoadingColumn(
        modifier = modifier,
        paddingValues = paddingValues
    )
}

@Composable
private fun PageLoadingColumn(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier.padding(top = 4.dp),
        contentPadding = paddingValues,
        userScrollEnabled = false
    ) {
        items(10) {
            BookingRow(
                booking = BookingDb.Empty,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun PageContent(
    lazyListState: LazyListState,
    pagingItems: LazyPagingItems<BookingDb>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {

    PageContentColumn(
        lazyListState = lazyListState,
        pagingItems = pagingItems,
        onItemClick = onItemClick,
        contentPadding = contentPadding,
        modifier = modifier
    )
}

@Composable
private fun PageContentColumn(
    lazyListState: LazyListState,
    pagingItems: LazyPagingItems<BookingDb>,
    onItemClick: (String) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = 4.dp),
        state = lazyListState,
        contentPadding = contentPadding
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(),
            contentType = pagingItems.itemContentType()
        ) { index ->
            val bookingDb = pagingItems[index]
            if (bookingDb != null) {
                BookingRow(
                    booking = bookingDb,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(color = Color.Red)
                        .clickable { onItemClick(bookingDb.bookingId) }
                )
            }
        }

        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BookingRow(
    booking: BookingDb,
    modifier: Modifier = Modifier,
    maxLines: Int = 10
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(224.dp)
            .background(Color.White),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, color = Color.LightGray),
        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = booking.mainApplicantName,
                fontSize = 32.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.floor_plan),
                    contentDescription = "Unit Details",
                    tint = sectionTitleColor,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = booking.projectName,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = listingSecondRowTextColor)
                )
                Text(
                    text = booking.towerName,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = listingSecondRowTextColor)
                )
                Text(
                    text = "${booking.unitSize} sqft",
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = listingSecondRowTextColor)
                )
                Text(
                    text = booking.unitNumber,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = listingSecondRowTextColor)
                )
            }
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = booking.projectName,
                    fontSize = 24.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = Color.Black)
                )
                Text(
                    text = "\u20B9 ${formatNumberWithComma(booking.agreementValue.toString())}/-",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(color = listingSecondRowTextColor)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = listingCardSeparatorColor)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = SimpleDateFormat("dd MMM, YYYY", Locale.getDefault()).format(
                            Date(
                                booking.createdAt
                            )
                        ),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(color = listingSecondRowTextColor)
                    )
                    Text(
                        text = "last update",
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(color = Color.LightGray)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier
                            .size(48.dp) // Set the size of the button
                            .padding(14.dp)
                            .clip(CircleShape)
                            .border(1.dp, color = Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Phone,
                            contentDescription = "Call",
                            tint = cardButtonColor
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier
                            .size(48.dp) // Set the size of the button
                            .clip(CircleShape)
                            .border(1.dp, color = Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Whatsapp,
                            contentDescription = "Whatsapp",
                            tint = cardButtonColor
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun BookingColumn(
    booking: Booking,
    modifier: Modifier = Modifier
) {
}

@Composable
fun PagingLoadingBox(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingToolbar(
    viewModel: ListingViewModel,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            SearchBar(
                query = searchText,
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search Icon")
                },
                placeholder = {
                    Text(text = "Search booking...", color = Color.LightGray)
                },
                shape = RoundedCornerShape(32.dp),
                onQueryChange = {
                    viewModel.onSearchTextChanged(it)
                },
                onSearch = viewModel::onSearchTextChanged,
                active = isSearching,
                onActiveChange = { viewModel.onToggleSearch() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {

            }
        },
        modifier = modifier, // Make sure this modifier doesn't hide the IconButton
        actions = {
            IconButton( // Ensure this modifier doesn't hide the IconButton
                onClick = { expanded = true },
                modifier = Modifier.then( // Try adding some padding for better visibility
                    Modifier.padding(horizontal = 8.dp)
                )
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}