package com.example.myapplication.domain.repository

import com.example.myapplication.domain.util.AuthResult
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val currentUserId: StateFlow<String?>

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult<Unit>

    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult<Unit>

    suspend fun signOut(): AuthResult<Unit>
}