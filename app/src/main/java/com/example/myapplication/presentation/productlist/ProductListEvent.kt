package com.example.myapplication.presentation.productlist

sealed class ProductListEvent {
    data class NavigateToAddEditProduct(val productId: Long?, val householdId: Long) : ProductListEvent()
}
