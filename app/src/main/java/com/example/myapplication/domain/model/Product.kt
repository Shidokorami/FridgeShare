package com.example.myapplication.domain.model

data class Product(
    val id: Long,

    val householdId: Long,

    val name: String,

    val quantity: Float? = null,

    val expirationDate: Long? = null,

    val buyerId: Long? = null

)
