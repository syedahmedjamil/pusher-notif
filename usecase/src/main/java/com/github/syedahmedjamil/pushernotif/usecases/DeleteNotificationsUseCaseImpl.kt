package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNotificationsUseCaseImpl(
    private val repository: NotificationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DeleteNotificationsUseCase {

    private val nonBusinessErrorMessage = "NonBusinessError: error deleting notifications."

    override suspend fun invoke(): Result<Unit> {
        try {
            withContext(dispatcher) {
                repository.deleteNotifications()
            }
        } catch (e: Exception) {
            return Result.Error(NonBusinessException(nonBusinessErrorMessage))
        }

        return Result.Success(Unit)
    }

}