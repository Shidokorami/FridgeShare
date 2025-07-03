package com.example.myapplication.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
data object HouseholdListScreen

@Serializable
data class HouseholdScreen(val householdId: String)

@Serializable
data class AddEditProductScreen(val productId: String = "", val householdId: String)

@Serializable
data class AddEditRequestScreen(val requestId: String = "", val householdId: String)

@Serializable
data object LoginScreen

@Serializable
data object SignUpScreen