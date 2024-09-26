package com.example.reltyleads.create.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reltyleads.Listing.ui.displayCutoutWindowInsets
import com.example.reltyleads.create.ui.create.viewmodel.CreateViewModel

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
        bottomBar = {
            CreateBottomBar(modifier, viewModel, onNavigateToListing = onBackClick)
        },
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

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        UnitDetailsForm(viewModel)
        ApplicantDetailsForm(viewModel)
    }
}