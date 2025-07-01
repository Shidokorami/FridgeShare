package com.example.myapplication.domain.useCases.household_use_cases

import android.util.Log
import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.repository.HouseholdRepository

class CreateHousehold(
    private val authRepository: AuthRepository,
    private val householdRepository: HouseholdRepository
) {
    suspend operator fun invoke(householdName: String){
        val userId = authRepository.currentUserId.value ?: ""

        if (userId.isNullOrEmpty()) {
            Log.e("CreateHousehold", "User ID is null or empty. Cannot create household.")
            return
        }

        val household = Household(
            id = "",
            name = householdName,
            creatorId = userId,
            memberIds = listOf(userId),
            createdAt = null
        )

        householdRepository.addHousehold(household)
    }
}