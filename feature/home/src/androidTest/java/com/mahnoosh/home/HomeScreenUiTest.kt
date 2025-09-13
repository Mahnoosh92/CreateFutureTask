package com.mahnoosh.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahnoosh.common.constants.UiTags
import com.mahnoosh.home.data.di.HomeModule
import com.mahnoosh.home.domain.usecase.FakeCharacterUseCase
import com.mahnoosh.home.presentation.HomeScreen
import com.mahnoosh.home.presentation.HomeViewModel
import com.mahnoosh.ui.FakeStringResolver
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(HomeModule::class)
internal class HomeScreenUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var fakeStringResolver: FakeStringResolver

    @Inject
    lateinit var fakeCharacterUseCase: FakeCharacterUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        homeViewModel = HomeViewModel(
            fetchCharacterUseCase = fakeCharacterUseCase,
            stringResolver = fakeStringResolver
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun screen_displays_success_state() = runTest {
        composeTestRule.setContent {
            HomeScreen(
                viewModel = homeViewModel,
                navigateToDetail = {},
            )
        }

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.HomeScreen.CHARACTER_ITEM)
        ).onFirst().assertIsDisplayed()
    }

    @Test
    fun screen_displays_error_state() = runTest {
        fakeCharacterUseCase.shouldReturnError = true

        composeTestRule.setContent {
            HomeScreen(
                viewModel = homeViewModel,
                navigateToDetail = {},
            )
        }

        composeTestRule.onNode(
            hasText(FakeCharacterUseCase.ERROR_MESSAGE) and hasAnyAncestor(hasTestTag(UiTags.Common.SNACK_BAR))
        ).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun screen_displays_error_state_and_retries_successfully() = runTest {
        fakeCharacterUseCase.shouldReturnError = true

        composeTestRule.setContent {
            HomeScreen(
                viewModel = homeViewModel,
                navigateToDetail = {},
            )
        }

        composeTestRule.onNode(
            hasText(FakeCharacterUseCase.ERROR_MESSAGE) and hasAnyAncestor(hasTestTag(UiTags.Common.SNACK_BAR))
        ).assertIsDisplayed()

        // Trigger the retry action by clicking the "Retry" button
        fakeCharacterUseCase.shouldReturnError = false
        composeTestRule.onNode(hasText("Retry")).performClick()

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.HomeScreen.CHARACTER_ITEM)
        ).onFirst().assertIsDisplayed()
    }
}