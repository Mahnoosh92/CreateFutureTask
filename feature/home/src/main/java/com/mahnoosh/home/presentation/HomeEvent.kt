package com.mahnoosh.home.presentation

/**
 * Sealed interface representing events related to home screen
 */
internal sealed interface HomeEvent {
    /**
     * Represents an event to fetch the list of characters
     */
    data class GetCharacters(val isRefreshing: Boolean = false) : HomeEvent

    /**
     * Represents an event to consume and clear any existing error messages
     */
    data object ConsumeErrorMessage : HomeEvent

    /**
     * Represents an event to search for character based on a given name
     */
    data class Search(val name: String) : HomeEvent
}