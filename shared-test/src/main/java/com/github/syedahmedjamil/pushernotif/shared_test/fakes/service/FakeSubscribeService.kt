package com.github.syedahmedjamil.pushernotif.shared_test.fakes.service

import com.github.syedahmedjamil.pushernotif.domain.SubscribeService

class FakeSubscribeService: SubscribeService {

    var isNetwork = true
    var isSystemError = false
    override suspend fun subscribe(instanceId: String, interests: List<String>) {
        if (isSystemError) {
            throw Exception()
        }
    }

    override fun isNetworkAvailable(): Boolean {
        return isNetwork
    }

}