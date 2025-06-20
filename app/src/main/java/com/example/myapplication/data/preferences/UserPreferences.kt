package com.example.myapplication.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


val Context.userPreferencesDataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    //Keys
    companion object {
        val USER_ID = longPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    //Saving values
    suspend fun saveUserData(userId: Long, username: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[USERNAME] = username
            preferences[IS_LOGGED_IN] = true
        }
    }

    //Deleting data
    suspend fun clearUserData(){
        context.userPreferencesDataStore.edit {it.clear()}
    }

    suspend fun getUserId(): Long? {
        return context.userPreferencesDataStore.data.firstOrNull()?.get(USER_ID)
    }

    val userId: Flow<Long?> = context.userPreferencesDataStore.data
        .map{ it[USER_ID] }

    val username: Flow<String?> = context.userPreferencesDataStore.data
        .map{it[USERNAME]}

    val isLoggedIn: Flow<Boolean> = context.userPreferencesDataStore.data
        .map { it[IS_LOGGED_IN] == true }
}