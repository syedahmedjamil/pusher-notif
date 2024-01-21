package com.github.syedahmedjamil.pushernotif.shared_test.fakes.source

import com.github.syedahmedjamil.pushernotif.domain.NotificationDataSource
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNotificationLocalDataSource(private val notifications: List<NotificationEntity> = listOf()) :
    NotificationDataSource {

    var isAddNotificationCalled = false

    override fun getNotifications(interest: String): Flow<List<NotificationEntity>> = flow {
        emit(notifications.filter {
            it.interest == interest
        })
    }

    override suspend fun addNotification(notification: NotificationEntity) {
        isAddNotificationCalled = true
    }

}