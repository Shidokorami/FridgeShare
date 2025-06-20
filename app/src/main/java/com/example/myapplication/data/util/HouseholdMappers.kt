package com.example.myapplication.data.util

import com.example.myapplication.data.local.entity.HouseholdEntity
import com.example.myapplication.domain.model.Household

fun HouseholdEntity.toHousehold(): Household {
    return Household(
        id = id,
        name = name,
        creatorId = creatorId,
        createdAt = createdAt
    )
}

fun Household.toHouseholdEntity(): HouseholdEntity {
    return HouseholdEntity(
        id = id,
        name = name,
        creatorId = creatorId,
        createdAt = createdAt
    )
}