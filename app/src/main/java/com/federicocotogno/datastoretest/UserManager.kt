package com.federicocotogno.datastoretest

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    //Create the dataStore
    private val dataStore = context.createDataStore(name = "user_prefs")

    //Create some keys
    companion object {
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
        val USER_GENDER_KEY = preferencesKey<Boolean>("USER_GENDER")
    }

    //Store user data
    suspend fun storeUser(age: Int, name: String, isMale: Boolean) {
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
            it[USER_GENDER_KEY] = isMale
        }
    }

    //Create an age flow
    val userAgeFlow: Flow<Int> = dataStore.data.map {
        it[USER_AGE_KEY] ?: 0
    }

    //Create a name flow
    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    //Create a gender flow
    val userGenderFlow: Flow<Boolean> = dataStore.data.map {
        it[USER_GENDER_KEY] ?: false
    }


}