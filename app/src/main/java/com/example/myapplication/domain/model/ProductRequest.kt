package com.example.myapplication.domain.model

import java.math.BigDecimal

data class ProductRequest(
    val id: String,

    val householdId: String,

    val creatorId: String? = null,

    val creatorName: String? = null,

    val name: String,

    val quantity: BigDecimal? = null,

    val unit: String? = null,

    val price: BigDecimal? = null,

    val fulfilledDate: Long? = null,

    val fulfilled: Boolean = false,

    val fulfilledBy: String? = null,

    val fulfillerName: String? = null,


    val moneyReturned: Boolean = false
)
