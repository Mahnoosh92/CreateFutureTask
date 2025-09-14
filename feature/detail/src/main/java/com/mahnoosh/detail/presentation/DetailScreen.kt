package com.mahnoosh.detail.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mahnoosh.detail.presentation.component.items.DetailItem
import com.mahnoosh.detail.presentation.component.items.HeaderItem
import com.mahnoosh.detail.presentation.component.items.InformationItem
import com.mahnoosh.detail.presentation.navigation.DetailUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreen(
    uiModel: DetailUiModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                HeaderItem(name = uiModel.name, culture = uiModel.culture)
            }
            item {
                DetailItem(
                    gender = uiModel.gender,
                    born = uiModel.born,
                    died = uiModel.died
                )
            }
            item {
                InformationItem(
                    tvSeries = uiModel.tvSeries,
                    titles = uiModel.titles
                )
            }
        }
    }
}