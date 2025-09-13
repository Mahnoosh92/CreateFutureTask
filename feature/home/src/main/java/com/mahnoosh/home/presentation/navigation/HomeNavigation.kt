package com.mahnoosh.home.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mahnoosh.home.presentation.HomeScreen
import com.mahnoosh.home.presentation.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(navigateToDetail: () -> Unit) {
    composable<HomeRoute> { backStackEntry ->
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(viewModel = viewModel, navigateToDetail = navigateToDetail)
    }
}