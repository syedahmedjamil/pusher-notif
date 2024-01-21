package com.github.syedahmedjamil.pushernotif.data

import com.github.syedahmedjamil.pushernotif.domain.InterestDataSource
import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InterestRepositoryImpl @Inject constructor(private val interestLocalDataSource: InterestDataSource) :
    InterestRepository {

    override suspend fun addInterest(interest: String) {
        interestLocalDataSource.addInterest(interest)
    }

    override fun getInterests(): Flow<List<String>> {
        return interestLocalDataSource.getInterests()
    }

    override suspend fun removeInterest(interest: String) {
        interestLocalDataSource.removeInterest(interest)
    }
}