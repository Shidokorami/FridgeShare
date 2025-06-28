package com.example.myapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.local.database.AppDatabaseCallback
import com.example.myapplication.data.local.database.AppDatabase
import com.example.myapplication.data.local.repository.OfflineHouseholdRepository
import com.example.myapplication.data.local.repository.OfflineProductRepository
import com.example.myapplication.data.local.repository.OfflineProductRequestRepository
import com.example.myapplication.data.local.repository.OfflineUserRepository
import com.example.myapplication.data.preferences.UserPreferences
import com.example.myapplication.domain.repository.HouseholdRepository
import com.example.myapplication.domain.repository.ProductRepository
import com.example.myapplication.domain.repository.ProductRequestRepository
import com.example.myapplication.domain.repository.UserRepository
import com.example.myapplication.domain.useCases.household_use_cases.GetHousehold
import com.example.myapplication.domain.useCases.household_use_cases.GetHouseholds
import com.example.myapplication.domain.useCases.product_use_cases.GetProductsFromHousehold
import com.example.myapplication.domain.useCases.household_use_cases.HouseholdUseCases
import com.example.myapplication.domain.useCases.product_use_cases.AddProduct
import com.example.myapplication.domain.useCases.product_use_cases.DeleteProduct
import com.example.myapplication.domain.useCases.product_use_cases.GetProduct
import com.example.myapplication.domain.useCases.product_use_cases.ProductUseCases
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
    fun provideProductRequestRepository(db: AppDatabase): ProductRequestRepository{
        return OfflineProductRequestRepository(db.productRequestDao())
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: AppDatabase): UserRepository{
        return OfflineUserRepository(db.userDao())
    }

    @Provides
    @Singleton
    fun provideHouseholdUseCases(repository: HouseholdRepository): HouseholdUseCases {
        return HouseholdUseCases(
            getHouseholds = GetHouseholds(repository),
            getHousehold = GetHousehold(repository)
        )
    }

    @Provides
    @Singleton
    fun provideProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProductsByHousehold = GetProductsFromHousehold(repository),
            getProduct = GetProduct(repository),
            addProduct = AddProduct(repository),
            deleteProduct = DeleteProduct(repository)
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