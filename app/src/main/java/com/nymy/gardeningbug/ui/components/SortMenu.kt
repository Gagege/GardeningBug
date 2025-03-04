package com.nymy.gardeningbug.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nymy.gardeningbug.ui.viewmodel.SortOption

@Composable
fun SortMenu(
    currentSort: SortOption,
    onSortSelected: (SortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded = true },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                Icons.Default.Tune,
                contentDescription = "Sort plants",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SortOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            when (option) {
                                SortOption.NAME -> "Sort by Name"
                                SortOption.TYPE -> "Sort by Type"
                                SortOption.WATERING_NEEDED -> "Sort by Watering Need"
                                SortOption.PLANTING_DATE -> "Sort by Planting Date"
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        onSortSelected(option)
                        expanded = false
                    },
                    leadingIcon = {
                        RadioButton(
                            selected = currentSort == option,
                            onClick = null
                        )
                    }
                )
            }
        }
    }
} 