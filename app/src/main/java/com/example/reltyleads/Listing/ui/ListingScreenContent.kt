package com.example.reltyleads.Listing.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.reltyleads.common.ktx.isLoading
import com.example.reltyleads.common.ktx.isPagingLoading
import com.example.reltyleads.Listing.viewmodel.ListingViewModel
import com.example.reltyleads.R
import com.example.reltyleads.repository.model.Booking

@Composable
fun ListingRoute(
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListingViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val searchText by viewModel.searchText.collectAsState()

    ListingScreenContent(
        pagingItems = pagingItems,
        searchText = searchText,
        onNavigateToDetails = onNavigateToDetails,
        onNavigateToCreate = onNavigateToCreate,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListingScreenContent(
    pagingItems: LazyPagingItems<Booking>,
    searchText: String,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreate: () -> Unit,
    modifier: Modifier
) {
    val lazyListState = rememberLazyListState()

    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            ListingToolbar(
                searchText = searchText,
                topAppBarScrollBehavior = topAppBarScrollBehavior
            ) {

            }
        },
        floatingActionButton = {
            CreateListingButton { onNavigateToCreate() }
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
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(ImageVector.vectorResource(id = R.drawable.iv_document), "Add Booking") },
        text = { Text(text = "Add Booking") },
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
        /*items(Leads) {
            BookingRow(
                movie = MovieDb.Empty,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        shape = MaterialTheme.shapes.small,
                        highlight = PlaceholderHighlight.fade()
                    )
            )
        }*/
    }
}

@Composable
fun PageContent(
    lazyListState: LazyListState,
    pagingItems: LazyPagingItems<Booking>,
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
    pagingItems: LazyPagingItems<Booking>,
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
            val leadsDb = pagingItems[index]
            if (leadsDb != null) {
                /*BookingRow(
                    movie = leadsDb,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .clickable { onMovieClick(movieDb.movieList, movieDb.movieId) }
                )*/
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
    booking: Booking,
    modifier: Modifier = Modifier,
    maxLines: Int = 10
) {

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
    searchText: String,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onSearchTextChanged: (String) -> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            TextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
            )
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
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}