package com.example.myapplication.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
data object HouseholdListScreen

@Serializable
data class HouseholdScreen(val householdId: Long)

@Serializable
data class AddEditProductScreen(val productId: Long = -1L, val householdId: Long)

@Serializable
data class AddEditRequestScreen(val requestId: Long = -1L, val householdId: Long)

@Serializable
data object LoginScreen

@Serializable
data object SignUpScreen