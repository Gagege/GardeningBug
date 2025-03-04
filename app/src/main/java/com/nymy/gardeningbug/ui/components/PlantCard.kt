package com.nymy.gardeningbug.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nymy.gardeningbug.data.model.Plant
import com.nymy.gardeningbug.data.model.PlantType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PlantCard(
    plant: Plant,
    onWaterClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { showDeleteDialog = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete ${plant.name}"
                    )
                }
            }
            
            Text(
                text = "Type: ${plant.type.name}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                text = "Planted: ${plant.plantingDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                text = "Water every ${plant.wateringFrequency} days",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            plant.lastWatered?.let { lastWatered ->
                Text(
                    text = "Last watered: ${lastWatered.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            if (plant.notes.isNotBlank()) {
                Text(
                    text = plant.notes,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onWaterClick,
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Water Plant")
                }
                
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Edit Plant")
                }
            }
        }
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            plantName = plant.name,
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                onDeleteClick()
                showDeleteDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantCardPreview_NeedsWater() {
    MaterialTheme {
        PlantCard(
            plant = Plant(
                id = "1",
                name = "Tomato Plant",
                type = PlantType.VEGETABLE,
                plantingDate = LocalDate.now().minusDays(30),
                wateringFrequency = 3,
                lastWatered = LocalDate.now().minusDays(4),
                notes = "Growing well, needs support soon"
            ),
            onWaterClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantCardPreview_RecentlyWatered() {
    MaterialTheme {
        PlantCard(
            plant = Plant(
                id = "2",
                name = "Basil",
                type = PlantType.HERB,
                plantingDate = LocalDate.now().minusDays(15),
                wateringFrequency = 2,
                lastWatered = LocalDate.now().minusDays(1),
                notes = "Ready for harvesting"
            ),
            onWaterClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantCardPreview_Tree() {
    MaterialTheme {
        PlantCard(
            plant = Plant(
                id = "3",
                name = "Apple Tree",
                type = PlantType.TREE,
                plantingDate = LocalDate.now().minusDays(365),
                wateringFrequency = 7,
                lastWatered = LocalDate.now().minusDays(5),
                notes = "Young tree, needs regular pruning"
            ),
            onWaterClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
} 