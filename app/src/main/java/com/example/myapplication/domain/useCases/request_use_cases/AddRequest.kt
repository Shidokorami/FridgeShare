package com.example.myapplication.domain.useCases.request_use_cases

import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.repository.ProductRequestRepository

class AddRequest(
    private val requestRepo: ProductRequestRepository
) {
    suspend operator fun invoke(householdId: String, request: ProductRequest){
        if (request.id.isBlank()) {
            requestRepo.addProductRequest(householdId, request)
        } else {
            requestRepo.updateProductRequest(householdId, request)
        }
    }
}