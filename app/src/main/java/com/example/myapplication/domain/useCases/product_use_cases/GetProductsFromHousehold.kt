package com.example.myapplication.domain.useCases.product_use_cases

import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsFromHousehold(
    private val repository: ProductRepository
) {
    operator fun invoke(householdId: String): Flow<List<Product>> {
        return repository.getProductsForHousehold(householdId)
    }
}