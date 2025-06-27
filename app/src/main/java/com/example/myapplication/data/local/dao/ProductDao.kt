package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.entity.ProductEntity
import com.example.myapplication.data.local.pojo.ProductWithBuyer
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query(
        """
            SELECT *
            FROM product
            WHERE id = :productId
        """
    )
    suspend fun getProductById(productId: Long): ProductEntity?

    @Query(
        """
            SELECT *
            FROM product
            WHERE householdId = :householdId
        """
    )
    fun getProductsFromHousehold(householdId: Long): Flow<List<ProductEntity>>

    @Query("""
        SELECT * 
        FROM product 
        WHERE householdId = :householdId
    """)
    fun getProductsWithBuyersFromHousehold(householdId: Long): Flow<List<ProductWithBuyer>>

}