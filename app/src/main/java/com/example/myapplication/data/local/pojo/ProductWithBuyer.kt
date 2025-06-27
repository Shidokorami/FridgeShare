package com.example.myapplication.data.local.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.data.local.entity.ProductEntity
import com.example.myapplication.data.local.entity.UserEntity

data class ProductWithBuyer(
    @Embedded val product: ProductEntity,

    @Relation(
        parentColumn = "buyerId",
        entityColumn = "id"
    )
    val buyer: UserEntity?
)
