package com.example.myapplication.domain.model

data class Household(
    val id: String,
    val name: String,
    val creatorId: String,
    val memberIds: List<String>,
    val createdAt: Long?
)

