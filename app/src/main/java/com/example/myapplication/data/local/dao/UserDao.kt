package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.myapplication.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query(
        """
        SELECT * FROM user u
        INNER JOIN household_user hu ON u.id = hu.user_id
        WHERE hu.household_id = :householdId
    """
    )
    fun getUsersByHousehold(householdId: Long) : Flow<List<UserEntity>>

    @Query("""
        SELECT *
        FROM USER 
        WHERE id = :userId
    """)
    suspend fun getUserById(userId: Long): UserEntity?
}