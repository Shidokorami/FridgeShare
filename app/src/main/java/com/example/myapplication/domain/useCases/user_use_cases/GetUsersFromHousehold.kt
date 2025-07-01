package com.example.myapplication.domain.useCases.user_use_cases

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.HouseholdRepository
import com.example.myapplication.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class GetUsersFromHousehold(
    private val householdRepository: HouseholdRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(householdId: String): Flow<List<User>> {
        return householdRepository.getHouseholdById(householdId)
            .flatMapLatest { household ->
                if (household != null && household.memberIds.isNotEmpty()) {
                    userRepository.getUsersByIds(household.memberIds)
                        .map { it.filterNotNull() }
                } else {
                    flowOf(emptyList())
                }
            }
    }
}
