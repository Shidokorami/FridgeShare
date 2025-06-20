package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    //get all user from household
    fun getUsersForHouseholdStream(householdId: Long): Flow<List<User>>

    //get user info
    fun getUserByIdStream(userId: Long): Flow<User>
}