package com.mahnoosh.home.presentation

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

    private fun setHomeUiStateToSuccess(
        characters: List<Character>?
    ) {
        _homeUiState.update {
            HomeUiState.Success(characters = characters ?: emptyList())
        }
    }

    private fun setHomeUiStateToError(message: String) {
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
        }
    }
}