package com.example.myapplication.data.remote.repository

import com.example.myapplication.data.model.FirestoreHousehold
import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.repository.HouseholdRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.Date


class HouseholdRepoImpl(
    private val firestore: FirebaseFirestore
) : HouseholdRepository {

    private val householdsCollection = firestore.collection("households")

    private fun FirestoreHousehold.toDomainModel(): Household?{
        return id?.let {
            Household(
                id = it,
                name = this.name,
                creatorId = this.creatorId,
                memberIds = this.memberIds,
                createdAt = this.createdAt?.time ?: Date().time
            )
        }
    }

    private fun Household.toFirestoreModel(): FirestoreHousehold{
        return FirestoreHousehold(
            name = this.name,
            creatorId = this.creatorId,
            memberIds = this.memberIds,

        )
    }

    private fun Household.toFirestoreUpdateMap(): Map<String, Any?> {
        return mapOf(
            "name" to this.name,
            "creatorId" to this.creatorId,
            "createdAt" to this.createdAt
        )
    }

    override fun getHouseholdsForUser(userId: String): Flow<List<Household>> {
        return householdsCollection
            .whereArrayContains("memberIds", userId)
            .snapshots()
            .map {snapshot ->
                snapshot.documents.mapNotNull { it.toObject(FirestoreHousehold::class.java)?.toDomainModel() }
            }
    }

    override fun getHouseholdById(householdId: String): Flow<Household?> {
        return householdsCollection.document(householdId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(FirestoreHousehold::class.java)?.toDomainModel()
            }
    }

    override suspend fun addHousehold(household: Household): String {
        val householdWithCreatorAsMember = household.copy(memberIds = listOf(household.creatorId))
        val firestoreHousehold = householdWithCreatorAsMember.toFirestoreModel()
        val newDocRef = householdsCollection.add(firestoreHousehold).await()
        return newDocRef.id
    }

    override suspend fun updateHousehold(household: Household) {
        household.id.let { id ->
            val firestoreHousehold = household.toFirestoreModel()
            householdsCollection.document(id).set(firestoreHousehold).await()
        } ?: throw IllegalArgumentException("Household ID cannot be null for update")
    }

    override suspend fun addHouseholdMember(householdId: String, userId: String) {
        householdsCollection.document(householdId).update("memberIds", FieldValue.arrayUnion(userId)).await()
        addHouseholdMemberToSubcollection(householdId, userId)

    }

    override suspend fun removeHouseholdMember(householdId: String, userId: String) {
        householdsCollection.document(householdId).update("memberIds", FieldValue.arrayRemove(userId)).await()
        removeHouseholdMemberFromSubcollection(householdId, userId)
    }

    override fun isUserMemberOfHousehold(
        householdId: String,
        userId: String
    ): Flow<Boolean> {
        return householdsCollection.document(householdId)
            .collection("members")
            .document(userId)
            .snapshots()
            .map { it.exists() }
    }

    private suspend fun addHouseholdMemberToSubcollection(householdId: String, userId: String) {
        householdsCollection.document(householdId)
            .collection("members")
            .document(userId)
            .set(mapOf("joinedAt" to Date()))
            .await()
    }

    private suspend fun removeHouseholdMemberFromSubcollection(householdId: String, userId: String) {
        householdsCollection.document(householdId)
            .collection("members")
            .document(userId)
            .delete()
            .await()
    }

}