package com.github.syedahmedjamil.pushernotif.shared_test

import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class FakeInterestRepository(val interests: MutableList<String> = mutableListOf()) :
    InterestRepository {

    var isSystemError = false

    override suspend fun addInterest(interest: String) {
        if (isSystemError) {
            throw IOException()
        }
    }

    override fun getInterests(): Flow<List<String>> = flow {
        emit(interests)
    }

}