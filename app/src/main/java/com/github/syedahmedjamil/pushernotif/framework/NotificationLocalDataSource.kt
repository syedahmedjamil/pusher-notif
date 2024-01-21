package com.github.syedahmedjamil.pushernotif.framework

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.syedahmedjamil.pushernotif.domain.NotificationDataSource
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class NotificationLocalDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) :
    NotificationDataSource {


    override fun getNotifications(interest: String): Flow<List<NotificationEntity>> {
        val notifications: Flow<List<NotificationEntity>> = dataStore.data.map { preferences ->
            preferences.asMap().values.map {
                Gson().fromJson(it as String, NotificationEntity::class.java)
            }.filter {
                it.interest == interest
            }
        }
        return notifications
    }

    override suspend fun addNotification(notification: NotificationEntity) {

        val notificationJson = Gson().toJson(notification)
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(UUID.randomUUID().toString())] = notificationJson
        }
    }
}