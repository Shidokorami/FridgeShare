package com.example.myapplication.data.util

import com.example.myapplication.data.local.entity.UserEntity
import com.example.myapplication.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        id = id,
        username = name
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = username
    )
}