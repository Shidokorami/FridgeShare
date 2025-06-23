package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductsFromHousehold(householdId: Long): Flow<List<Product>>

    suspend fun getProductById(productId: Long): Product?

    suspend fun insertProduct(product: Product)
}