package com.example.myapplication.domain.useCases.product_use_cases

import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.repository.ProductRepository

class DeleteProduct(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product){
        repository.deleteProduct(product)
    }
}