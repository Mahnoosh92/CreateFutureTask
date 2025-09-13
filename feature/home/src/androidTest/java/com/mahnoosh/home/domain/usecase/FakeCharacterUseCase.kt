package com.mahnoosh.home.domain.usecase

import com.mahnoosh.common.toRomanNumeralList
import com.mahnoosh.home.domain.model.Character
import javax.inject.Inject

internal class FakeCharacterUseCase @Inject constructor() : FetchCharacterUseCase {

    companion object {
        const val ERROR_MESSAGE = "Test Error: Could not retrieve characters."
    }

    private val fakeCharacters = listOf(
        Character(
            name = "Arya Stark",
            culture = "Northmen",
            born = "In 263 AC, at Winterfell",
            died = "In 299 AC, at Great Sept of Baelor in King's Landing",
            aliases = emptyList(),
            titles = emptyList(),
            playedBy = emptyList(),
            gender = "Male",
            father = "",
            mother = "",
            tvSeries = listOf(
                "Season 1",
                "Season 2",
                "Season 3",
                "Season 4",
                "Season 5",
                "Season 6",
                "Season 7",
                "Season 8"
            ).toRomanNumeralList()
        ),
        Character(
            name = "Jon Snow",
            culture = "Northmen",
            died = "",
            born = "In 273 AC, at Casterly Rock",
            aliases = emptyList(),
            titles = emptyList(),
            playedBy = emptyList(),
            gender = "Male",
            father = "",
            mother = "",
            tvSeries = listOf(
                "Season 1",
                "Season 2",
                "Season 3",
                "Season 4",
                "Season 5",
                "Season 6",
                "Season 7",
                "Season 8"
            ).toRomanNumeralList()
        )
    )

    var shouldReturnError = false

    override suspend fun invoke(): Result<List<Character>> {
        return if (shouldReturnError) {
            Result.failure(Exception(ERROR_MESSAGE))
        } else {
            Result.success(fakeCharacters)
        }
    }
}