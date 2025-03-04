package com.nymy.gardeningbug.data.local

import androidx.room.*
import com.nymy.gardeningbug.data.model.Plant
import com.nymy.gardeningbug.data.model.PlantType
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY name ASC")
    fun getAllPlants(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE type = :plantType ORDER BY name ASC")
    fun getPlantsByType(plantType: PlantType): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlantById(id: String): PlantEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: PlantEntity)

    @Update
    suspend fun updatePlant(plant: PlantEntity)

    @Delete
    suspend fun deletePlant(plant: PlantEntity)

    @Query("SELECT * FROM plants WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchPlants(query: String): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants ORDER BY wateringFrequency ASC")
    fun getPlantsByWateringFrequency(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants ORDER BY plantingDate DESC")
    fun getPlantsByPlantingDate(): Flow<List<PlantEntity>>
} 