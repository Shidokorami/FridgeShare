package com.example.myapplication.data.remote.repository

import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.util.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore

) : AuthRepository {
    private val _currentUserId = MutableStateFlow<String?>(null)
    override val currentUserId: StateFlow<String?> = _currentUserId.asStateFlow()

    init {
        firebaseAuth.addAuthStateListener { auth ->
            _currentUserId.value = auth.currentUser?.uid
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(Unit)
        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Email format is invalid."
                "ERROR_WRONG_PASSWORD" -> "Incorrect password."
                "ERROR_USER_NOT_FOUND" -> "A user with this name couldn't be found."
                "ERROR_USER_DISABLED" -> "User account is banned."
                "ERROR_TOO_MANY_REQUESTS" -> "Too many login attempts. Try again later."
                else -> "Sign-in failed: ${e.localizedMessage}"
            }
            AuthResult.Error(errorMessage, e)
        } catch (e: Exception) {
            AuthResult.Error("Unknown error occurred: ${e.localizedMessage}", e)
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = firebaseAuth.currentUser?.uid
            if (userId != null) {
                val newUser = hashMapOf(
                    "email" to email,
                    "createdAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
                )
                firestore.collection("users").document(userId).set(newUser).await()
            }
            AuthResult.Success(Unit)
        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e.errorCode) {
                "ERROR_WEAK_PASSWORD" -> "Password is too weak."
                "ERROR_INVALID_EMAIL" -> "Email format is invalid."
                "ERROR_EMAIL_ALREADY_IN_USE" -> "Email address is already in use."
                else -> "Sign-up failed: ${e.localizedMessage}"
            }
            AuthResult.Error(errorMessage, e)
        } catch (e: Exception) {
            AuthResult.Error("Unknown error occurred: ${e.localizedMessage}", e)
        }
    }

    override suspend fun signOut(): AuthResult<Unit> {
        return try {
            firebaseAuth.signOut()
            AuthResult.Success(Unit)
        } catch (e: Exception) {
            AuthResult.Error("Log-out error: ${e.localizedMessage}", e)
        }
    }

}