package com.example.myapplication.presentation.productlist

import com.example.myapplication.domain.model.Product

data class ProductListStates(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false

)
