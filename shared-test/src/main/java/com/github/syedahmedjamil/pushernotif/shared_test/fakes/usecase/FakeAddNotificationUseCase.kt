package com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase

import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.usecases.AddNotificationUseCase

class FakeAddNotificationUseCase : AddNotificationUseCase {
    var isError = false
    var result: Result<Unit> = Result.Success(Unit)

    companion object {
        var storedNotification: NotificationEntity? = null
    }



    override suspend fun invoke(notification: NotificationEntity): Result<Unit> {
        if (isError)
            result = Result.Error(Exception("error"))
        storedNotification = notification
        return result
    }
}