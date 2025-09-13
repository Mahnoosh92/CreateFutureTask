package com.mahnoosh.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mahnoosh.home.presentation.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(navigateToDetail: () -> Unit) {
    composable<HomeRoute> { backStackEntry ->
        HomeScreen(navigateToDetail = navigateToDetail)
    }
}