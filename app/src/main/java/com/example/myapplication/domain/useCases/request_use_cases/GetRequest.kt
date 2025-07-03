package com.example.myapplication.domain.useCases.request_use_cases

import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.repository.ProductRequestRepository
import kotlinx.coroutines.flow.Flow

class GetRequest(
    private val requestRepo: ProductRequestRepository
) {
    operator fun invoke(householdId: String, requestId: String):  Flow<ProductRequest?> {
        return requestRepo.getProductRequestById(householdId, requestId)
    }
}