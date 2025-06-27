package com.example.myapplication.data.local.repository

import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.util.toUser
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineUserRepository(
    private val dao: UserDao
): UserRepository {
    override fun getUsersForHousehold(householdId: Long): Flow<List<User>> {
        return dao.getUsersByHousehold(householdId).map{users ->
            users.map{it.toUser()}
        }
    }

    override suspend fun getUserById(userId: Long): User? {
        return dao.getUserById(userId)?.toUser()
    }
}