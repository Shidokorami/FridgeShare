package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.ProductRequest
import kotlinx.coroutines.flow.Flow

interface ProductRequestRepository {

    fun getRequestByFulfillForHousehold(householdId: Long, isFulfilled: Boolean): Flow<List<ProductRequest>>

    suspend fun getRequestById(requestId: Long): ProductRequest?

    suspend fun insertRequest(request: ProductRequest)

    suspend fun updateRequest(request: ProductRequest)

}