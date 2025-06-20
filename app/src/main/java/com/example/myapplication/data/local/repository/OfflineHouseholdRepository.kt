package com.example.myapplication.data.local.repository

import com.example.myapplication.data.local.dao.HouseholdDao
import com.example.myapplication.data.util.toHousehold
import com.example.myapplication.data.util.toHouseholdEntity
import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.repository.HouseholdRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class OfflineHouseholdRepository(
    private val dao: HouseholdDao
) : HouseholdRepository {




    override fun getHouseholds(): Flow<List<Household>> {
        return dao.getHouseholds().map { entities ->
            entities.map { it.toHousehold() }
        }
    }

    override suspend fun getHouseholdById(id: Long): Household? {
        return dao.getHouseholdById(id)?.toHousehold()
    }

    override suspend fun insertHousehold(household: Household) {
        dao.insert(household.toHouseholdEntity())
    }

}