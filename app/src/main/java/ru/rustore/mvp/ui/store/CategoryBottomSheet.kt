package ru.rustore.mvp.ui.store

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.rustore.mvp.R
import ru.rustore.mvp.data.model.AppCategory
import ru.rustore.mvp.data.model.CategorySummary
import ru.rustore.mvp.ui.common.CategoryDot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    summaries: List<CategorySummary>,
    selectedCategory: AppCategory?,
    onSelectCategory: (AppCategory?) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            Text(
                text = stringResource(R.string.categories_sheet_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                item {
                    CategoryRow(
                        title = stringResource(R.string.store_all_apps),
                        countLabel = null,
                        category = null,
                        isSelected = selectedCategory == null,
                        onClick = { onSelectCategory(null) },
                    )
                    HorizontalDivider()
                }
                items(summaries, key = { it.category.name }) { summary ->
                    CategoryRow(
                        title = summary.category.displayName,
                        countLabel = stringResource(R.string.category_count, summary.appCount),
                        category = summary.category,
                        isSelected = selectedCategory == summary.category,
                        onClick = { onSelectCategory(summary.category) },
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(
    title: String,
    countLabel: String?,
    category: AppCategory?,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (category != null) {
                CategoryDot(category = category)
                Spacer(modifier = Modifier.width(12.dp))
            } else {
                Spacer(modifier = Modifier.width(22.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface,
            )
        }
        if (countLabel != null) {
            Text(
                text = countLabel,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
