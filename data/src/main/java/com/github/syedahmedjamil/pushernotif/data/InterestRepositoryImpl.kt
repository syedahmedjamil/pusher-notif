package com.github.syedahmedjamil.pushernotif.data

import com.github.syedahmedjamil.pushernotif.domain.InterestDataSource
import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import kotlinx.coroutines.flow.Flow

class InterestRepositoryImpl(private val interestLocalDataSource: InterestDataSource) :
    InterestRepository {

    override suspend fun addInterest(interest: String) {
        interestLocalDataSource.addInterest(interest)
    }

    override fun getInterests(): Flow<List<String>> {
        return interestLocalDataSource.getInterests()
    }
}