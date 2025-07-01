package com.example.myapplication.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class FirestoreProduct(
    @DocumentId
    val id: String? = null,
    val householdId: String? = "",
    val name: String = "",
    val quantity: String? = null,
    val unit: String? = null,
    val expirationDate: Date? = null,
    val buyerId: String? = null
)