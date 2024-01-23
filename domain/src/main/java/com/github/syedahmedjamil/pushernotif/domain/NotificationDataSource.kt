package com.github.syedahmedjamil.pushernotif.domain

import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    fun getNotifications(interest: String): Flow<List<NotificationEntity>>
    suspend fun addNotification(notification: NotificationEntity)
    suspend fun deleteNotifications()
}