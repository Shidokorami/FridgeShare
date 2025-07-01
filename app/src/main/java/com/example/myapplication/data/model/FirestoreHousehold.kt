package com.example.myapplication.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class FirestoreHousehold(
    @DocumentId
    val id: String? = null,
    val name: String = "",
    val creatorId: String = "",
    val memberIds: List<String> = emptyList(),
    @ServerTimestamp
    val createdAt: Date? = null,

    )