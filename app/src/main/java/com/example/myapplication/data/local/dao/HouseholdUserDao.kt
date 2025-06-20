package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.myapplication.data.local.entity.HouseholdUserEntity

@Dao
interface HouseholdUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(householdUser: HouseholdUserEntity)

    @Update
    suspend fun update(householdUser: HouseholdUserEntity)

    @Delete
    suspend fun delete(householdUser: HouseholdUserEntity)
}