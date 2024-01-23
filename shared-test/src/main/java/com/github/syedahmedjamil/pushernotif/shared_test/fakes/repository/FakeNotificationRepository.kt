package com.github.syedahmedjamil.pushernotif.shared_test.fakes.repository

import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class FakeNotificationRepository(val notifications: List<NotificationEntity> = listOf()) :
    NotificationRepository {

    var isSystemError = false

    override fun getNotifications(interest: String): Flow<List<NotificationEntity>> = flow {
        emit(notifications.filter {
            it.interest == interest
        })
    }

    override suspend fun addNotification(notification: NotificationEntity) {
        if (isSystemError) {
            throw IOException()
        }
    }

    override suspend fun deleteNotifications() {
        if (isSystemError) {
            throw IOException()
        }
    }

}