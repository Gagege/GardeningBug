package com.nymy.gardeningbug.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nymy.gardeningbug.data.model.Plant
import com.nymy.gardeningbug.data.model.PlantType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import java.time.LocalDate
import java.util.UUID

enum class SortOption {
    NAME,
    TYPE,
    WATERING_NEEDED,
    PLANTING_DATE
}

class GardenViewModel : ViewModel(), GardenViewModelInterface {
    private val _plants = MutableStateFlow(createMockPlants())
    private val _selectedPlant = MutableStateFlow<Plant?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _sortOption = MutableStateFlow(SortOption.NAME)

    override val sortOption: StateFlow<SortOption> = _sortOption.asStateFlow()
    override val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    override val sortedAndFilteredPlants: StateFlow<List<Plant>> = combine(
        _plants,
        _sortOption,
        _searchQuery
    ) { plants, sortOption, query ->
        val filteredPlants = if (query.isBlank()) {
            plants
        } else {
            plants.filter { plant ->
                plant.name.contains(query, ignoreCase = true) ||
                plant.type.name.contains(query, ignoreCase = true) ||
                plant.notes.contains(query, ignoreCase = true)
            }
        }

        when (sortOption) {
            SortOption.NAME -> filteredPlants.sortedBy { it.name }
            SortOption.TYPE -> filteredPlants.sortedBy { it.type }
            SortOption.WATERING_NEEDED -> filteredPlants.sortedBy {
                it.lastWatered ?: LocalDate.MIN
            }
            SortOption.PLANTING_DATE -> filteredPlants.sortedByDescending { it.plantingDate }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    override fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    override fun setSortOption(option: SortOption) {
        _sortOption.value = option
    }

    override fun addPlant(
        name: String,
        type: PlantType,
        wateringFrequency: Int,
        notes: String
    ) {
        val newPlant = Plant(
            id = UUID.randomUUID().toString(),
            name = name,
            type = type,
            plantingDate = LocalDate.now(),
            wateringFrequency = wateringFrequency,
            notes = notes
        )
        _plants.value += newPlant
    }

    override fun updatePlant(plant: Plant) {
        _plants.value = _plants.value.map {
            if (it.id == plant.id) plant else it
        }
    }

    override fun deletePlant(plantId: String) {
        _plants.value = _plants.value.filter { it.id != plantId }
    }

    override fun waterPlant(plantId: String) {
        val plant = _plants.value.find { it.id == plantId } ?: return
        updatePlant(plant.copy(lastWatered = LocalDate.now()))
    }

    override fun getPlantsNeedingWater(): List<Plant> {
        val today = LocalDate.now()
        return _plants.value.filter { plant ->
            plant.lastWatered == null || 
            today.minusDays(plant.wateringFrequency.toLong()).isAfter(plant.lastWatered)
        }
    }

    internal fun createMockPlants(): List<Plant> {
        val today = LocalDate.now()
        return listOf(
            Plant(
                id = "1",
                name = "Tomato Plant",
                type = PlantType.VEGETABLE,
                plantingDate = today.minusDays(30),
                wateringFrequency = 3,
                lastWatered = today.minusDays(2),
                notes = "Growing well, needs support soon"
            ),
            Plant(
                id = "2",
                name = "Basil",
                type = PlantType.HERB,
                plantingDate = today.minusDays(15),
                wateringFrequency = 1,
                lastWatered = today.minusDays(2),
                notes = "Ready for harvesting"
            ),
            Plant(
                id = "3",
                name = "Sunflower",
                type = PlantType.FLOWER,
                plantingDate = today.minusDays(45),
                wateringFrequency = 4,
                lastWatered = today.minusDays(3),
                notes = "Starting to bloom"
            ),
            Plant(
                id = "4",
                name = "Apple Tree",
                type = PlantType.TREE,
                plantingDate = today.minusDays(365),
                wateringFrequency = 7,
                lastWatered = today.minusDays(5),
                notes = "Young tree, needs regular pruning"
            ),
            Plant(
                id = "5",
                name = "Strawberry Plant",
                type = PlantType.FRUIT,
                plantingDate = today.minusDays(60),
                wateringFrequency = 2,
                lastWatered = today.minusDays(1),
                notes = "First berries appearing"
            ),
            Plant(
                id = "6",
                name = "Mint",
                type = PlantType.HERB,
                plantingDate = today.minusDays(20),
                wateringFrequency = 2,
                lastWatered = today.minusDays(1),
                notes = "Growing in container"
            ),
            Plant(
                id = "7",
                name = "Carrots",
                type = PlantType.VEGETABLE,
                plantingDate = today.minusDays(25),
                wateringFrequency = 3,
                lastWatered = today.minusDays(2),
                notes = "Thin seedlings soon"
            ),
            Plant(
                id = "8",
                name = "Rose Bush",
                type = PlantType.FLOWER,
                plantingDate = today.minusDays(90),
                wateringFrequency = 4,
                lastWatered = today.minusDays(3),
                notes = "First buds forming"
            ),
            Plant(
                id = "9",
                name = "Blueberry Bush",
                type = PlantType.FRUIT,
                plantingDate = today.minusDays(180),
                wateringFrequency = 5,
                lastWatered = today.minusDays(4),
                notes = "Acidic soil maintained"
            ),
            Plant(
                id = "10",
                name = "Maple Tree",
                type = PlantType.TREE,
                plantingDate = today.minusDays(730),
                wateringFrequency = 10,
                lastWatered = today.minusDays(8),
                notes = "Established tree, minimal care needed"
            )
        )
    }
}

interface GardenViewModelInterface {
    val sortedAndFilteredPlants: StateFlow<List<Plant>>
    val sortOption: StateFlow<SortOption>
    val searchQuery: StateFlow<String>
    fun setSearchQuery(query: String)
    fun setSortOption(option: SortOption)
    fun addPlant(name: String, type: PlantType, wateringFrequency: Int, notes: String)
    fun updatePlant(plant: Plant)
    fun deletePlant(plantId: String)
    fun waterPlant(plantId: String)
    fun getPlantsNeedingWater(): List<Plant>
}

class PreviewGardenViewModel : GardenViewModelInterface {
    private val mockPlants = GardenViewModel().createMockPlants()

    override val sortedAndFilteredPlants: StateFlow<List<Plant>> = MutableStateFlow(mockPlants)
    override val sortOption: StateFlow<SortOption> = MutableStateFlow(SortOption.NAME)
    override val searchQuery: StateFlow<String> = MutableStateFlow("")

    override fun getPlantsNeedingWater(): List<Plant> = mockPlants.filter {
        it.lastWatered == null ||
        LocalDate.now().minusDays(it.wateringFrequency.toLong()).isAfter(it.lastWatered)
    }

    override fun setSearchQuery(query: String) {}
    override fun setSortOption(option: SortOption) {}
    override fun addPlant(name: String, type: PlantType, wateringFrequency: Int, notes: String) {}
    override fun updatePlant(plant: Plant) {}
    override fun deletePlant(plantId: String) {}
    override fun waterPlant(plantId: String) {}
}