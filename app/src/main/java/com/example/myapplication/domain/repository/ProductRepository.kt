package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsForHousehold(householdId: String): Flow<List<Product>>
    fun getProductById(householdId: String, productId: String): Flow<Product?>
    suspend fun addProduct(householdId: String, product: Product): String
    suspend fun updateProduct(householdId: String, product: Product)
    suspend fun deleteProduct(householdId: String, productId: String)
    suspend fun markProductAsBought(householdId: String, productId: String, buyerId: String)
}
