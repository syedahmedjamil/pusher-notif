package com.github.syedahmedjamil.pushernotif.usecases

import kotlinx.coroutines.flow.Flow

interface GetInterestsUseCase {
    operator fun invoke(): Flow<List<String>>
}