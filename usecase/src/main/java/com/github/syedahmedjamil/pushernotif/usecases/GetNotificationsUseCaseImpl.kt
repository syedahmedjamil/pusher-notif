package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import kotlinx.coroutines.flow.Flow


class GetNotificationsUseCaseImpl(
    private val repository: NotificationRepository
) : GetNotificationsUseCase {
    override fun invoke(interest: String): Flow<List<NotificationEntity>> {
        return repository.getNotifications(interest)
    }

}