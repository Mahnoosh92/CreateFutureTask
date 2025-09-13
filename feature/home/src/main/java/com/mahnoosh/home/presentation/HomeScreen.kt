package com.mahnoosh.home.presentation

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun HomeScreen(navigateToDetail: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick = navigateToDetail) {
        Text(text = "Go to detail")
    }
}