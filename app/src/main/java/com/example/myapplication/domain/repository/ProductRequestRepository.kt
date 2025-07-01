package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.ProductRequest
import kotlinx.coroutines.flow.Flow

interface ProductRequestRepository {
    fun getProductRequestsForHousehold(householdId: String): Flow<List<ProductRequest>>
    fun getProductRequestById(householdId: String, requestId: String): Flow<ProductRequest?>
    suspend fun addProductRequest(householdId: String, productRequest: ProductRequest): String
    suspend fun updateProductRequest(householdId: String, productRequest: ProductRequest)
    suspend fun deleteProductRequest(householdId: String, requestId: String)
    suspend fun markRequestFulfilled(householdId: String, requestId: String, fulfilledByUserId: String)
    suspend fun markMoneyReturned(householdId: String, requestId: String)
}