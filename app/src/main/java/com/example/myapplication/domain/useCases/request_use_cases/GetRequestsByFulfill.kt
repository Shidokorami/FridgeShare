package com.example.myapplication.domain.useCases.request_use_cases

import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.repository.ProductRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRequestsByFulfill(
    private val productRequestRepository: ProductRequestRepository
) {
    operator fun invoke(householdId: String, fulfilled: Boolean): Flow<List<ProductRequest>> {
        return productRequestRepository.getProductRequestsForHousehold(householdId)
            .map { requests ->
                requests.filter { it.fulfilled == fulfilled }
            }
    }
}