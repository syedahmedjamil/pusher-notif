package com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase

import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.usecases.DeleteNotificationsUseCase

class FakeDeleteNotificationsUseCase : DeleteNotificationsUseCase {
    var isError = false
    var result: Result<Unit> = Result.Success(Unit)
    override suspend operator fun invoke(): Result<Unit> {
        if (isError)
            result = Result.Error(Exception("error"))
        return result
    }
}