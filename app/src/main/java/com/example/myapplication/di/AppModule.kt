package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.local.preferences.UserPreferences
import com.example.myapplication.data.remote.repository.AuthRepoImpl
import com.example.myapplication.data.remote.repository.HouseholdRepoImpl
import com.example.myapplication.data.remote.repository.ProductRepoImpl
import com.example.myapplication.data.remote.repository.ProductRequestRepoImpl
import com.example.myapplication.data.remote.repository.UserRepoImpl
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.repository.HouseholdRepository
import com.example.myapplication.domain.repository.ProductRepository
import com.example.myapplication.domain.repository.ProductRequestRepository
import com.example.myapplication.domain.repository.UserRepository
import com.example.myapplication.domain.useCases.auth_use_cases.AuthUseCases
import com.example.myapplication.domain.useCases.auth_use_cases.SignIn
import com.example.myapplication.domain.useCases.auth_use_cases.SignUp
import com.example.myapplication.domain.useCases.household_use_cases.CreateHousehold
import com.example.myapplication.domain.useCases.household_use_cases.GetHousehold
import com.example.myapplication.domain.useCases.household_use_cases.GetHouseholds
import com.example.myapplication.domain.useCases.household_use_cases.HouseholdUseCases
import com.example.myapplication.domain.useCases.product_use_cases.AddProduct
import com.example.myapplication.domain.useCases.product_use_cases.DeleteProduct
import com.example.myapplication.domain.useCases.product_use_cases.GetProduct
import com.example.myapplication.domain.useCases.product_use_cases.GetProductsFromHousehold
import com.example.myapplication.domain.useCases.product_use_cases.ProductUseCases
import com.example.myapplication.domain.useCases.request_use_cases.AddRequest
import com.example.myapplication.domain.useCases.request_use_cases.DeleteRequest
import com.example.myapplication.domain.useCases.request_use_cases.GetRequest
import com.example.myapplication.domain.useCases.request_use_cases.GetRequestsByFulfill
import com.example.myapplication.domain.useCases.request_use_cases.RequestUseCases
import com.example.myapplication.domain.useCases.user_use_cases.GetUserName
import com.example.myapplication.domain.useCases.user_use_cases.GetUsersFromHousehold
import com.example.myapplication.domain.useCases.user_use_cases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepoImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideHouseholdRepository(firestore: FirebaseFirestore): HouseholdRepository {
        return HouseholdRepoImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideProductRepository(firestore: FirebaseFirestore): ProductRepository {
        return ProductRepoImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideProductRequestRepository(
        firestore: FirebaseFirestore,
        userRepository: UserRepository
    ): ProductRequestRepository {
        return ProductRequestRepoImpl(firestore, userRepository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository {
        return UserRepoImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideHouseholdUseCases(householdRepository: HouseholdRepository, authRepository: AuthRepository): HouseholdUseCases {
        return HouseholdUseCases(
            getHouseholds = GetHouseholds(householdRepository),
            getHousehold = GetHousehold(householdRepository),
            createHousehold = CreateHousehold(authRepository, householdRepository)
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
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            signIn = SignIn(repository),
            signUp = SignUp(repository)
        )
    }

    @Provides
    @Singleton
    fun provideRequestUseCases(repository: ProductRequestRepository): RequestUseCases{
        return RequestUseCases(
            getRequestsByFulfill = GetRequestsByFulfill(repository),
            getRequest = GetRequest(repository),
            addRequest = AddRequest(repository),
            deleteRequest = DeleteRequest(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(householdRepository: HouseholdRepository, userRepository: UserRepository): UserUseCases{
        return UserUseCases(
            getUsersFromHousehold = GetUsersFromHousehold(householdRepository, userRepository),
            getUserName = GetUserName(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }
}