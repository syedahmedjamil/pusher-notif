package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.Result

interface SubscribeUseCase {
    suspend operator fun invoke(instanceId: String, interests: List<String>): Result<Unit>
}