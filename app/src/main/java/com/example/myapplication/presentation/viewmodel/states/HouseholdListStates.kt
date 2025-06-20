package com.example.myapplication.presentation.viewmodel.states

import com.example.myapplication.domain.model.Household

data class HouseholdListStates(
    val households: List<Household> = emptyList(),
    val isLoading: Boolean = false

)
