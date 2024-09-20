package com.example.reltyleads.navigation.Listing

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.reltyleads.Listing.ui.ListingRoute

fun NavGraphBuilder.listingGraph(
    navigateToDetails: (String) -> Unit,
    navigateToCreate: () -> Unit
) {
    composable(
        route = ListingDestination.route
    ) {
        ListingRoute(
            onNavigateToDetails = navigateToDetails,
            onNavigateToCreate = navigateToCreate
        )
    }
}