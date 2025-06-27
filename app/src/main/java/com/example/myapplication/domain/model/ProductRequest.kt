package com.example.myapplication.domain.model

import java.math.BigDecimal

data class ProductRequest(
    val id: Long,

    val householdId: Long,

    val creatorId: Long? = null,

    val creatorName: String? = null,

    val name: String,

    val quantity: BigDecimal? = null,

    val unit: String? = null,

    val price: BigDecimal? = null,

    val expirationDate: Long? = null,

    val fulfilled: Boolean = false,

    val fulfilledBy: Long? = null,
    
    val fulfillerName: String? = null,

    val moneyReturned: Boolean = false
)
