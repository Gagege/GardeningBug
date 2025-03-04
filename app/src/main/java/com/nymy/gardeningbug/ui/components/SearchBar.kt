package com.nymy.gardeningbug.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = { },
        active = false,
        onActiveChange = { },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") }
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        placeholder = {
            Text(
                "Search plants...",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Search suggestions would go here if needed
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview_Empty() {
    MaterialTheme {
        SearchBar(
            query = "",
            onQueryChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview_WithText() {
    MaterialTheme {
        SearchBar(
            query = "Tomato",
            onQueryChange = {}
        )
    }
} 