package com.example.myapplication.domain.useCases

import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.repository.HouseholdRepository
import kotlinx.coroutines.flow.Flow

class GetHouseholds(
    private val repository: HouseholdRepository
) {
    operator fun invoke(): Flow<List<Household>> {
        return repository.getHouseholds()
    }
}