package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface GetNotificationsUseCase {
    operator fun invoke(interest: String): Flow<List<NotificationEntity>>
}