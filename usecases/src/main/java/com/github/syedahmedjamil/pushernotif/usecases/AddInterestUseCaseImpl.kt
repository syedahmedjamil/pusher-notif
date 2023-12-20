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


class AddInterestUseCaseImpl(
    private val repository: InterestRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AddInterestUseCase {

    private val emptyErrorMessage = "Interest can't be empty."
    private val duplicateErrorMessage = "Interest already exists."
    private val nonBusinessErrorMessage = "NonBusinessError: error adding interest."

    override suspend operator fun invoke(interest: String): Result<Unit> {

        if (interest.isEmpty())
            return Result.Error(EmptyInterestException(emptyErrorMessage))

        if (repository.getInterests().first().contains(interest))
            return Result.Error(DuplicateInterestException(duplicateErrorMessage))

        try {
            withContext(dispatcher) {
                repository.addInterest(interest)
            }
        } catch (e: Exception) {
            return Result.Error(NonBusinessException(nonBusinessErrorMessage))
        }

        return Result.Success(Unit)
    }
}