package com.example.myapplication.domain.useCases.household_use_cases

import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.repository.HouseholdRepository

class GetHousehold(
    private val repository: HouseholdRepository
) {
    suspend operator fun invoke(householdId: Long): Household?{
        return repository.getHouseholdById(householdId)
    }
}