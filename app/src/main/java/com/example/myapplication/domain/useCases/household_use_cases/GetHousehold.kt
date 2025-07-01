package com.example.myapplication.domain.useCases.household_use_cases

import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.repository.HouseholdRepository
import kotlinx.coroutines.flow.Flow

class GetHousehold(
    private val repository: HouseholdRepository
) {
    operator fun invoke(householdId: String): Flow<Household?> {
        return repository.getHouseholdById(householdId)
    }
}