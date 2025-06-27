package com.example.myapplication.data.util

import com.example.myapplication.data.local.entity.ProductEntity
import com.example.myapplication.data.local.model.ProductWithBuyer
import com.example.myapplication.domain.model.Product


fun ProductEntity.toProduct(): Product{
    return Product(
        id = id,
        householdId = householdId,
        name = name,
        quantity = quantity,
        unit = unit,
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
        unit = unit,
        expirationDate = expirationDate,
        buyerId = buyerId

    )
}

fun ProductWithBuyer.toProduct(): Product {
    return Product(
        id = product.id,
        householdId = product.householdId,
        name = product.name,
        quantity = product.quantity,
        unit = product.unit,
        expirationDate = product.expirationDate,
        buyerId = product.buyerId,
        buyerName = buyer?.name
    )
}