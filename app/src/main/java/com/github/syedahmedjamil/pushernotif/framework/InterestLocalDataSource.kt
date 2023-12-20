package com.github.syedahmedjamil.pushernotif.framework

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.syedahmedjamil.pushernotif.domain.InterestDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class InterestLocalDataSource(private val dataStore: DataStore<Preferences>) : InterestDataSource {

    override suspend fun addInterest(interest: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(UUID.randomUUID().toString())] = interest
        }
    }

    override fun getInterests(): Flow<List<String>> {
        val interests: Flow<List<String>> = dataStore.data.map { preferences ->
            preferences.asMap().values.map { it.toString() }
        }
        return interests

    }
}