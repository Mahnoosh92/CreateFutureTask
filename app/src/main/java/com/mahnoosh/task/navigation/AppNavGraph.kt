package com.mahnoosh.task.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mahnoosh.detail.presentation.navigation.DetailUiModel
import com.mahnoosh.detail.presentation.navigation.detailScreen
import com.mahnoosh.detail.presentation.navigation.navigateToDetails
import com.mahnoosh.home.presentation.navigation.HomeRoute
import com.mahnoosh.home.presentation.navigation.homeScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeScreen { character ->
            navController.navigateToDetails(
                uiModel = DetailUiModel(
                    name = character.name,
                    gender = character.gender,
                    culture = character.culture,
                    born = character.born,
                    died = character.died,
                    titles = character.titles,
                    tvSeries = character.tvSeries
                )
            )
        }

        detailScreen {
            navController.navigateUp()
        }
    }
}