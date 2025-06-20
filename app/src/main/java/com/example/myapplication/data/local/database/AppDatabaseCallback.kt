package com.example.myapplication.data.local.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.data.local.dao.HouseholdDao
import com.example.myapplication.data.local.dao.HouseholdUserDao
import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.local.entity.HouseholdEntity
import com.example.myapplication.data.local.entity.HouseholdUserEntity
import com.example.myapplication.data.local.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Inject

val initialHouseholds = listOf(
    HouseholdEntity(id = 1, name = "Domostwo Kolorowe", createdAt = 300, creatorId = 1),
    HouseholdEntity(id = 2, name = "Domostwo Tęczowe", createdAt = 400, creatorId = 2),
    HouseholdEntity(id = 3, name = "Lodówka", createdAt = 100, creatorId = 2),

    )

val initialUsers = listOf(
    UserEntity(id = 1, name = "Domownik"),
    UserEntity(id = 2, name = "Koksownik"),
    UserEntity(id = 3, name = "Weronika"),
    UserEntity(id = 4, name = "Mikolaj"),
    UserEntity(id = 5, name = "Spioszek"),
)

val initialHouseholdUser = listOf(
    HouseholdUserEntity(householdId = 1, userId = 1),
    HouseholdUserEntity(householdId = 1, userId = 2),
    HouseholdUserEntity(householdId = 1, userId = 5),
    HouseholdUserEntity(householdId = 2, userId = 3),
    HouseholdUserEntity(householdId = 2, userId = 4),
    HouseholdUserEntity(householdId = 3, userId = 3),
    HouseholdUserEntity(householdId = 3, userId = 4),
    HouseholdUserEntity(householdId = 3, userId = 5),

    )

class AppDatabaseCallback @Inject constructor(
    private val householdDaoProvider: Provider<HouseholdDao>,
    private val userDaoProvider: Provider<UserDao>,
    private val householdUserDaoProvider: Provider<HouseholdUserDao>,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val householdDao = householdDaoProvider.get()
            val userDao = userDaoProvider.get()
            val householdUserDao = householdUserDaoProvider.get()

            initialUsers.forEach { user ->
                userDao.insert(user)
            }

            initialHouseholds.forEach { household ->
                householdDao.insert(household)
            }



            initialHouseholdUser.forEach { householdUser ->
                householdUserDao.insert(householdUser)
            }

        }
    }
}