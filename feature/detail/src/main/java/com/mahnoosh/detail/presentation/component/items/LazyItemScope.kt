package com.mahnoosh.detail.presentation.component.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mahnoosh.designsystem.ui.ThemePreviews
import com.mahnoosh.designsystem.ui.theme.TaskTheme
import com.mahnoosh.detail.R
import com.mahnoosh.detail.presentation.component.Chip
import com.mahnoosh.detail.presentation.component.SectionHeader
import com.mahnoosh.detail.presentation.component.SectionWithChips

@Composable
internal fun LazyItemScope.HeaderItem(
    name: String,
    culture: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = stringResource(R.string.culture, culture),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
internal fun LazyItemScope.DetailItem(
    gender: String,
    born: String,
    died: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader(stringResource(R.string.details), Icons.Outlined.Info)
            Spacer(Modifier.height(8.dp))
            DetailItem(
                label = stringResource(R.string.gender),
                value = gender,
                icon = Icons.Filled.Face
            )
            DetailItem(
                label = stringResource(R.string.born),
                value = born,
                icon = Icons.Filled.DateRange
            )
            DetailItem(
                label = stringResource(R.string.died),
                value = died,
                icon = Icons.Filled.Person
            )
        }
    }
}

@Composable
internal fun LazyItemScope.InformationItem(
    tvSeries: List<String>,
    titles: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader(stringResource(R.string.information), Icons.Outlined.Info)
            if (titles.isNotEmpty()) {
                SectionWithChips(
                    title = stringResource(R.string.titles),
                    chips = titles
                )
            }
            if (tvSeries.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.tv_series),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                FlowRow(modifier = Modifier.padding(top = 8.dp)) {
                    tvSeries.forEachIndexed { index, value ->
                        Chip(text = stringResource(R.string.season, value))
                    }
                }
            }
        }
    }
}

@Composable
internal fun DetailItem(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    if (value.isNotBlank()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = " $label:",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@ThemePreviews
@Composable
fun HeaderItemPreview() {
    TaskTheme {
        LazyColumn {
            item {
                HeaderItem(
                    name = "Tyrion Lannister",
                    culture = "Westeros"
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun DetailItemPreview() {
    TaskTheme {
        LazyColumn {
            item {
                DetailItem(
                    gender = "Male",
                    born = "274 AC",
                    died = "Still living"
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun InformationItemPreview() {
    TaskTheme {
        LazyColumn {
            item {
                InformationItem(
                    tvSeries = listOf("1", "2", "3", "4", "5", "6", "7", "8"),
                    titles = listOf("The Imp", "Hand of the King")
                )
            }
        }
    }
}