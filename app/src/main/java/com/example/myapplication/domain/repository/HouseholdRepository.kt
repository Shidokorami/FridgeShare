package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Household
import kotlinx.coroutines.flow.Flow

interface HouseholdRepository {

    //get all Households
    fun getHouseholds(): Flow<List<Household>>

    //get household by id
    suspend fun getHouseholdById(id: Long): Household?

    //insert/create household
    suspend fun insertHousehold(household: Household)
}