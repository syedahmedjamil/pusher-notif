package com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase

import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCase

class FakeAddInterestUseCase : AddInterestUseCase {
    var isError = false
    var result: Result<Unit> = Result.Success(Unit)
    override suspend operator fun invoke(interest: String): Result<Unit> {
        if (isError)
            result = Result.Error(Exception("error"))
        return result
    }
}