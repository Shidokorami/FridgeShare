package com.example.myapplication.data.local.repository

import com.example.myapplication.data.local.dao.ProductDao
import com.example.myapplication.data.util.toProduct
import com.example.myapplication.data.util.toProductEntity
import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineProductRepository(
    private val dao: ProductDao
): ProductRepository {
    override fun getProductsFromHousehold(householdId: Long): Flow<List<Product>> {
        return dao.getProductsWithBuyersFromHousehold(householdId).map { products ->
            products.map{ it.toProduct()}
        }
    }

    override suspend fun getProductById(productId: Long): Product? {
        return dao.getProductById(productId)?.toProduct()
    }

    override suspend fun insertProduct(product: Product) {
        dao.insert(product.toProductEntity())
    }

    override suspend fun updateProduct(product: Product) {
        dao.update(product.toProductEntity())
    }

}