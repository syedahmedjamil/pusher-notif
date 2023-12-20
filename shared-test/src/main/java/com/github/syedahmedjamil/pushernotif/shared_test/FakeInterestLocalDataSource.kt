package com.github.syedahmedjamil.pushernotif.shared_test

import com.github.syedahmedjamil.pushernotif.domain.InterestDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeInterestLocalDataSource(private val interests: MutableList<String> = mutableListOf()) :
    InterestDataSource {

    var isAddInterestCalled = false

    override suspend fun addInterest(interest: String) {
        isAddInterestCalled = true
    }

    override fun getInterests(): Flow<List<String>> = flow {
        emit(interests)
    }
}