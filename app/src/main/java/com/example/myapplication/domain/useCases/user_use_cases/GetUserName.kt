package com.example.myapplication.domain.useCases.user_use_cases

import android.util.Log // Dodaj ten import
import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class GetUserName(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String?): String? {
        Log.d("UserUseCase", "GetUserName invoked for userId: $userId") // Log: Wywołanie z userId
        return if (!userId.isNullOrBlank()) {
            val username = repository.getUserById(userId).firstOrNull()?.username
            Log.d("UserUseCase", "Fetched username for $userId: $username") // Log: Pobrana nazwa
            username
        } else {
            Log.w("UserUseCase", "userId is null or blank. Cannot fetch username.") // Log: Puste ID
            null
        }
    }
}