package com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase

import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.usecases.SubscribeUseCase

class FakeSubscribeUseCase : SubscribeUseCase {
    var isError = false
    var result: Result<Unit> = Result.Success(Unit)
    override suspend operator fun invoke(instanceId: String, interests: List<String>): Result<Unit> {
        if (isError)
            result = Result.Error(Exception("error"))
        return result
    }
}