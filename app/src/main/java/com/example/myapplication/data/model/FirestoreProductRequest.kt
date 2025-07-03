package com.example.myapplication.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class FirestoreProductRequest(
    @DocumentId
    val id: String? = null,
    val householdId: String = "",
    val creatorId: String? = null,

    val name: String = "",

    val quantity: String? = null,
    val unit: String? = null,
    val price: String? = null,
    val fulfilledDate: Date? = null,

    val fulfilled: Boolean = false,
    val fulfilledBy: String? = null,

    val expirationDate: Date? = null,

    val moneyReturned: Boolean = false
)

