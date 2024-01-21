package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.EmptyInstanceIdException
import com.github.syedahmedjamil.pushernotif.core.NoInterestAddedException
import com.github.syedahmedjamil.pushernotif.core.NoNetworkException
import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.SubscribeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubscribeUseCaseImpl @Inject constructor(
    private val service: SubscribeService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SubscribeUseCase {

    private val msg_nonBusiness = "NonBusinessError: subscribe."
    private val msg_emptyId = "Please enter your Pusher Instance ID."
    private val msg_noInterest = "Please add at least 1 interest before subscribing."
    private val msg_noNetwork = "Network unavailable."

    override suspend fun invoke(instanceId: String, interests: List<String>): Result<Unit> {
        if (instanceId.isEmpty())
            return Result.Error(EmptyInstanceIdException(msg_emptyId))

        if (interests.isEmpty())
            return Result.Error(NoInterestAddedException(msg_noInterest))

        if (!service.isNetworkAvailable())
            return Result.Error(NoNetworkException(msg_noNetwork))

        try {
            withContext(dispatcher) {
                service.subscribe(instanceId, interests)
            }
        } catch (e: Exception) {
            return Result.Error(NonBusinessException(msg_nonBusiness))
        }

        return Result.Success(Unit)
    }
}