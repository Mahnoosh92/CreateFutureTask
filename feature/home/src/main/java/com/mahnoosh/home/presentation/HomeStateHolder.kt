package com.mahnoosh.home.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
internal class HomeStateHolder(
    val snackBarHostState: SnackbarHostState,
    private val scope: CoroutineScope,
    isSearchVisible: Boolean = false,
    searchQuery: String = ""
) {
    var isSearchVisible by mutableStateOf(isSearchVisible)
        private set

    var searchQuery by mutableStateOf(searchQuery)
        private set

    fun clearSearchQuery() {
        searchQuery = ""
    }

    fun updateSearchQuery(name: String) {
        searchQuery = name
    }

    fun toggleSearchVisibility() {
        isSearchVisible = !isSearchVisible
    }

    fun showSnackBar(
        message: String,
        actionLabel: String,
        resultCallback: (SnackbarResult) -> Unit
    ) {
        scope.launch {
            val result =
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short
                )
            resultCallback(result)
        }
    }

    companion object {
        fun homeStateSaver(
            snackBarHostState: SnackbarHostState,
            scope: CoroutineScope
        ) = Saver<HomeStateHolder, Any>(
            save = { value ->
                listOf(value.isSearchVisible, value.searchQuery)
            },
            restore = { value ->
                val list = value as? List<*>
                if (list?.size == 3) {
                    val isSearchVisible = list[0] as? Boolean ?: false
                    val searchQuery = list[1] as? String ?: ""
                    HomeStateHolder(
                        snackBarHostState = snackBarHostState,
                        scope = scope,
                        isSearchVisible = isSearchVisible,
                        searchQuery = searchQuery
                    )
                } else {
                    null // Return null if the saved data is invalid
                }
            }
        )
    }
}

@Composable
internal fun rememberHomeScreenState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
) = rememberSaveable(
    saver = HomeStateHolder.homeStateSaver(snackBarHostState, scope)
) {
    HomeStateHolder(scope = scope, snackBarHostState = snackBarHostState)
}