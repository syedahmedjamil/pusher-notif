package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.Result

interface RemoveInterestUseCase {
    suspend operator fun invoke(interest: String): Result<Unit>
}