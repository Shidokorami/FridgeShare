package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.entity.ProductRequestEntity


@Dao
interface ProductRequestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(request: ProductRequestEntity)

    @Update
    suspend fun update(request: ProductRequestEntity)

    @Delete
    suspend fun delete(request: ProductRequestEntity)

    @Query(
        """
            SELECT * 
            FROM product_request
            WHERE id = :productId
        """
    )
    suspend fun getRequestById(productId: Long): ProductRequestEntity
}