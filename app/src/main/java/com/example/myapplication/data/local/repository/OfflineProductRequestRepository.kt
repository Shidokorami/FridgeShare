package com.example.myapplication.data.local.repository

import com.example.myapplication.data.local.dao.ProductRequestDao
import com.example.myapplication.data.util.toProductRequest
import com.example.myapplication.data.util.toProductRequestEntity
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.repository.ProductRequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineProductRequestRepository(
    private val dao: ProductRequestDao
): ProductRequestRepository {
    override fun getRequestByFulfillForHousehold(
        householdId: Long,
        isFulfilled: Boolean
    ): Flow<List<ProductRequest>> {
        return dao.getRequestByFulfillForHousehold(householdId, isFulfilled).map { requests ->
            requests.map{it.toProductRequest()}
        }
    }

    override suspend fun getRequestById(requestId: Long): ProductRequest? {
        return dao.getRequestById(requestId)?.toProductRequest()
    }

    override suspend fun insertRequest(request: ProductRequest) {
        dao.insert(request.toProductRequestEntity())
    }

    override suspend fun updateRequest(request: ProductRequest) {
        dao.update(request.toProductRequestEntity() )
    }
}