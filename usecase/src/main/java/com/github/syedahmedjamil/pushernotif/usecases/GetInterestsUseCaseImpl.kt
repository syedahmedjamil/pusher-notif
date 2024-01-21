package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetInterestsUseCaseImpl @Inject constructor(
    private val repository: InterestRepository
) : GetInterestsUseCase {
    override fun invoke(): Flow<List<String>> {
        return repository.getInterests()
    }

}