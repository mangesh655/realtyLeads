package com.example.reltyleads

import androidx.activity.SystemBarStyle
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.reltyleads.navigation.details.detailsGraph
import com.example.reltyleads.navigation.details.navigateToDetails
import com.example.reltyleads.navigation.Listing.ListingDestination
import com.example.reltyleads.navigation.Listing.listingGraph
import com.example.reltyleads.navigation.create.createGraph
import com.example.reltyleads.navigation.create.navigateToCreate
import com.example.reltyleads.theme.RealtyTheme

@Composable
internal fun MainActivityContent(
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit
) {
    val navHostController = rememberNavController()

    RealtyTheme(
        enableEdgeToEdge = enableEdgeToEdge
    ) {
        NavHost(
            navController = navHostController,
            startDestination = ListingDestination.route
        ) {

            listingGraph(
                navigateToDetails = navHostController::navigateToDetails,
                navigateToCreate = navHostController::navigateToCreate
            )

            detailsGraph(
                navigateBack = navHostController::popBackStack,
            )

            createGraph(
                navigateBack = navHostController::popBackStack,
            )
        }
    }
}