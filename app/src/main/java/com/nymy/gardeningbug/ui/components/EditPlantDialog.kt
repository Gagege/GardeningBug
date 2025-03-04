package com.nymy.gardeningbug.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nymy.gardeningbug.data.model.Plant
import com.nymy.gardeningbug.data.model.PlantType

@Composable
fun EditPlantDialog(
    plant: Plant,
    onDismiss: () -> Unit,
    onConfirm: (name: String, type: PlantType, wateringFrequency: Int, notes: String) -> Unit
) {
    var name by remember { mutableStateOf(plant.name) }
    var selectedType by remember { mutableStateOf(plant.type) }
    var wateringFrequency by remember { mutableStateOf(plant.wateringFrequency.toString()) }
    var notes by remember { mutableStateOf(plant.notes) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Plant") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Plant Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Column {
                    Text("Plant Type", style = MaterialTheme.typography.bodyLarge)
                    PlantType.entries.forEach { type ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            RadioButton(
                                selected = selectedType == type,
                                onClick = { selectedType = type }
                            )
                            Text(type.name)
                        }
                    }
                }

                OutlinedTextField(
                    value = wateringFrequency,
                    onValueChange = { wateringFrequency = it },
                    label = { Text("Water every X days") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        name,
                        selectedType,
                        wateringFrequency.toIntOrNull() ?: plant.wateringFrequency,
                        notes
                    )
                    onDismiss()
                },
                enabled = name.isNotBlank() && wateringFrequency.toIntOrNull() != null
            ) {
                Text("Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}