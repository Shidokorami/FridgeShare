package com.example.myapplication.data.remote.repository

import com.example.myapplication.data.model.FirestoreProduct
import com.example.myapplication.data.util.toBigDecimalFromFirestore
import com.example.myapplication.data.util.toFirestoreString
import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.Date

class ProductRepoImpl(
    private val firestore: FirebaseFirestore
): ProductRepository {

    private fun getProductsCollectionRef(householdId: String) =
        firestore.collection("households").document(householdId).collection("products")


    private fun FirestoreProduct.toDomainModel(): Product? {
        return id?.let {
            Product(
                id = this.id,
                householdId = this.householdId,
                name = this.name,
                quantity = this.quantity.toBigDecimalFromFirestore(),
                unit = this.unit,
                expirationDate = this.expirationDate?.time,
                buyerId = this.buyerId
            )
        }
    }

    private fun Product.toFirestoreModel(): FirestoreProduct {
        return FirestoreProduct(
            id = this.id,
            householdId = this.householdId,
            name = this.name,
            quantity = this.quantity.toFirestoreString(),
            unit = this.unit,
            expirationDate = this.expirationDate?.let { Date(it) },
            buyerId = this.buyerId
        )
    }


    override fun getProductsForHousehold(householdId: String): Flow<List<Product>> {
        return getProductsCollectionRef(householdId)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents.mapNotNull { it.toObject(FirestoreProduct::class.java)?.toDomainModel() }
            }
    }

    override fun getProductById(
        householdId: String,
        productId: String
    ): Flow<Product?> {
        return getProductsCollectionRef(householdId).document(productId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(FirestoreProduct::class.java)?.toDomainModel()
            }
    }

    override suspend fun addProduct(
        householdId: String,
        product: Product
    ): String {
        val firestoreProduct = product.toFirestoreModel()
        val newDocRef = getProductsCollectionRef(householdId).add(firestoreProduct).await()
        return newDocRef.id
    }

    override suspend fun updateProduct(
        householdId: String,
        product: Product
    ) {
        val id = product.id
        val firestoreProduct = product.toFirestoreModel()
        getProductsCollectionRef(householdId).document(id).set(firestoreProduct).await()
    }

    override suspend fun deleteProduct(householdId: String, productId: String) {
        getProductsCollectionRef(householdId).document(productId).delete().await()
    }

    override suspend fun markProductAsBought(
        householdId: String,
        productId: String,
        buyerId: String
    ) {
        val updates = mapOf(
            "buyerId" to buyerId,
            "fulfilled" to true
        )
        getProductsCollectionRef(householdId).document(productId).update(updates).await()
    }


}