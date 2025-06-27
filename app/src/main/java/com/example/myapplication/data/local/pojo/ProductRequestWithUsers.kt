package com.example.myapplication.data.local.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.data.local.entity.ProductRequestEntity
import com.example.myapplication.data.local.entity.UserEntity

data class ProductRequestWithUsers(
    @Embedded val request: ProductRequestEntity,

    // Relation for user, who created request
    @Relation(
        parentColumn = "creatorId",
        entityColumn = "id"
    )
    val creator: UserEntity?,

    // Relation for user, who fulfilled request
    @Relation(
        parentColumn = "fulfilledBy",
        entityColumn = "id"
    )
    val fulfiller: UserEntity?
)