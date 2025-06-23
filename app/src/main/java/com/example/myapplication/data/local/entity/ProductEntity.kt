package com.example.myapplication.data.local.entity

import android.text.BoringLayout
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product")
data class ProductEntity (
    @PrimaryKey
    val id: Long,

    val householdId: Long,

    val name: String,

    val quantity: Float? = null,

    val expirationDate: Long? = null,

    val buyerId: Long? = null
)