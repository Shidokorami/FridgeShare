package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersByIds(userIds: List<String>): Flow<List<User?>>
    fun getUserById(userId: String): Flow<User?>
    suspend fun createUser(user: User): Long
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: Long)
    fun getUserByFirestoreId(firestoreId: String): Flow<User?>
}