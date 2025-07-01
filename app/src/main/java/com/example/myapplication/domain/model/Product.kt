package com.example.myapplication.domain.model

import java.math.BigDecimal

data class Product(
    val id: String,

    val householdId: String?,

    val name: String,

    val quantity: BigDecimal? = null,

    val unit: String? = null,

    val expirationDate: Long? = null,

    val buyerId: String? = null,

    val buyerName: String? = null

)
