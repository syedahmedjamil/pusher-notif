package com.github.syedahmedjamil.pushernotif.shared_test

import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeGetInterestsUseCase : GetInterestsUseCase {

    private val flow = MutableSharedFlow<List<String>>()
    suspend fun emit(value: List<String>) = flow.emit(value)
    override fun invoke(): Flow<List<String>> = flow

}