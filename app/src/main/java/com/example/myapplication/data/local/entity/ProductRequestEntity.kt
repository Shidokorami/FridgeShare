package com.example.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal


@Entity(
    tableName = "product_request",
    foreignKeys = [
        ForeignKey(
            entity = HouseholdEntity::class,
            parentColumns = ["id"],
            childColumns = ["householdId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["creatorId"],
            onDelete = ForeignKey.SET_NULL
        ),

        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["fulfilledBy"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class ProductRequestEntity(
    @PrimaryKey
    val id: Long,

    val householdId: Long,

    val creatorId: Long? = null,

    val name: String,

    val quantity: BigDecimal? = null,

    val unit: String? = null,

    val price: BigDecimal? = null,

    val expirationDate: Long? = null,

    val fulfilled: Boolean = false,

    val fulfilledBy: Long? = null,

    val moneyReturned: Boolean = false


)
