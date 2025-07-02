package com.example.myapplication.domain.useCases.request_use_cases

import android.util.Log // Dodaj ten import
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.repository.ProductRequestRepository
import com.example.myapplication.domain.useCases.user_use_cases.GetUserName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async

class GetRequestsByFulfill(
    private val productRequestRepository: ProductRequestRepository,
    private val getUserNameUseCase: GetUserName
) {
    suspend operator fun invoke(householdId: String, fulfilled: Boolean): Flow<List<ProductRequest>> {
        Log.d("RequestUseCase", "Fetching requests for household: $householdId, fulfilled: $fulfilled") // Log: Początek
        return productRequestRepository.getProductRequestsForHousehold(householdId)
            .map { requests ->
                val filteredRequests = requests.filter { it.fulfilled == fulfilled }
                Log.d("RequestUseCase", "Filtered requests count: ${filteredRequests.size}") // Log: Liczba przefiltrowanych
                coroutineScope {
                    filteredRequests.map { request ->
                        val creatorNameDeferred = async {
                            Log.d("RequestUseCase", "Processing request ID: ${request.id}, creatorId: '${request.creatorId}'") // Log: creatorId
                            if (request.creatorId?.isNotBlank() == true) {
                                getUserNameUseCase(request.creatorId)
                            } else {
                                Log.w("RequestUseCase", "creatorId is blank for request ID: ${request.id}. Skipping username fetch.") // Log: Pusty creatorId
                                null
                            }
                        }
                        val finalCreatorName = creatorNameDeferred.await()
                        Log.d("RequestUseCase", "Final creatorName for request ID ${request.id}: $finalCreatorName") // Log: Ustawiona nazwa
                        request.copy(creatorName = finalCreatorName)
                    }
                }
            }
    }
}