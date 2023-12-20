package com.github.syedahmedjamil.pushernotif.domain

import kotlinx.coroutines.flow.Flow

interface InterestRepository {
    suspend fun addInterest(interest: String)
    fun getInterests(): Flow<List<String>>
}