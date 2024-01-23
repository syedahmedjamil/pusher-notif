package com.github.syedahmedjamil.pushernotif.data

import com.github.syedahmedjamil.pushernotif.domain.NotificationDataSource
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val notificationDataSource: NotificationDataSource) :
    NotificationRepository {

    override fun getNotifications(interest: String): Flow<List<NotificationEntity>> {
        return notificationDataSource.getNotifications(interest)
    }

    override suspend fun addNotification(notification: NotificationEntity) {
        notificationDataSource.addNotification(notification)
    }

    override suspend fun deleteNotifications() {
        notificationDataSource.deleteNotifications()
    }
}