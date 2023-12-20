package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import kotlinx.coroutines.flow.Flow


class GetInterestsUseCaseImpl(
    private val repository: InterestRepository
) : GetInterestsUseCase {
    override fun invoke(): Flow<List<String>> {
        return repository.getInterests()
    }

}