package com.example.myapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.local.database.AppDatabaseCallback
import com.example.myapplication.data.local.database.AppDatabase
import com.example.myapplication.data.local.repository.OfflineHouseholdRepository
import com.example.myapplication.data.local.repository.OfflineProductRepository
import com.example.myapplication.data.preferences.UserPreferences
import com.example.myapplication.domain.repository.HouseholdRepository
import com.example.myapplication.domain.repository.ProductRepository
import com.example.myapplication.domain.useCases.GetHouseholds
import com.example.myapplication.domain.useCases.GetProductsFromHousehold
import com.example.myapplication.domain.useCases.HouseholdUseCases
import com.example.myapplication.domain.useCases.ProductUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        app: Application,
        appDatabaseCallback: AppDatabaseCallback
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME

        ).addCallback(appDatabaseCallback)
            .build()
    }

    @Provides
    @Singleton
    fun provideHouseholdRepository(db: AppDatabase): HouseholdRepository {
        return OfflineHouseholdRepository(db.householdDao())
    }

    @Provides
    @Singleton
    fun provideProductRepository(db: AppDatabase): ProductRepository {
        return OfflineProductRepository(db.productDao())
    }

    @Provides
    @Singleton
    fun provideHouseholdUseCases(repository: HouseholdRepository): HouseholdUseCases {
        return HouseholdUseCases(
            getHouseholds = GetHouseholds(repository)
        )
    }

    @Provides
    @Singleton
    fun provideProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProductsByHousehold = GetProductsFromHousehold(repository)
        )
    }

    @Provides
    @Singleton
    fun provideHouseholdDao(database: AppDatabase) = database.householdDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideHouseholdUserDao(database: AppDatabase) = database.householdUserDao()

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase) = database.productDao()

    @Provides
    @Singleton
    fun provideProductRequestDao(database: AppDatabase) = database.productRequestDao()

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }
}