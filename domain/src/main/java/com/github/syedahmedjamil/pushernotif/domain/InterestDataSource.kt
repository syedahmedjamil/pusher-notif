package com.github.syedahmedjamil.pushernotif.domain

import kotlinx.coroutines.flow.Flow

interface InterestDataSource {
    suspend fun addInterest(interest: String)
    fun getInterests(): Flow<List<String>>
    suspend fun removeInterest(interest: String)
}