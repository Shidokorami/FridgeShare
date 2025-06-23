package com.example.myapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.local.dao.HouseholdDao
import com.example.myapplication.data.local.dao.HouseholdUserDao
import com.example.myapplication.data.local.dao.ProductDao
import com.example.myapplication.data.local.dao.ProductRequestDao
import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.local.entity.HouseholdEntity
import com.example.myapplication.data.local.entity.HouseholdUserEntity
import com.example.myapplication.data.local.entity.ProductEntity
import com.example.myapplication.data.local.entity.ProductRequestEntity
import com.example.myapplication.data.local.entity.UserEntity
import com.example.myapplication.data.util.BigDecimalConverter

@Database(
    entities = [
        HouseholdEntity::class,
        UserEntity::class,
        HouseholdUserEntity::class,
        ProductEntity::class,
        ProductRequestEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(BigDecimalConverter::class)
abstract class  AppDatabase : RoomDatabase() {

    abstract fun householdDao(): HouseholdDao
    abstract fun userDao(): UserDao
    abstract fun householdUserDao(): HouseholdUserDao
    abstract fun productDao(): ProductDao
    abstract fun productRequestDao(): ProductRequestDao


    companion object {
        const val DATABASE_NAME = "app_db"
    }
}