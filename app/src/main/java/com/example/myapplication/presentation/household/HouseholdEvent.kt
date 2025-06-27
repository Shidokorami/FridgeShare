package com.example.myapplication.presentation.household

sealed class HouseholdEvent {
    data class NavigateToAddEditProduct(val productId: Long?, val householdId: Long) : HouseholdEvent()
}
