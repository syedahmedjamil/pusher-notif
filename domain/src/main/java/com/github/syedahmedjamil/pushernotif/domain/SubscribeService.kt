package com.github.syedahmedjamil.pushernotif.domain

interface SubscribeService {
    suspend fun subscribe(instanceId: String, interests: List<String>)
    fun isNetworkAvailable(): Boolean
}