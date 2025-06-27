package com.example.myapplication.domain.useCases.product_use_cases

import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.repository.ProductRepository

class GetProduct(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Long): Product? {
        return repository.getProductById(productId)
    }
}