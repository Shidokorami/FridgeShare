package com.example.myapplication.domain.useCases.request_use_cases

import com.example.myapplication.domain.repository.ProductRequestRepository

class DeleteRequest(
    private val repository: ProductRequestRepository
) {
    suspend operator fun invoke(householdId: String, requestId: String){
        repository.deleteProductRequest(householdId = householdId, requestId = requestId )
    }
}