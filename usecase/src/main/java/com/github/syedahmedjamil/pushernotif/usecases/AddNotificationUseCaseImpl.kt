package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddNotificationUseCaseImpl(
    private val repository: NotificationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AddNotificationUseCase {
    override suspend fun invoke(notification: NotificationEntity): Result<Unit> {
        try {
            withContext(dispatcher) {
                repository.addNotification(notification)
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }

        return Result.Success(Unit)
    }

}