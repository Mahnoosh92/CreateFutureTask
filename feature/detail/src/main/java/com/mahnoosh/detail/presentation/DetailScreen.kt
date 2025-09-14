package com.mahnoosh.detail.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mahnoosh.detail.presentation.navigation.DetailUiModel

@Composable
internal fun DetailScreen(
    uiModel: DetailUiModel,
    modifier: Modifier = Modifier
) {
    Text(text = "${uiModel?.name}")
}