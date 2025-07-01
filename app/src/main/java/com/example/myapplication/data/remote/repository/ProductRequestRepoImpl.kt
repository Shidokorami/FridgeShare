package com.example.myapplication.data.remote.repository

import com.example.myapplication.data.util.toFirestoreString
import com.example.myapplication.data.util.toBigDecimalFromFirestore
import com.example.myapplication.domain.repository.ProductRequestRepository
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.data.model.FirestoreProductRequest
import com.example.myapplication.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class ProductRequestRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository
) : ProductRequestRepository {

    private fun getProductRequestsCollectionRef() =
        firestore.collection("productRequests")

    private suspend fun FirestoreProductRequest.toDomainModel(): ProductRequest? {
        return id?.let { firestoreId ->
            val creatorName = this.creatorId?.let { userRepository.getUserById(it).firstOrNull()?.username }
            val fulfillerName = this.fulfilledBy?.let { userRepository.getUserById(it).firstOrNull()?.username }

            ProductRequest(
                id = firestoreId,
                householdId = this.householdId,
                creatorId = this.creatorId,
                creatorName = creatorName,
                name = this.name,
                quantity = this.quantity.toBigDecimalFromFirestore(),
                unit = this.unit,
                price = this.price.toBigDecimalFromFirestore(),
                fulfilledDate = this.fulfilledDate?.time,
                fulfilled = this.fulfilled,
                fulfilledBy = this.fulfilledBy,
                fulfillerName = fulfillerName,
                moneyReturned = this.moneyReturned
            )
        }
    }

    private fun ProductRequest.toFirestoreModel(): FirestoreProductRequest {
        return FirestoreProductRequest(
            householdId = this.householdId,
            creatorId = this.creatorId,
            name = this.name,
            quantity = this.quantity.toFirestoreString(),
            unit = this.unit,
            price = this.price.toFirestoreString(),
            fulfilledDate = this.fulfilledDate?.let { Date(it) },
            fulfilled = this.fulfilled,
            fulfilledBy = this.fulfilledBy,
            moneyReturned = this.moneyReturned
        )
    }

    override fun getProductRequestsForHousehold(householdId: String): Flow<List<ProductRequest>> {
        return getProductRequestsCollectionRef()
            .whereEqualTo("householdId", householdId)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents.mapNotNull {
                    runBlocking { it.toObject(FirestoreProductRequest::class.java)?.toDomainModel() }
                }
            }
    }

    override fun getProductRequestById(
        householdId: String,
        requestId: String
    ): Flow<ProductRequest?> {
        return getProductRequestsCollectionRef()
            .document(requestId)
            .snapshots()
            .map { snapshot ->
                runBlocking { snapshot.toObject(FirestoreProductRequest::class.java)?.toDomainModel() }
            }
    }

    override suspend fun addProductRequest(
        householdId: String,
        productRequest: ProductRequest
    ): String {
        val firestoreProductRequest = productRequest.copy(householdId = householdId).toFirestoreModel()
        val newDocRef = getProductRequestsCollectionRef().add(firestoreProductRequest).await()
        return newDocRef.id
    }

    override suspend fun updateProductRequest(householdId: String, productRequest: ProductRequest) {
        val id = productRequest.id

        val firestoreProductRequest = productRequest.toFirestoreModel()
        getProductRequestsCollectionRef().document(id).set(firestoreProductRequest).await()
    }

    override suspend fun deleteProductRequest(householdId: String, requestId: String) {
        getProductRequestsCollectionRef().document(requestId).delete().await()
    }

    override suspend fun markRequestFulfilled(
        householdId: String,
        requestId: String,
        fulfilledByUserId: String
    ) {
        val updates = mapOf(
            "fulfilled" to true,
            "fulfilledBy" to fulfilledByUserId
        )
        getProductRequestsCollectionRef().document(requestId).update(updates).await()
    }

    override suspend fun markMoneyReturned(householdId: String, requestId: String) {
        val updates = mapOf("moneyReturned" to true)
        getProductRequestsCollectionRef().document(requestId).update(updates).await()
    }
}
