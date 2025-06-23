package com.example.myapplication.domain.useCases

import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsFromHousehold(
    private val repository: ProductRepository
) {
    operator fun invoke(householdId: Long): Flow<List<Product>> {
        return repository.getProductsFromHousehold(householdId)
    }
}