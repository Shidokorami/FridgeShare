package com.example.myapplication.data.util

import com.example.myapplication.data.local.entity.ProductRequestEntity
import com.example.myapplication.data.local.pojo.ProductRequestWithUsers
import com.example.myapplication.domain.model.ProductRequest

fun ProductRequestEntity.toProductRequest(): ProductRequest{
    return ProductRequest(
        id = id,
        householdId =householdId ,
        creatorId = creatorId,
        name = name,
        quantity = quantity,
        unit = unit,
        price = price,
        expirationDate = expirationDate,
        fulfilled = fulfilled,
        fulfilledBy = fulfilledBy,
        moneyReturned = moneyReturned
    )
}

fun ProductRequest.toProductRequestEntity(): ProductRequestEntity{
    return ProductRequestEntity(
        id = id ,
        householdId = householdId ,
        creatorId = creatorId ,
        name = name ,
        quantity = quantity ,
        unit = unit ,
        price = price ,
        expirationDate = expirationDate ,
        fulfilled = fulfilled ,
        fulfilledBy = fulfilledBy ,
        moneyReturned = moneyReturned
    )
}

fun  ProductRequestWithUsers.toProductRequest(): ProductRequest{
    return ProductRequest(
        id = request.id ,
        householdId = request.householdId ,
        creatorId = request.creatorId ,
        creatorName = creator?.name,
        name = request.name ,
        quantity = request.quantity ,
        unit = request.unit ,
        price = request.price ,
        expirationDate = request.expirationDate ,
        fulfilled = request.fulfilled ,
        fulfilledBy = request.fulfilledBy ,
        fulfillerName = fulfiller?.name ,
        moneyReturned = request.moneyReturned
    )
}