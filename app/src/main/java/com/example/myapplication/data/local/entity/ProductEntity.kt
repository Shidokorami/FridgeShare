package com.example.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["buyerId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class ProductEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val householdId: Long,

    val name: String,

    val quantity: BigDecimal? = null,

    val unit: String? = null,

    val expirationDate: Long? = null,

    val buyerId: Long? = null
)

