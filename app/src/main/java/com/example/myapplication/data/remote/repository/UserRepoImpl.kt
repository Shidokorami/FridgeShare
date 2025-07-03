package com.example.myapplication.data.remote.repository

import com.example.myapplication.data.model.FirestoreUser
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.UserRepository
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UserRepoImpl(
    firestore: FirebaseFirestore
): UserRepository {

    private val usersCollection = firestore.collection("users")

    private fun FirestoreUser.toDomainModel():
            User? {
        return id?.let { firestoreId ->

            User(
                id = firestoreId,
                username = this.email
            )
        }
    }

    private fun
            User.toFirestoreModel(): FirestoreUser {
        return FirestoreUser(
            email = this.username
        )
    }

    override fun getUserById(userId: String): Flow<User?> {
        return usersCollection.document(userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(FirestoreUser::class.java)?.toDomainModel()
            }
    }

    override fun getUserByFirestoreId(firestoreId: String): Flow<
            User?> {
        return usersCollection.document(firestoreId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(FirestoreUser::class.java)?.toDomainModel()
            }
    }

    override fun getUsersByIds(userIds: List<String>): Flow<List<User>> {
        if (userIds.isEmpty()) {
            return flowOf(emptyList())
        }

        val chunkedUserIds = userIds.chunked(10)

        val flows = chunkedUserIds.map { chunk ->
            usersCollection
                .whereIn(FieldPath.documentId(), chunk)
                .snapshots()
                .map { querySnapshot ->
                    querySnapshot.documents.mapNotNull {
                        it.toObject(FirestoreUser::class.java)?.toDomainModel()
                    }
                }
        }

        return if (flows.isNotEmpty()) {
            combine(flows) { arrays ->
                arrays.flatMap { it }
            }
        } else {
            flowOf(emptyList())
        }
    }

    override suspend fun createUser(user:
                                    User): Long {
        val firestoreUser = user.toFirestoreModel()
        val newDocRef = usersCollection.add(firestoreUser).await()
        return newDocRef.id.hashCode().toLong()
    }

    override suspend fun updateUser(user:
                                    User) {
        val firestoreIdToUpdate = user.id.toString()
        val firestoreUser = user.toFirestoreModel()
        usersCollection.document(firestoreIdToUpdate).set(firestoreUser).await()
    }

    override suspend fun deleteUser(userId: Long) {
        val firestoreIdToDelete = userId.toString()
        usersCollection.document(firestoreIdToDelete).delete().await()
    }

}