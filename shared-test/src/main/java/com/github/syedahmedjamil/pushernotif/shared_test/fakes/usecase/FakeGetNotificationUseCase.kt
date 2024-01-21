package com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase

import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.usecases.GetNotificationsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeGetNotificationUseCase : GetNotificationsUseCase {


    private val flow = MutableSharedFlow<List<NotificationEntity>>()
    suspend fun emit(value: List<NotificationEntity>) = flow.emit(value)
    override fun invoke(interest: String): Flow<List<NotificationEntity>> = flow


}