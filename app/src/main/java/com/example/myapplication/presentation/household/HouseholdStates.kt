package com.example.myapplication.presentation.household

import com.example.myapplication.domain.model.Product

data class HouseholdStates(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false

)
