package com.example.myapplication.domain.useCases.user_use_cases

import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class GetUserName(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String?): String?{
        return repository.getUserById(userId.toString()).firstOrNull()?.username
    }
}