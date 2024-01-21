package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RemoveInterestUseCaseImpl @Inject constructor(
    private val repository: InterestRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoveInterestUseCase {

    private val nonBusinessErrorMessage = "NonBusinessError: error removing interest."

    override suspend operator fun invoke(interest: String): Result<Unit> {
        try {
            withContext(dispatcher) {
                repository.removeInterest(interest)
            }
        } catch (e: Exception) {
            return Result.Error(NonBusinessException(nonBusinessErrorMessage))
        }

        return Result.Success(Unit)
    }
}