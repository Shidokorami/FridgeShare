package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.entity.HouseholdEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseholdDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(household: HouseholdEntity)

    @Update
    suspend fun update(household: HouseholdEntity)

    @Delete
    suspend fun delete(household: HouseholdEntity)

    @Query(
        """
    SELECT * FROM household
    """
    )
    fun getHouseholds(): Flow<List<HouseholdEntity>>


    @Query(
        """
            SELECT * FROM household
            WHERE id = :householdId
        """
    )
    suspend fun getHouseholdById(householdId: Long): HouseholdEntity?

}