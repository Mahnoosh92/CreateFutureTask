package com.mahnoosh.home.presentation

import androidx.compose.runtime.Immutable
import com.mahnoosh.home.domain.model.Character

internal sealed interface HomeUiState {

    /**
     * Represents the success state, where the list of characters is available.
     */
    @Immutable
    data class Success(val characters: List<Character>) : HomeUiState

    /**
     * Represents the error state, where an error occurred while fetching the data.
     */
    data class Error(val errorMessage: String?) : HomeUiState

    /**
     * Represents the idle state, where there is no data to display.
     */
    data object Idle : HomeUiState

    /**
     * Represents the loading state, where the data is being fetched.
     */
    data object Loading : HomeUiState

    /**
     * Represents the refreshing state, where the data is being refreshed.
     */
    data object Refreshing : HomeUiState
}