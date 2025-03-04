package com.nymy.gardeningbug.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nymy.gardeningbug.data.model.Plant
import com.nymy.gardeningbug.data.model.PlantType
import java.time.LocalDate

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: PlantType,
    val plantingDate: LocalDate,
    val wateringFrequency: Int,
    val lastWatered: LocalDate?,
    val notes: String
) {
    fun toPlant(): Plant {
        return Plant(
            id = id,
            name = name,
            type = type,
            plantingDate = plantingDate,
            wateringFrequency = wateringFrequency,
            lastWatered = lastWatered,
            notes = notes
        )
    }

    companion object {
        fun fromPlant(plant: Plant): PlantEntity {
            return PlantEntity(
                id = plant.id,
                name = plant.name,
                type = plant.type,
                plantingDate = plant.plantingDate,
                wateringFrequency = plant.wateringFrequency,
                lastWatered = plant.lastWatered,
                notes = plant.notes
            )
        }
    }
} 