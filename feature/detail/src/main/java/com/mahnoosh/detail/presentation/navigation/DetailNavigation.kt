package com.mahnoosh.detail.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mahnoosh.detail.presentation.DetailScreen
import kotlinx.serialization.Serializable

@Serializable
data object DetailRoute

fun NavGraphBuilder.detailScreen() {
    composable<DetailRoute> { backStackEntry ->
        DetailScreen()
    }
}

fun NavController.navigateToDetails() = navigate(route = DetailRoute)