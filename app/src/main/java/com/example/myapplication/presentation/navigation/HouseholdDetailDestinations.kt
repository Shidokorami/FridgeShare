package com.example.myapplication.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data class ProductsScreen(val householdId: Long)

@Serializable
data class RequestsScreen(val householdId: Long)

@Serializable
data class RoommatesScreen(val householdId: Long)

sealed class HouseholdDetailNavItems(
    val baseRoute: String,
    val icon: @Composable () -> Unit,
    val label: String
) {
    data object Products : HouseholdDetailNavItems(
        ProductsScreen::class.qualifiedName!!,
        { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Products") },
        "Products"
    )

    data object Requests : HouseholdDetailNavItems(
        RequestsScreen::class.qualifiedName!!,
        { Icon(Icons.Default.ShoppingCart, contentDescription = "Requests") },
        "Requests"
    )

    data object Roommates : HouseholdDetailNavItems(
        RoommatesScreen::class.qualifiedName!!,
        { Icon(Icons.Default.People, contentDescription = "Roommates") },
        "Roommates"
    )

    companion object {
        val entries = listOf(Products, Requests, Roommates)
    }
}