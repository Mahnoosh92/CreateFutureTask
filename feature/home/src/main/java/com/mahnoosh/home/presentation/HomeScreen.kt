package com.mahnoosh.home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mahnoosh.common.constants.UiTags
import com.mahnoosh.home.R
import com.mahnoosh.home.presentation.component.CharactersList
import com.mahnoosh.home.presentation.component.LoadingItem
import com.mahnoosh.home.presentation.component.ShimmerLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetCharacters())
    }

    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredCharacters by viewModel.filteredCharacters.collectAsStateWithLifecycle()
    val screenState = rememberHomeScreenState()

    Scaffold(
        modifier = modifier.testTag(UiTags.HomeScreen.ROOT),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    AnimatedContent(screenState.isSearchVisible) { isSearchIconVisible ->
                        if (isSearchIconVisible) {
                            TextField(
                                value = searchQuery,
                                onValueChange = { newQuery ->
                                    viewModel.onEvent(HomeEvent.Search(newQuery))
                                    screenState.updateSearchQuery(newQuery)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.search_hint),
                                        color = Color.Black.copy(alpha = 0.5f)
                                    )
                                }
                            )
                        } else {
                            Text(text = stringResource(R.string.title))
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (screenState.isSearchVisible) {
                            if (screenState.searchQuery.isEmpty()) {
                                screenState.toggleSearchVisibility()
                            } else {
                                screenState.clearSearchQuery()
                                viewModel.onEvent(HomeEvent.Search(""))
                            }
                        } else {
                            screenState.toggleSearchVisibility()
                        }
                    }) {
                        AnimatedContent(screenState.isSearchVisible) { isSearchIconVisible ->
                            if (isSearchIconVisible) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.close)
                                )
                            } else {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = stringResource(R.string.search)
                                )
                            }
                        }
                    }
                }
            )
        },
        content = { innerPaddingModifier ->
            Crossfade(
                modifier = Modifier.padding(innerPaddingModifier),
                targetState = homeUiState,
                animationSpec = tween(500),
                label = "cross fade"
            ) { targetState ->
                when (targetState) {
                    HomeUiState.Idle -> Unit

                    is HomeUiState.Error -> {
                        targetState.errorMessage?.let { errorMessage ->
                            screenState.showSnackBar(
                                message = errorMessage,
                                actionLabel = stringResource(com.mahnoosh.common.R.string.retry),
                                resultCallback = { result ->
                                    when (result) {
                                        SnackbarResult.ActionPerformed ->
                                            viewModel.onEvent(
                                                HomeEvent.GetCharacters(
                                                    isRefreshing = true
                                                )
                                            )

                                        SnackbarResult.Dismissed -> {
                                            viewModel.onEvent(HomeEvent.ConsumeErrorMessage)
                                        }
                                    }
                                }
                            )
                        }
                    }

                    HomeUiState.Loading -> ShimmerLazyColumn()

                    is HomeUiState.Success -> {
                        CharactersList(
                            isRefreshing = homeUiState is HomeUiState.Refreshing,
                            characters = filteredCharacters,
                            onRefresh = {
                                viewModel.onEvent(
                                    HomeEvent.GetCharacters(
                                        isRefreshing = true
                                    )
                                )
                            },
                        )
                    }

                    HomeUiState.Refreshing -> {
                        LoadingItem()
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.testTag(UiTags.Common.SNACK_BAR),
                hostState = screenState.snackBarHostState
            )
        }
    )
}

