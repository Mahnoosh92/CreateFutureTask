@file:OptIn(ExperimentalMaterial3Api::class)

package com.mahnoosh.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.mahnoosh.common.constants.UiTags
import com.mahnoosh.designsystem.ui.ThemePreviews
import com.mahnoosh.designsystem.ui.theme.TaskTheme
import com.mahnoosh.home.R
import com.mahnoosh.home.domain.model.Character


@Composable
internal fun CharactersList(
    isRefreshing: Boolean,
    characters: List<Character>,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pullRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullRefreshState
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                key = { item -> item.name },
                items = characters
            ) { character ->
                CharacterItem(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    character = character
                )
            }
        }
    }
}

@Composable
private fun CharacterItem(
    modifier: Modifier = Modifier,
    character: Character
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .height(IntrinsicSize.Min)
            .testTag(UiTags.HomeScreen.CHARACTER_ITEM),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column {
            ItemRow(
                key = stringResource(R.string.name),
                value = character.name
            )
            if (character.culture.isNotEmpty()) {
                ItemRow(
                    key = stringResource(R.string.culture),
                    value = character.culture
                )
            }
            if (character.died.isNotEmpty())
                ItemRow(
                    key = stringResource(R.string.died),
                    value = character.died
                )
            ItemRow(
                key = stringResource(R.string.seasons),
                value = character.tvSeries.joinToString(",")
            )
        }
    }
}

@Composable
private fun ItemRow(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = key
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = value,
            textAlign = TextAlign.End
        )
    }
}

@ThemePreviews
@Composable
internal fun CharactersListPreview(
    @PreviewParameter(CharactersProvider::class) characters: List<Character>
) {
    TaskTheme {
        CharactersList(
            isRefreshing = false,
            characters = characters,
            onRefresh = { /* Handle refresh */ }
        )
    }
}

@ThemePreviews
@Composable
internal fun CharactersListRefreshingPreview(
    @PreviewParameter(CharactersProvider::class) characters: List<Character>
) {
    TaskTheme {
        CharactersList(
            isRefreshing = true,
            characters = characters,
            onRefresh = { /* Handle refresh */ }
        )
    }
}