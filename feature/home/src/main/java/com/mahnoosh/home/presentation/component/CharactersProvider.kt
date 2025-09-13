package com.mahnoosh.home.presentation.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.mahnoosh.common.toRomanNumeralList

internal class CharactersProvider :
    PreviewParameterProvider<List<com.mahnoosh.home.domain.model.Character>> {
    override val values = sequenceOf(
        listOf(
            com.mahnoosh.home.domain.model.Character(
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
            com.mahnoosh.home.domain.model.Character(
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
    )
}