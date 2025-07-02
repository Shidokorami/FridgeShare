package com.example.myapplication.data.model

import com.google.firebase.firestore.DocumentId

data class FirestoreUser(
    @DocumentId
    val id: String? = null,
    val email: String = ""
)