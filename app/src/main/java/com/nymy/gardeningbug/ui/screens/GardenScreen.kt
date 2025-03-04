package com.nymy.gardeningbug.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nymy.gardeningbug.data.model.Plant
import com.nymy.gardeningbug.ui.components.AddPlantDialog
import com.nymy.gardeningbug.ui.components.EditPlantDialog
import com.nymy.gardeningbug.ui.components.PlantCard
import com.nymy.gardeningbug.ui.components.SearchBar
import com.nymy.gardeningbug.ui.components.SortMenu
import com.nymy.gardeningbug.ui.theme.GardeningBugTheme
import com.nymy.gardeningbug.ui.viewmodel.GardenViewModelInterface
import com.nymy.gardeningbug.ui.viewmodel.PreviewGardenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardenScreen(
    viewModel: GardenViewModelInterface
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var plantToEdit by remember { mutableStateOf<Plant?>(null) }
    val plants by viewModel.sortedAndFilteredPlants.collectAsState()
    val plantsNeedingWater = viewModel.getPlantsNeedingWater()
    val currentSort by viewModel.sortOption.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Garden") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    SortMenu(
                        currentSort = currentSort,
                        onSortSelected = viewModel::setSortOption
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Plant")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::setSearchQuery
            )

            if (plantsNeedingWater.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Plants Needing Water",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        plantsNeedingWater.forEach { plant ->
                            Text(
                                text = "• ${plant.name}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }

            if (plants.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (searchQuery.isBlank()) {
                            "No plants yet. Add your first plant!"
                        } else {
                            "No plants found matching '$searchQuery'"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(plants) { plant ->
                        PlantCard(
                            plant = plant,
                            onWaterClick = { viewModel.waterPlant(plant.id) },
                            onEditClick = { plantToEdit = plant },
                            onDeleteClick = { viewModel.deletePlant(plant.id) }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddPlantDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, type, wateringFrequency, notes ->
                    viewModel.addPlant(name, type, wateringFrequency, notes)
                    showAddDialog = false
                }
            )
        }

        plantToEdit?.let { plant ->
            EditPlantDialog(
                plant = plant,
                onDismiss = { plantToEdit = null },
                onConfirm = { name, type, wateringFrequency, notes ->
                    viewModel.updatePlant(
                        plant.copy(
                            name = name,
                            type = type,
                            wateringFrequency = wateringFrequency,
                            notes = notes
                        )
                    )
                    plantToEdit = null
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GardenScreenPreview() {
    GardeningBugTheme {
        GardenScreen(PreviewGardenViewModel())
    }
} 