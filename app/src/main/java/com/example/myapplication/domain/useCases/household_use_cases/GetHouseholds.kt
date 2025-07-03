package com.example.myapplication.domain.useCases.household_use_cases

import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.repository.HouseholdRepository
import kotlinx.coroutines.flow.Flow

class GetHouseholds(
    private val repository: HouseholdRepository
) {
    operator fun invoke(userId: String): Flow<List<Household>> {
        return repository.getHouseholdsForUser(userId)
    }
}