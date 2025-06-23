package com.example.myapplication.presentation.householdlist

import com.example.myapplication.domain.model.Household

data class HouseholdListStates(
    val households: List<Household> = emptyList(),
    val isLoading: Boolean = false

)