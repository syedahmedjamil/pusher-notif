package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity

interface AddNotificationUseCase {
    suspend operator fun invoke(notification: NotificationEntity): Result<Unit>
}