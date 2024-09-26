package com.example.reltyleads.navigation.create

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.reltyleads.create.ui.create.CreateRoute

fun NavController.navigateToCreate() {
    navigate("create")
}

fun NavGraphBuilder.createGraph(
    navigateBack: () -> Unit,
) {
    composable(
        route = CreateDestination.route,
    ) {
        CreateRoute(
            onBackClick = navigateBack
        )
    }
}