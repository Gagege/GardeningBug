package com.nymy.gardeningbug.data.model

import java.time.LocalDate

data class Plant(
    val id: String,
    val name: String,
    val type: PlantType,
    val plantingDate: LocalDate,
    val wateringFrequency: Int, // in days
    val lastWatered: LocalDate? = null,
    val notes: String = ""
)

enum class PlantType {
    VEGETABLE,
    HERB,
    FLOWER,
    FRUIT,
    TREE
} 