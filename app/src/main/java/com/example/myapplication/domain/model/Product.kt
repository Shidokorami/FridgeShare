package com.example.myapplication.domain.model

import java.math.BigDecimal

data class Product(
    val id: Long?,

    val householdId: Long,

    val name: String,

    val quantity: BigDecimal? = null,

    val unit: String? = null,

    val expirationDate: Long? = null,

    val buyerId: Long? = null,

    val buyerName: String? = null

)
