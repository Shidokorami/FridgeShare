package com.example.myapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.HouseholdDao
import com.example.myapplication.data.local.dao.HouseholdUserDao
import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.local.entity.HouseholdEntity
import com.example.myapplication.data.local.entity.HouseholdUserEntity
import com.example.myapplication.data.local.entity.UserEntity

@Database(
    entities = [
        HouseholdEntity::class,
        UserEntity::class,
        HouseholdUserEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class  AppDatabase : RoomDatabase() {

    abstract fun householdDao(): HouseholdDao
    abstract fun userDao(): UserDao
    abstract fun householdUserDao(): HouseholdUserDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}