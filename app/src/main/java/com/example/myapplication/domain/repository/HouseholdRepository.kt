package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Household
import kotlinx.coroutines.flow.Flow

interface HouseholdRepository {

    //get all Households for chosen user by id
    fun getHouseholdsForUser(userId: String): Flow<List<Household>>

    ////get household by id
    fun getHouseholdById(householdId: String): Flow<Household?>

    //Adds new household, returns it's id
    suspend fun addHousehold(household: Household): String

    suspend fun updateHousehold(household: Household)

    suspend fun addHouseholdMember(householdId: String, userId: String)

    suspend fun removeHouseholdMember(householdId: String, userId: String)

    fun isUserMemberOfHousehold(householdId: String, userId: String): Flow<Boolean>
}


