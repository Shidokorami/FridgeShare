package com.example.myapplication.data.util

import com.example.myapplication.data.local.entity.ProductEntity
import com.example.myapplication.domain.model.Product


fun ProductEntity.toProduct(): Product{
    return Product(
        id = id,
        householdId = householdId,
        name = name,
        quantity = quantity,
        expirationDate = expirationDate,
        buyerId = buyerId
    )
}

fun Product.toProductEntity(): ProductEntity{
    return ProductEntity(
        id = id,
        householdId = householdId,
        name = name,
        quantity = quantity,
        expirationDate = expirationDate,
        buyerId = buyerId

    )
}