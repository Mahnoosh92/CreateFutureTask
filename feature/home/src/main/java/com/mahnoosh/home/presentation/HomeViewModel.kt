package com.mahnoosh.home.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.common.stringresolver.StringResolver
import com.mahnoosh.home.R
import com.mahnoosh.home.domain.model.Character
import com.mahnoosh.home.domain.usecase.FetchCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val fetchCharacterUseCase: FetchCharacterUseCase,
    private val stringResolver: StringResolver
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState = _homeUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val filteredCharacters = combine(homeUiState, searchQuery) { uiState, query ->
        if (uiState is HomeUiState.Success) {
            filterCharacters(uiState.characters, query)
        } else {
            emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private fun filterCharacters(characters: List<Character>, name: String) =
        characters.filter { character ->
            character.name.lowercase().contains(name.lowercase())
        }

    private fun getCharacters(
        isRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {
            if (_homeUiState.value !is HomeUiState.Success || isRefreshing) { // to avoid calling api again when navigating back to HomeScreen
                if (isRefreshing) {
                    _homeUiState.value = HomeUiState.Refreshing
                } else {
                    _homeUiState.value = HomeUiState.Loading
                }

                val result = fetchCharacterUseCase()
                when {
                    result.isSuccess -> {
                        setHomeUiStateToSuccess(characters = result.getOrNull())
                    }

                    result.isFailure -> {
                        setHomeUiStateToError(
                            result.exceptionOrNull()?.message ?: stringResolver.findString(
                                R.string.error_general
                            )
                        )
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun setHomeUiStateToSuccess(
        characters: List<Character>?
    ) {
        _homeUiState.update {
            HomeUiState.Success(characters = characters ?: emptyList())
        }
    }

    @VisibleForTesting
    fun setHomeUiStateToError(message: String) {
        _homeUiState.update {
            HomeUiState.Error(errorMessage = message)
        }
    }

    private fun consumeErrorMessage() {
        _homeUiState.update {
            HomeUiState.Error(null)
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ConsumeErrorMessage -> {
                consumeErrorMessage()
            }

            is HomeEvent.GetCharacters -> {
                getCharacters(isRefreshing = event.isRefreshing)
            }

            is HomeEvent.Search -> {
                _searchQuery.value = event.name
            }
        }
    }
}