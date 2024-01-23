package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.Result

interface DeleteNotificationsUseCase {
    suspend operator fun invoke(): Result<Unit>
}