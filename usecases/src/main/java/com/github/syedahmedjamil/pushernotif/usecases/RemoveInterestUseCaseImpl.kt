package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.DuplicateInterestException
import com.github.syedahmedjamil.pushernotif.core.EmptyInterestException
import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class RemoveInterestUseCaseImpl(
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