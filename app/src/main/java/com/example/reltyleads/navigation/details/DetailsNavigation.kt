package com.example.reltyleads.navigation.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.reltyleads.details.ui.DetailsRoute

fun NavController.navigateToDetails(bookingId: String) {
    navigate("booking?bookingId=$bookingId")
}

fun NavGraphBuilder.detailsGraph(
    navigateBack: () -> Unit,
) {
    composable(
        route = DetailsDestination.route,
        arguments = listOf(
            navArgument("bookingId") { type = NavType.LongType },
        )
    ) {
        DetailsRoute(
            onBackClick = navigateBack
        )
    }
}